import top.parak.HelloService;
import top.parak.config.RpcServiceConfig;
import top.parak.remoting.transport.socket.SocketRpcServer;
import top.parak.serviceimpl.HelloServiceImpl;

/**
 * @author KHighness
 * @since 2021-09-11
 */
public class SocketServerMain {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        SocketRpcServer socketRpcServer = new SocketRpcServer();
        RpcServiceConfig rpcServiceConfig = new RpcServiceConfig();
        rpcServiceConfig.setService(helloService);
        socketRpcServer.registerService(rpcServiceConfig);
        socketRpcServer.start();
    }
}
