package cn.merryyou.service.impl;

import cn.merryyou.entity.News;
import cn.merryyou.mapper.NewsMapper;
import cn.merryyou.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2016/9/17 0017.
 *
 * @author zlf
 * @since 1.0
 */
@Service
public class NewServiceImpl implements NewService {

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public News findOne(Integer id) {
        return newsMapper.selectByPrimaryKey(id);
    }
}
