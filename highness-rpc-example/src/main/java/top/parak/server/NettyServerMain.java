package top.parak.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import top.parak.annotation.RpcScan;
import top.parak.api.HelloService;
import top.parak.config.RpcServiceConfig;
import top.parak.remoting.transport.netty.server.NettyRpcServer;
import top.parak.server.serviceimpl.HelloServiceImplTwo;

/**
 * @author KHighness
 * @since 2021-08-26
 */
@RpcScan(basePackage = {"top.parak"})
public class NettyServerMain {
    public static void main(String[] args) {
        // Register service via annotation
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyServerMain.class);
        NettyRpcServer nettyRpcServer = (NettyRpcServer) applicationContext.getBean("nettyRpcServer");
        // Register service manually
        HelloService helloService2 = new HelloServiceImplTwo();
        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                .group("test2").version("version2").service(helloService2).build();
        nettyRpcServer.registerService(rpcServiceConfig);
        nettyRpcServer.start();
    }
}
