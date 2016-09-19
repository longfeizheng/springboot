package cn.merryyou;

import cn.merryyou.entity.News;
import cn.merryyou.mapper.NewsMapper;
import cn.merryyou.util.SpringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * //在所有测试方法前执行一次，一般在其中写上整体初始化的代码
 * @BeforeClass
 * @AfterClass
 * //在每个测试方法前执行，一般用来初始化方法（比如我们在测试别的方法时，类中与其他测试方法共享的值已经被改变，为了保证测试结果的有效性，我们会在@Before注解的方法中重置数据）
 * @Before
 * //在每个测试方法后执行，在方法执行完成后要做的事情
 * @After
 * // 测试方法执行超过1000毫秒后算超时，测试将失败
 * @Test(timeout = 1000)
 * // 测试方法期望得到的异常类，如果方法执行没有抛出指定的异常，则测试失败
 * @Test(expected = Exception.class)
 * // 执行测试时将忽略掉此方法，如果用于修饰类，则忽略整个类
 * @Ignore(“not ready yet”)
 * @Test
 * @RunWith
 * 在JUnit中有很多个Runner，他们负责调用你的测试代码，每一个Runner都有各自的特殊功能，你要根据需要选择不同的Runner来运行你的测试代码。
 * 如果我们只是简单的做普通Java测试，不涉及Spring Web项目，你可以省略@RunWith注解，这样系统会自动使用默认Runner来运行你的代码。
 * The type Test mapper.
 */
@SuppressWarnings("deprecation")
//SpringJUnit支持，由此引入Spring-Test框架支持
@RunWith(SpringJUnit4ClassRunner.class)
//由于是web项目，JUNIT需要模拟ServletContext
@WebAppConfiguration
//指定我们SpringBoot工程的Application启动类
@SpringApplicationConfiguration(Application.class)
public class TestMapper{

//	@Autowired
//	private NewsMapper newsMapper;
	
	@Test
	public void testSelect() throws Exception{
		//利用SpringUtil 获得Spring IOC容器中的bean
		NewsMapper newsMapper = (NewsMapper) SpringUtil.getBean("newsMapper");
		News news = newsMapper.selectByPrimaryKey(1);
		System.out.println(news.toString());
	}
}
