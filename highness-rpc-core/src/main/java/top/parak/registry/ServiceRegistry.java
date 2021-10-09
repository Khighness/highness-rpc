package top.parak.registry;

import top.parak.extension.SPI;

import java.net.InetSocketAddress;

/**
 * service registration
 *
 * @author KHighness
 * @since 2021-09-11
 */
@SPI
public interface ServiceRegistry {

    /**
     * register service
     *
     * @param rpcServiceName    rpc service name
     * @param inetSocketAddress service address
     */
    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);

}
