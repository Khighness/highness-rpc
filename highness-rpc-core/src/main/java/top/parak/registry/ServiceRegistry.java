package top.parak.registry;

import java.net.InetSocketAddress;

/**
 * @author KHighness
 * @since 2021-08-22
 * @apiNote 服务注册接口
 */
public interface ServiceRegistry {

    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);

}
