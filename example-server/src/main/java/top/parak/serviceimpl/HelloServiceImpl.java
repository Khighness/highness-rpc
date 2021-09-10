package top.parak.serviceimpl;

import top.parak.Hello;
import top.parak.HelloService;
import top.parak.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author KHighness
 * @since 2021-09-11
 */
@Slf4j
@RpcService(group = "test1", version = "version1")
public class HelloServiceImpl implements HelloService {

    static {
        System.out.println("HelloServiceImpl被创建");
    }

    @Override
    public String hello(Hello hello) {
        log.info("HelloServiceImpl收到: {}.", hello.getMessage());
        String result = "Hello description is " + hello.getDescription();
        log.info("HelloServiceImpl返回: {}.", result);
        return result;
    }
}
