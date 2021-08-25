package top.parak.remoting.transport.socket;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import top.parak.extension.ExtensionLoader;
import top.parak.registry.ServiceDiscovery;
import top.parak.remoting.dto.RpcRequest;
import top.parak.remoting.transport.RpcRequestTransport;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author KHighness
 * @since 2021-08-25
 * @apiNote Socket客户端
 */
@AllArgsConstructor
@Slf4j
public class SocketRpcClient implements RpcRequestTransport {
    private final ServiceDiscovery serviceDiscovery;

    public SocketRpcClient() {
        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension("zk");
    }

    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest);
        try (Socket socket = new Socket()) {
            socket.connect(inetSocketAddress);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            // send data to the server through the output stream
            objectOutputStream.writeObject(rpcRequest);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            // read rpc response from the input stream
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("call service failed: ", e);
        }
        return null;
    }
}
