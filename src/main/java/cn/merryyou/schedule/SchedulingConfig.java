package cn.merryyou.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created on 2016/9/18 0018.
 *
 * @author zlf
 * @since 1.0
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {

    @Scheduled(cron = "0 0 * * * ?") //每小时执行一次
    public void scheduler(){
        System.out.println(">>>>>>>>>>>>>>>>>>执行定时任务");
    }
}
