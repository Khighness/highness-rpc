package top.parak.remoting.transport;

import top.parak.remoting.dto.RpcRequest;

/**
 * @author KHighness
 * @since 2021-08-25
 * @apiNote 发送RPC请求
 */
public interface RpcRequestTransport {

    /**
     * 发送RPC请求并获取结果
     *
     * @param rpcRequest RPC请求
     * @return 服务器返回结果
     */
    Object sendRpcRequest(RpcRequest rpcRequest);
}
