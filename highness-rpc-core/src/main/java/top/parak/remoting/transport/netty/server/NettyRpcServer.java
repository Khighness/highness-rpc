package top.parak.remoting.transport.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.parak.concurrent.ThreadPoolFactoryUtils;
import top.parak.config.CustomShutdownHook;
import top.parak.config.RpcServiceConfig;
import top.parak.factory.SingletonFactory;
import top.parak.provider.ServiceProvider;
import top.parak.provider.impl.ZkServiceProviderImpl;
import top.parak.remoting.transport.netty.codec.RpcMessageDecoder;
import top.parak.remoting.transport.netty.codec.RpcMessageEncoder;
import top.parak.utils.RuntimeUtils;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author KHighness
 * @since 2021-08-22
 */
@Slf4j
@Component
public class NettyRpcServer {

    public static final int PORT = 9998;

    private final ServiceProvider serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);

    public void registerService(RpcServiceConfig rpcServiceConfig) {
        serviceProvider.publishService(rpcServiceConfig);
    }

    @SneakyThrows
    public void start() {
        CustomShutdownHook.getCustomShutdownHook().clearAll();
        String host = InetAddress.getLocalHost().getHostAddress();
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        DefaultEventExecutorGroup serviceHandlerGroup = new DefaultEventExecutorGroup(
                RuntimeUtils.cpus() * 2,
                ThreadPoolFactoryUtils.createThreadFactory("service-handler-group", false)
        );
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // 开启TCP的Nagle算法，尽可能发送大数据块，减少网络传输
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    // 开启TCP底层心跳机制
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 系统用于临时存放已完成三次握手的请求的队列的最大长度，连接建立频繁，需要适当调大
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // 当客户端第一次进行请求的时候才会进行初始化
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 30S没有收到客户端请求进关闭连接
                            ChannelPipeline p = socketChannel.pipeline();
                            p.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                            p.addLast(new RpcMessageEncoder());
                            p.addLast(new RpcMessageDecoder());
                            p.addLast(serviceHandlerGroup, new NettyRpcServerHandler());

                        }
                    });
            // 绑定端口，同步等待绑定成功
            ChannelFuture f = b.bind(host, PORT).sync();
            // 等待服务器监听端口关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("occur exception when start server: ", e);
        } finally {
            log.info("shutdown boss group and worker group");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            serviceHandlerGroup.shutdownGracefully();
        }
    }

}
