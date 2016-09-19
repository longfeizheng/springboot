package cn.merryyou;

import cn.merryyou.properties.MerryYouProperties;
import cn.merryyou.properties.MerryYouSettings;
import cn.merryyou.servlet.MyServlet;
import cn.merryyou.util.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@SpringBootApplication
@MapperScan("cn.merryyou.mapper")
@ServletComponentScan
@EnableConfigurationProperties({MerryYouSettings.class, MerryYouProperties.class})
public class Application extends WebMvcConfigurerAdapter{

	@Bean
	public SpringUtil springUtil(){
		return new SpringUtil();
	}

	//spring boot servlet
	@Bean
	public ServletRegistrationBean myServlet(){
		return new ServletRegistrationBean(new MyServlet(),"/myServlet/*");
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
