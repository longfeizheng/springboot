package cn.merryyou.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *  使用@WebListener注解，实现ServletContextListener接口
 * Created on 2016/9/18 0018.
 *
 * @author zlf
 * @since 1.0
 */
@WebListener
public class MyServletContextListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("《《《《《《《《《《《《《《ServletContex初始化");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("《《《《《《《《《《《《《《ServletContex销毁");
    }
}
