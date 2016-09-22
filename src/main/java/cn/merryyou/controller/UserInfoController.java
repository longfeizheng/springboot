package cn.merryyou.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 2016/9/22 0022.
 *
 * @author zlf
 * @since 1.0
 */
@Controller
@RequestMapping(value = "/userInfo")
public class UserInfoController {
    /**
     * 用户查询.
     * @return
     */
    @RequestMapping("/userList")
    @RequiresPermissions("user:query")//权限管理
    public String userInfo(){
        return "userInfo/userInfo";
    }

    /**
     * 用户添加;
     * @return
     */
    @RequestMapping("/userAdd")
    @RequiresPermissions("user:create")//权限管理
    public String userInfoAdd(){
        return "userInfo/userInfoAdd";
    }
}
