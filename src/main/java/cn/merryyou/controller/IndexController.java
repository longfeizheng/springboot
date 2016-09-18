package cn.merryyou.controller;

import cn.merryyou.entity.News;
import cn.merryyou.entity.PageView;
import cn.merryyou.service.NewService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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
    @RequestMapping(value = {"","/","index"})
    public String index(Model model , HttpServletRequest request){
        String page = request.getParameter("page");
        page = StringUtils.defaultIfBlank(page,"1");
        int pageNumber = Integer.parseInt(page);

        int total = (int) newService.queryAllCount();
        PageView<News> pageView = new PageView<News>(total,pageNumber);
        pageView.setList((newService.queryListByPageAndWhere(null,pageView.getPageStart(),pageView.getPageSize())).getList());

        model.addAttribute("page",pageView);
        return "index";
    }
}
