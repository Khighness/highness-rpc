package top.parak.remoting.transport.netty.handler;

import lombok.extern.slf4j.Slf4j;
import top.parak.exception.RpcException;
import top.parak.provider.ServiceProvider;
import top.parak.remoting.dto.RpcRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author KHighness
 * @since 2021-08-25
 */
@Slf4j
public class RpcRequestHandler {
    private final ServiceProvider serviceProvider;

    public RpcRequestHandler(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    /**
     * 处理RPC请求，寻找对应服务，然后调用方法
     * @param rpcRequest RPC请求
     * @return 调用方法的处理结果
     */
    public Object handle(RpcRequest rpcRequest) {
        Object service = serviceProvider.getService(rpcRequest.getRpcServiceName());
        return invokeTargetMethod(rpcRequest, service);
    }

    /**
     * 反射调用方法，获得返回结果
     *
     * @param rpcRequest RPC请求
     * @param service    服务
     * @return 服务相关方法的执行结果
     */
    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) {
        Object result;
        try {
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            result = method.invoke(service, rpcRequest.getParameters());
            log.info("service [{}] successful invoke method [{}]", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            throw new RpcException(e.getMessage(), e);
        }
        return result;
    }
}
