package top.parak.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author KHighness
 * @since 2021-09-11
 */
@AllArgsConstructor
@Getter
public enum RpcConfigEnum {

    RPC_CONFIG_PATH("rpc.properties"),
    ZK_ADDRESS("rpc.zookeeper.address");

    private final String propertyValue;

}
