package top.parak.config;

import lombok.*;

/**
 * @author KHighness
 * @since 2021-08-23
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcServiceConfig {

    /**
     * 服务版本
     */
    private String version = "";

    /**
     * 服务组别
     */
    private String group = "";

    /**
     * 目标服务
     */
    private Object service;

    public String getRpcServiceName() {
        return this.getServiceName() + this.getGroup() + this.getVersion();
    }

    public String getServiceName() {
        return this.service.getClass().getInterfaces()[0].getCanonicalName();
    }
}
