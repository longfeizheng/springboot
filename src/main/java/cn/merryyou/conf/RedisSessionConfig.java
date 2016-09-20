package cn.merryyou.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created on 2016/9/19 0019.
 *
 * @author zlf
 * @since 1.0
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60)//一分钟失效
public class RedisSessionConfig {
}
