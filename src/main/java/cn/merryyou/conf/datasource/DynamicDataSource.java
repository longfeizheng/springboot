package cn.merryyou.conf.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created on 2016/9/23 0023.
 *
 * @author zlf
 * @since 1.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource{


    /**
     * 代码中的determineCurrentLookupKey方法取得一个字符串，
     * 该字符串将与配置文件中的相应字符串进行匹配以定位数据源，配置文件，即applicationContext.xml
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
