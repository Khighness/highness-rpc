package top.parak.registry;

import top.parak.extension.SPI;
import top.parak.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;

/**
 * @author KHighness
 * @since 2021-08-23
 * @apiNote 服务发现接口
 */
@SPI
public interface ServiceDiscovery {

    /**
     * 通过服务名称寻找服务地址
     *
     * @param rpcRequest RPC请求
     * @return 服务地址
     */
    InetSocketAddress lookupService(RpcRequest rpcRequest);

}
