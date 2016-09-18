package cn.merryyou;

import cn.merryyou.entity.News;
import cn.merryyou.mapper.NewsMapper;
import cn.merryyou.util.SpringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
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
