package top.parak.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author KHighness
 * @since 2021-08-22
 * @apiNote RPC响应状态码
 */
@AllArgsConstructor
@Getter
@ToString
public enum RpcResponseCodeEnum {

    SUCCESS(200, "The remote call is successful"),
    FAILURE(500, "The remote call is failed");

    private final int code;
    private final String message;

}
