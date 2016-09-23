package cn.merryyou.conf.datasource;

import java.lang.annotation.*;

/**
 *
 * 在方法上使用，用于指定使用哪个数据源
 * Created on 2016/9/23 0023.
 *
 * @author zlf
 * @since 1.0
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String value();
}
