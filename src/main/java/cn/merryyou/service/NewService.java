package cn.merryyou.service;

import cn.merryyou.entity.News;
import org.springframework.stereotype.Service;

/**
 * Created on 2016/9/17 0017.
 *
 * @author zlf
 * @since 1.0
 */
@Service
public class NewService extends BaseService<News> {
    public News findOne(Integer id){
        News news = new News();
        news.setId(id);
        return super.queryOne(news);
    }
}
