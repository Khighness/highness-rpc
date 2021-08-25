package top.parak.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import top.parak.annotation.RpcScan;

/**
 * @author KHighness
 * @since 2021-08-26
 */
@RpcScan(basePackage = {"top.parak"})
public class NettyClientMain {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyClientMain.class);
        HelloController helloController = (HelloController) applicationContext.getBean("helloController");
        helloController.test();
    }
}
