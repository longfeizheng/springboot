package cn.merryyou.controller;

import cn.merryyou.entity.News;
import cn.merryyou.entity.PageView;
import cn.merryyou.exception.CustomException;
import cn.merryyou.service.NewService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2016/9/17 0017.
 *
 * @author zlf
 * @since 1.0
 */
@Controller
public class IndexController {
    private static Logger log = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private NewService newService;

    @RequestMapping("detail/{id}")
    public String detail(@PathVariable("id") int id, Model model){
        model.addAttribute("info",newService.findOne(id));
        return "detail";
    }
    @RequestMapping(value = {"/list"})
    public String list(Model model , HttpServletRequest request){
        String page = request.getParameter("page");
        page = StringUtils.defaultIfBlank(page,"1");
        int pageNumber = Integer.parseInt(page);

        int total = (int) newService.queryAllCount();
        PageView<News> pageView = new PageView<News>(total,pageNumber);
        pageView.setList((newService.queryListByPageAndWhere(null,pageView.getPageStart(),pageView.getPageSize())).getList());

        model.addAttribute("page",pageView);
        return "list";
    }

    @RequestMapping({"/","/index"})
    public String index(){
        return "index";
    }
    @RequestMapping(value = "/login")
    public String login( HttpServletRequest request) throws Exception{
        //如果登陆失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
        //根据shiro返回的异常类路径判断，抛出指定异常信息
        Map map = new HashMap<>();
        String msg ="";
        if(exceptionClassName!=null){
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
//                throw new CustomException("账号不存在");
                log.debug("账号不存在！");
                msg = "账号不存在!";
            } else if (IncorrectCredentialsException.class.getName().equals(
                    exceptionClassName)) {
//                throw new CustomException("用户名/密码错误");
                log.debug("用户名/密码错误！");
                msg = "用户名/密码错误!";
            } else if("randomCodeError".equals(exceptionClassName)){
                throw new CustomException("验证码错误 ");
            }else {
                throw new Exception();//最终在异常处理器生成未知错误
            }
        }
        //此方法不处理登陆成功（认证成功），shiro认证成功会自动跳转到上一个请求路径
        //登陆失败还到login页面
        map.put("msg",msg);
        return "login";
    }
}
