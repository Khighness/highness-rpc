package top.parak.annotation;

import java.lang.annotation.*;

/**
 * @author KHighness
 * @since 2021-08-22
 * @apiNote 扫描路径
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RpcScan {

    String[] basePackages();

}
