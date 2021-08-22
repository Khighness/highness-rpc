package top.parak.remoting.dto;

import lombok.*;

import java.io.Serializable;

/**
 * @author KHighness
 * @since 2021-08-22
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = -507928998806292275L;

    /**
     * Request ID
     */
    private String requestId;

    /**
     * RPC service name
     */
    private String interfaceName;

    /**
     * The name of the calling method
     */
    private String methodName;

    /**
     * The parameters of the calling method
     */
    private Object[] parameters;

    /**
     * The parameter type of the calling method
     */
    private Class<?> paramTypes;

    /**
     * The version of the calling method
     */
    private String version;

    /**
     * The group of the calling method
     */
    private String group;

    public String getRpcServiceName() {
        return this.getInterfaceName();
    }
}
