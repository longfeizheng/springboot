package cn.merryyou.controller;

import cn.merryyou.entity.News;
import cn.merryyou.service.NewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created on 2016/9/19 0019.
 *
 * @author zlf
 * @since 1.0
 */
@RequestMapping("/redis")
@Controller
public class RedisTestController {

    private static Logger log = LoggerFactory.getLogger(RedisTestController.class);

    @Autowired
    private NewService newService;

    @RequestMapping("/test/{id}")
    public @ResponseBody String test(@PathVariable("id")Integer id){
        News news = newService.findOne(id);
        log.debug("news="+news);
        News cached = newService.findOne(id);
        log.debug("cache="+news);
        news = newService.findOne(id);
        log.debug("news2="+news);
        return "ok";
    }


    @RequestMapping("/delete/{id}")
    public @ResponseBody String delete(@PathVariable("id")Integer id){
        newService.deleteFromCache(id);
        return "ok";
    }

    @RequestMapping("/test1")
    public @ResponseBody String test1(){
        newService.test();
        log.debug("DemoInfoController.test1()");
        return "ok";
    }
}
