package cn.merryyou;

import cn.merryyou.entity.News;
import cn.merryyou.mapper.NewsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(Application.class)
public class TestMapper{

	@Autowired
	private NewsMapper newsMapper;
	
	@Test
	public void testSelect() throws Exception{
		News news = newsMapper.selectByPrimaryKey(1);
		System.out.println(news.toString());
	}
}
