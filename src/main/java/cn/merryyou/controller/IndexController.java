package cn.merryyou.controller;

import cn.merryyou.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 2016/9/17 0017.
 *
 * @author zlf
 * @since 1.0
 */
@Controller
public class IndexController {
    @Autowired
    private NewService newService;

    @RequestMapping("detail/{id}")
    public String detail(@PathVariable("id") int id, Model model){
        model.addAttribute("info",newService.findOne(id));
        return "detail";
    }
}
