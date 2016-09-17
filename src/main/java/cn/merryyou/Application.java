package cn.merryyou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@SpringBootApplication
@ComponentScan
@MapperScan("cn.merryyou.mapper")
public class Application extends WebMvcConfigurerAdapter{

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
