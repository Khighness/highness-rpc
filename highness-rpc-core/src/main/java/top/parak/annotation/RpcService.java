package top.parak.annotation;

/**
 * @author KHighness
 * @since 2021-08-22
 * @apiNote 标识RPC服务实现类
 */
public @interface RpcService {

    /**
     * Service version, default value is empty string
     */
    String version() default "";

    /**
     * Service group, default value is empty string
     */
    String group() default "";

}
