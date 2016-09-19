package cn.test;

import org.springframework.stereotype.Service;

/**
 * Created on 2016/9/19 0019.
 *
 * @author zlf
 * @since 1.0
 */
@Service
public class HelloService {
    /**
     * 启动的时候观察控制台是否打印此信息;
     */
    public HelloService() {
        System.out.println("HelloService.HelloService()");
        System.out.println("org.kfit.service.HelloService.HelloService()");
        System.out.println("HelloService.HelloService()");
    }
}
