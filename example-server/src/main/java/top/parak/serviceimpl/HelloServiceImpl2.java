package top.parak.serviceimpl;

import top.parak.Hello;
import top.parak.HelloService;
import lombok.extern.slf4j.Slf4j;
import top.parak.annotation.RpcService;

/**
 * @author KHighness
 * @since 2021-09-11
 */
@Slf4j
@RpcService(group = "test2", version = "version1")
public class HelloServiceImpl2 implements HelloService {

    @Override
    public String hello(Hello hello) {
        log.info("HelloServiceImpl2收到: {}.", hello.getMessage());
        String result = "Hello description is " + hello.getDescription();
        log.info("HelloServiceImpl2返回: {}.", result);
        return result;
    }
}
