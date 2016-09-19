package cn.merryyou.controller;

import cn.merryyou.properties.MerryYouProperties;
import cn.merryyou.properties.MerryYouSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.File;
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

    private final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private MerryYouSettings merryYouSettings;

    @Autowired
    private MerryYouProperties merryYouProperties;

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

    @RequestMapping(value = "test")
    public @ResponseBody String test(){
        logger.debug(merryYouProperties.getName()+"...................."+merryYouProperties.getUrl());
        logger.debug(merryYouSettings.getName()+"...................."+merryYouSettings.getUrl());
        return "成功";
    }
    @RequestMapping("rest")
    public @ResponseBody Map<String,String> testJson(){
        Map map  = new HashMap<>();
        map.put("msg","测试JSON数据");
        return map;
    }

    @RequestMapping("/file")
    public String file(){
        return "file";
    }

    @RequestMapping("/upload")
    public @ResponseBody String handleFileUpload(@RequestParam("file")MultipartFile file){
        if(!file.isEmpty()){
            try{
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
                out.write(file.getBytes());
                out.flush();
                out.close();
            }catch (Exception e){
                e.printStackTrace();
                return"上传失败,"+e.getMessage();
            }
            return "上传成功";
        }else{
            return "上传失败，空文件";
        }
    }
}

