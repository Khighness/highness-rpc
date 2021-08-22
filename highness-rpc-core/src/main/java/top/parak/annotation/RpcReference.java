package top.parak.annotation;

import java.lang.annotation.*;

/**
 * @author KHighness
 * @since 2021-08-22
 * @apiNote 标注RPC接口实现类
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface RpcReference {

    /**
     * Service version
     */
    String version() default "";

    /**
     * Service group
     */
    String group() default "";
}
