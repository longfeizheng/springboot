package cn.merryyou.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * classpath路径：locations={"classpath:application-bean1.xml","classpath:application-bean2.xml"}
 * file路径： locations = {"file:d:/test/application-bean1.xml"};
 * Created on 2016/9/19 0019.
 *
 * @author zlf
 * @since 1.0
 */
@Configuration
@ImportResource(locations={"classpath:application-bean.xml"})
public class ConfigClass {
}
