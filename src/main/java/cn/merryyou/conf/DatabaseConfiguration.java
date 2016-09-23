package cn.merryyou.conf;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.MultipartConfigElement;

/**
 * 凡是被Spring管理的类，实现接口 EnvironmentAware
 * 重写方法 setEnvironment 可以在工程启动时，获取到系统环境变量和application配置文件中的变量。
 * The type Database configuration.
 * @Value("${spring.datasource.url}")
 */
@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration implements EnvironmentAware{
	private static Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

	private Environment environment;
    private RelaxedPropertyResolver datasourcePropertyResolver;
    @Value("${spring.http.multipart.max-file-size}")
    private String maxFileSize;
    @Value("${spring.http.multipart.max-request-size}")
    private String maxRequestSize;
    @Value("${spring.http.multipart.location}")
    private String fileLocation;

    //从application.yml中读取资源
	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
		this.datasourcePropertyResolver = new RelaxedPropertyResolver(environment,
                "spring.datasource.");
	}

//	@Bean(initMethod = "init", destroyMethod = "close")
//    public DataSource dataSource() {
//        log.debug("Configruing DataSource");
//        if (StringUtil.isEmpty(datasourcePropertyResolver.getProperty("url"))) {
//            log.error("Your database conncetion pool configuration is incorrct ! The application "
//                    + "cannot start . Please check your jdbc");
//            Arrays.toString(environment.getActiveProfiles());
//            throw new ApplicationContextException(
//                    "DataBase connection pool is not configured correctly");
//        }
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setUrl(datasourcePropertyResolver.getProperty("url"));
//        druidDataSource.setUsername(datasourcePropertyResolver.getProperty("username"));
//        druidDataSource.setPassword(datasourcePropertyResolver.getProperty("password"));
//        druidDataSource.setInitialSize(5);
//        druidDataSource.setMinIdle(5);
//        druidDataSource.setMaxActive(20);
//        druidDataSource.setMaxWait(60000);
//        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
//        druidDataSource.setMinEvictableIdleTimeMillis(300000);
//        druidDataSource.setTestWhileIdle(true);
//        druidDataSource.setTestOnBorrow(false);
//        druidDataSource.setTestOnReturn(false);
//        druidDataSource.setPoolPreparedStatements(true);
//        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
//        druidDataSource.setEnable(true);
//        return druidDataSource;
//    }

    @Bean
    public ServletRegistrationBean DruidStatViewServle(){
        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");

        //添加初始化参数 initParams
        //白名单
        servletRegistrationBean.addInitParameter("allow","127.0.0.1");
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny","");
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername","admin");
        servletRegistrationBean.addInitParameter("loginPassword","123456");
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable","false");

        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean druidStatFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());

        //添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    /**
     * 监听Spring
     *  1.定义拦截器
     *  2.定义切入点
     *  3.定义通知类
     * @return
     */
    @Bean
    public DruidStatInterceptor druidStatInterceptor(){
        return new DruidStatInterceptor();
    }
    @Bean
    public JdkRegexpMethodPointcut druidStatPointcut(){
        JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
        String patterns = "cn.merryyou.service.*";
        String patterns2 = "cn.merryyou.mapper.*";
        druidStatPointcut.setPatterns(patterns,patterns2);
        return druidStatPointcut;
    }
    @Bean
    public Advisor druidStatAdvisor() {
        return new DefaultPointcutAdvisor(druidStatPointcut(), druidStatInterceptor());
    }

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(this.maxFileSize);
        factory.setMaxRequestSize(this.maxRequestSize);
        factory.setLocation(this.fileLocation);
        return factory.createMultipartConfig();
    }
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter4 fastConverter = new FastJsonHttpMessageConverter4();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }
}
