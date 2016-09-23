package cn.merryyou.conf.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created on 2016/9/23 0023.
 *
 * @author zlf
 * @since 1.0
 */
@Configuration
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar,EnvironmentAware {
    private static Logger log = LoggerFactory.getLogger(DynamicDataSourceRegister.class);

    private ConversionService conversionService = new DefaultConversionService();

    private Environment environment;

    private PropertyValues dataSourcePropertyValues;
    //默认的数据类型
    private static final Object DATASOURCE_TYPE_DEFAULT = "com.alibaba.druid.pool.DruidDataSource";
    //默认数据源
    private DataSource defaultDataSource;
    //存放DataSource配置的集合
    private Map<String,DataSource> customDataSources  = new HashMap<>();


    @Override
    public void setEnvironment(Environment environment) {
        log.info("DynamicDataSourceRegister.setEnvironment()...");
        /**
         * 获取application.yml配置的多数据源配置，添加到map中，之后在postProcessBeanDefinitionRegistry中进行注册
         *
         */
        this.environment = environment;
        initDefaultDataSource();
        initCustomDataSources();
    }

    /**
     * 初始化更多数据源
     */
    private void initCustomDataSources() {
        RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(environment, "custom.datasource.");
        String dsPrefixs = propertyResolver.getProperty("names");
        for (String dsPrefix : dsPrefixs.split(",")) {// 多个数据源
            Map<String, Object> dsMap = propertyResolver.getSubProperties(dsPrefix);
            Map<String, Object> newDsMap = new HashMap<>();
            //key 会是.username
            for(String key : dsMap.keySet()){
                newDsMap.put(key.substring(1,key.length()),dsMap.get(key));
            }
            DataSource ds = buildDataSource(newDsMap);
            customDataSources.put(dsPrefix, ds);
            dataBinder(ds);
        }
    }

    /**
     * 加载住数据源配置
     */
    private void initDefaultDataSource() {
        RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(environment,
                "spring.datasource.");
        Map<String,Object> dsMap = new HashMap<>();
        dsMap.put("type", propertyResolver.getProperty("type"));
        dsMap.put("driver-class-name", propertyResolver.getProperty("driver-class-name"));
        dsMap.put("url", propertyResolver.getProperty("url"));
        dsMap.put("username", propertyResolver.getProperty("username"));
        dsMap.put("password", propertyResolver.getProperty("password"));

        //创建数据源
        defaultDataSource = buildDataSource(dsMap);
        dataBinder(defaultDataSource);
    }

    /**
     * 为DataSource绑定更多数据
     * @param dataSource
     */
    private void dataBinder(DataSource dataSource) {
        RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
        dataBinder.setConversionService(conversionService);
        dataBinder.setIgnoreNestedProperties(false);//false
        dataBinder.setIgnoreInvalidFields(false);//false
        dataBinder.setIgnoreUnknownFields(true);//true

        if(dataSourcePropertyValues == null){
            Map<String, Object> rpr = new RelaxedPropertyResolver(environment, "spring.datasource").getSubProperties(".");
            Map<String, Object> values = new HashMap<>(rpr);
            // 排除已经设置的属性
            values.remove("type");
            values.remove("driver-class-name");
            values.remove("url");
            values.remove("username");
            values.remove("password");
            dataSourcePropertyValues = new MutablePropertyValues(values);
        }
        dataBinder.bind(dataSourcePropertyValues);
    }

    /**
     * 创建DataSource
     * @param dsMap
     * @return
     */

    public DataSource buildDataSource(Map<String ,Object> dsMap){
        Object type = dsMap.get("type");
        if(type == null){
            type = DATASOURCE_TYPE_DEFAULT;//默认Datasource
        }

//        Class<? extends DataSource> dataSourceType;
//
//        try {
//            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
//            String driverClassName = dsMap.get("driver-class-name").toString();
//            String url = dsMap.get("url").toString();
//            String username = dsMap.get("username").toString();
//            String password = dsMap.get("password").toString();
//            DataSourceBuilder factory =   DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(username).password(password).type(dataSourceType);
//            return factory.build();
//        }catch (ClassNotFoundException e){
//            e.printStackTrace();
//        }
//        return null;
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(dsMap.get("driver-class-name").toString());
        druidDataSource.setUrl(dsMap.get("url").toString());
        druidDataSource.setUsername(dsMap.get("username").toString());
        druidDataSource.setPassword(dsMap.get("password").toString());
        initdruidDataSource(druidDataSource);
        return druidDataSource;
    }
    public void initdruidDataSource(DruidDataSource dataSource){
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(5);
        dataSource.setMaxActive(20);
        dataSource.setMaxWait(60000);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        dataSource.setEnable(true);
    }
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        log.info("DynamicDataSourceRegister.registerBeanDefinitions().........");
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        //将主数据源添加到更多数据源中
        targetDataSources.put("dataSource",defaultDataSource);
        DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
        //添加更多数据源
        targetDataSources.putAll(customDataSources);
        for(String key : customDataSources.keySet()){
            DynamicDataSourceContextHolder.dataSourceIds.add(key);
        }

        //创建DynamicDataSource
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicDataSource.class);

        beanDefinition.setSynthetic(true);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();

        //添加属性：AbstractRoutingDataSource.defaultTargetDataSource
        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
        mpv.addPropertyValue("targetDataSources", targetDataSources);
        registry.registerBeanDefinition("dataSource", beanDefinition);
    }
}
