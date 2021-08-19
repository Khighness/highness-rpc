package top.parak.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author KHighness
 * @since 2021-08-19
 * @apiNote 配置文件
 */
@Getter
@AllArgsConstructor
public enum RpcConfigEnum {

    RPC_CONFIG_ENUM("rpc.properies"),
    ZK_ADDRESS("rpc.zookeeper.properies");

    private final String propertyValue;
}
