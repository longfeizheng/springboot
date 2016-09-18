package cn.merryyou.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created on 2016/9/18 0018.
 *
 * @author zlf
 * @since 1.0
 */
@WebListener
public class MyHttpSessionListener implements HttpSessionListener{
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("《《《《《《《《《《《《《《SESSION被创建");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("《《《《《《《《《《《《《《SESSION被销毁");
    }
}
