package cn.merryyou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2016/9/17 0017.
 *
 * @author zlf
 * @since 1.0
 */
@Controller
public class HelloController {
    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name){
        Map map = new HashMap<>();
        map.put("name",name);
        return "hello";
    }

    @RequestMapping("/zeroException")
    public int zeroException(){
        return 100/0;
    }
}
