package top.parak.client;

import org.springframework.stereotype.Component;
import top.parak.annotation.RpcReference;
import top.parak.api.Hello;
import top.parak.api.HelloService;

/**
 * @author KHighness
 * @since 2021-08-26
 */
@Component
public class HelloController {

    @RpcReference(version = "version1", group = "test1")
    private HelloService helloService;

    public void test() throws InterruptedException {
        String hello = this.helloService.hello(new Hello("111", "222"));
        Thread.sleep(12000);
        for (int i = 0; i < 10; i++) {
            System.out.println(helloService.hello(new Hello("111", "222")));
        }
    }
}
