package cn.merryyou.service;

import cn.merryyou.entity.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * Created on 2016/9/17 0017.
 *
 * @author zlf
 * @since 1.0
 */
@Service
public class NewService extends BaseService<News> {

    private static Logger log = LoggerFactory.getLogger(NewService.class);

    @Cacheable(value = "newsCache",key = "#id + 'findOne'")
    public News findOne(Integer id){
        log.debug("NewService.findOne()=========从数据库中进行获取的....id="+id);
        News news = new News();
        news.setId(id);
        return super.queryOne(news);
    }
//    #user.userId SpEL（Spring Expression Language）表达式
    @CacheEvict(value = "newsCache",key = "#id + 'findOne'",allEntries = true)
    public void deleteFromCache(Integer id){
        log.debug("NewService.findOne()=========从缓存中删除....id="+id);
    }

    public void test(){
        ValueOperations<String,String> valueOperations = super.getRedisTemplate().opsForValue();
        valueOperations.set("mykey4", "random1="+Math.random());
        log.debug(valueOperations.get("mykey4"));
    }
}
