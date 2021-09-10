package top.parak;

import top.parak.config.RpcServiceConfig;
import top.parak.proxy.RpcClientProxy;
import top.parak.remoting.transport.RpcRequestTransport;
import top.parak.remoting.transport.socket.SocketRpcClient;

/**
 * @author KHighness
 * @since 2021-09-11
 */
public class SocketClientMain {
    public static void main(String[] args) {
        RpcRequestTransport rpcRequestTransport = new SocketRpcClient();
        RpcServiceConfig rpcServiceConfig = new RpcServiceConfig();
        RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcRequestTransport, rpcServiceConfig);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        String hello = helloService.hello(new Hello("111", "222"));
        System.out.println(hello);
    }
}
