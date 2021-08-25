package top.parak.provider;

import top.parak.config.RpcServiceConfig;

/**
 * @author KHighness
 * @since 2021-08-23
 */
public interface ServiceProvider {

    /**
     * 添加服务
     *
     * @param rpcServiceConfig RPC服务相关属性
     */
    void addService(RpcServiceConfig rpcServiceConfig);

    /**
     * 查询服务
     *
     * @param rpcServiceName RPC服务名称
     * @return RPC服务
     */
    Object getService(String rpcServiceName);

    /**
     * @param rpcServiceConfig RPC服务相关属性
     */
    void publishService(RpcServiceConfig rpcServiceConfig);

}
