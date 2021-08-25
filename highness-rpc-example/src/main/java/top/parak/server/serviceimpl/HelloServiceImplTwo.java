package top.parak.server.serviceimpl;

import lombok.extern.slf4j.Slf4j;
import top.parak.api.Hello;
import top.parak.api.HelloService;

/**
 * @author KHighness
 * @since 2021-08-26
 */
@Slf4j
public class HelloServiceImplTwo implements HelloService {

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
