package top.parak.registry;

import top.parak.extension.SPI;
import top.parak.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;

/**
 * service discovery
 *
 * @author KHighness
 * @since 2021-09-11
 */
@SPI
public interface ServiceDiscovery {

    /**
     * lookup service by rpcServiceName
     *
     * @param rpcRequest rpc service pojo
     * @return service address
     */
    InetSocketAddress lookupService(RpcRequest rpcRequest);

}
