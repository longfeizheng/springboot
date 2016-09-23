package cn.merryyou.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局一场捕获
 * Created on 2016/9/18 0018.
 *
 * @author zlf
 * @since 1.0
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public @ResponseBody String defaultErrorHandler(HttpServletRequest req , Exception ex){
        //打印异常信息
        ex.printStackTrace();
        System.out.println("GlobalDefaultExceptionHandler.defaultErrorHandler()");
        /*
        * 返回json数据或者String数据：
        * 那么需要在方法上加上注解：@ResponseBody
        * 添加return即可。
        */

       /*
        * 返回视图：
        * 定义一个ModelAndView即可，
        * 然后return;
        * 定义视图文件(比如：error.html,error.ftl,error.jsp);
        *
        */
        //统一异常处理代码
        //针对系统自定义的CustomException异常，就可以直接从异常类中获取异常信息，将异常处理在错误页面展示
        //异常信息
        String message = null;
        CustomException customException = null;
        //如果ex是系统 自定义的异常，直接取出异常信息
        if(ex instanceof CustomException){
            customException = (CustomException)ex;
        }else{
            //针对非CustomException异常，对这类重新构造成一个CustomException，异常信息为“未知错误”
            customException = new CustomException("未知错误");
        }

        //错误 信息
        message = customException.getMessage();

        return message;
    }

}
