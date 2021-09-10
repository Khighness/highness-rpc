package top.parak.serviceimpl;

import top.parak.Hello;
import top.parak.HelloService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author KHighness
 * @since 2021-09-11
 */
@Slf4j
public class HelloServiceImpl2 implements HelloService {

    static {
        System.out.println("HelloServiceImpl2被创建");
    }

    @Override
    public String hello(Hello hello) {
        log.info("HelloServiceImpl2收到: {}.", hello.getMessage());
        String result = "Hello description is " + hello.getDescription();
        log.info("HelloServiceImpl2返回: {}.", result);
        return result;
    }
}
