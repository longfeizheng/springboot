package cn.merryyou.controller;

import cn.merryyou.entity.RestUser;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 请求类型             URL             工程说明
 * GET                 /users          查询用户列表
 * POST                /users          创建一个用户
 * GET                 /users/id       根据ID查询一个用户
 * PUT                 /users/id       根据ID更新一个用户
 * DELETE              /users/id       根据ID删除一个用户
 * Created on 2016/9/22 0022.
 *
 * @author zlf
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/users")
public class RestUserController {
    //为了方便测试，直接将数据存储在map中，实际需从数据库中取数据
    private static Map<Integer, RestUser> users = Collections.synchronizedMap(new HashMap<Integer, RestUser>());

    /**
     * Get user list list.
     *
     * @return the list
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<RestUser> getUserList() {
        List<RestUser> list = new ArrayList<>(users.values());
        return list;
    }

    /**
     * Gets user.
     *
     * @param id the id
     * @return the user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestUser getUser(@PathVariable(value = "id") Integer id) {
        return users.get(id);
    }

    /**
     * Post user string.
     *
     * @param user the user
     * @return the string
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postUser(RestUser user) {
        users.put(user.getId(), user);
        return "success";
    }

    /**
     * Put user string.
     *
     * @param id   the id
     * @param user the user
     * @return the string
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putUser(@PathVariable(value = "id")Integer id,RestUser user){
        RestUser u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id,u);
        return "success";
    }

    /**
     * Delete user string.
     *
     * @param id the id
     * @return the string
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable(value = "id") Integer id){
        users.remove(id);
        return "success";
    }
}
