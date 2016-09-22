package cn.merryyou;

import cn.merryyou.controller.RestUserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created on 2016/9/22 0022.
 *
 * @author zlf
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class RestUserControllerTest extends MockMvcResultHandlers {
    private static Logger log = LoggerFactory.getLogger(RestUserControllerTest.class);
    //模拟mvc对象类
    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new RestUserController()).build();
    }

    @Test
    public void testRestUserController() throws Exception {
        RequestBuilder request = null;

        //模拟pust登录 （因为增加shrio认证授权）
        request = MockMvcRequestBuilders.post("/login")
                .param("username","lisi")
                .param("password","111111");
        mvc.perform(request);

        //get 一下user列表 ，应该为空
        //构建get请求
        request = MockMvcRequestBuilders.get("/users");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
        log.info("RestUserControllerTest.testRestUserController().get");

        //post提交一个user
        request = MockMvcRequestBuilders.post("/users")
                                        .param("id","1")
                                        .param("name","林峰")
                                        .param("age","20");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string("success"));

        //get 获取user列表，应该是刚才插入的数据
        request = MockMvcRequestBuilders.get("/users");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string("[{\"id\":1,\"name\":\"林峰\",\"age\":20}]"));

        // put修改id为1的user
        request = MockMvcRequestBuilders.put("/users/1")
                .param("name", "林则徐")
                .param("age", "30");
        mvc.perform(request)
                .andExpect(content().string("success"));

        // get一个id为1的user
        request = MockMvcRequestBuilders.get("/users/1");
        mvc.perform(request)
                .andExpect(content().string("{\"id\":1,\"name\":\"林则徐\",\"age\":30}"));
        // del删除id为1的user
        request = MockMvcRequestBuilders.delete("/users/1");
        mvc.perform(request)
                .andExpect(content().string("success"));

        // get查一下user列表，应该为空
        request = MockMvcRequestBuilders.get("/users");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }
}
