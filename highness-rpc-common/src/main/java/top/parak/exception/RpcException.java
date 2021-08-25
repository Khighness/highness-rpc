package top.parak.exception;

import top.parak.enums.RpcErrorMessageEnum;

/**
 * @author KHighness
 * @since 2021-08-19
 * @apiNote 全局异常
 */
public class RpcException extends RuntimeException{

    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum, String detail) {
        super(rpcErrorMessageEnum.getMessage() + ":" + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum) {
        super(rpcErrorMessageEnum.getMessage());
    }
}
