package cn.merryyou.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created on 2016/9/19 0019.
 *
 * @author zlf
 * @since 1.0
 */
@ConfigurationProperties(prefix = MerryYouProperties.MERRYYOU_PREFIX,locations = "classpath:config/merryyou.yml")
public class MerryYouProperties {
    public static final String MERRYYOU_PREFIX = "merryyou";
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
