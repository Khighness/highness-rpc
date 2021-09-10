package top.parak.remoting.transport;

import top.parak.extension.SPI;
import top.parak.remoting.dto.RpcRequest;

/**
 * send RpcRequest。
 *
 * @author KHighness
 * @since 2021-09-11
 */
@SPI
public interface RpcRequestTransport {
    /**
     * send rpc request to server and get result
     *
     * @param rpcRequest message body
     * @return data from server
     */
    Object sendRpcRequest(RpcRequest rpcRequest);
}
