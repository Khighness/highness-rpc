package top.parak.remoting.transport.socket;

import lombok.extern.slf4j.Slf4j;
import top.parak.factory.SingletonFactory;
import top.parak.remoting.dto.RpcRequest;
import top.parak.remoting.dto.RpcResponse;
import top.parak.remoting.transport.netty.handler.RpcRequestHandler;

import java.io.*;
import java.net.Socket;

/**
 * @author KHighness
 * @since 2021-08-25
 */
@Slf4j
public class SocketRpcRequestHandlerRunnable implements Runnable {
    private final Socket socket;
    private final RpcRequestHandler rpcRequestHandler;

    public SocketRpcRequestHandlerRunnable(Socket socket) {
        this.socket = socket;
        this.rpcRequestHandler = SingletonFactory.getInstance(RpcRequestHandler.class);
    }

    @Override
    public void run() {
        log.info("server handle message from client by thread: [{}]", Thread.currentThread().getName());
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object result = rpcRequestHandler.handle(rpcRequest);
            objectOutputStream.writeObject(RpcResponse.success(result, rpcRequest.getRequestId()));
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            log.error("occur exception: ", e);
        }
    }
}
