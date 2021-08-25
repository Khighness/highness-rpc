package top.parak.remoting.transport.socket;

import lombok.extern.slf4j.Slf4j;
import top.parak.concurrent.ThreadPoolFactoryUtils;
import top.parak.config.CustomShutdownHook;
import top.parak.config.RpcServiceConfig;
import top.parak.factory.SingletonFactory;
import top.parak.provider.ServiceProvider;
import top.parak.provider.impl.ZkServiceProviderImpl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static top.parak.remoting.transport.netty.server.NettyRpcServer.PORT;

/**
 * @author KHighness
 * @since 2021-08-25
 * @apiNote Socket服务器
 */
@Slf4j
public class SocketRpcServer {
    private final ExecutorService threadPool;
    private final ServiceProvider serviceProvider;

    public SocketRpcServer(ExecutorService threadPool, ServiceProvider serviceProvider) {
        this.threadPool = ThreadPoolFactoryUtils.createCustomThreadPoolIfAbsent("socket-server-rpc-pool");
        this.serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);
    }

    public void registerService(RpcServiceConfig rpcServiceConfig) {
        serviceProvider.publishService(rpcServiceConfig);
    }

    public void start() {
        try (ServerSocket server = new ServerSocket()) {
            String host = InetAddress.getLocalHost().getHostAddress();
            server.bind(new InetSocketAddress(host, PORT));
            CustomShutdownHook.getCustomShutdownHook().clearAll();
            Socket socket;
            while (((socket = server.accept())) != null) {
                log.info("client connected [{}]", socket.getInetAddress());

            }
        } catch (IOException e) {
            log.error("occur IOException:", e);
        }
    }
}
