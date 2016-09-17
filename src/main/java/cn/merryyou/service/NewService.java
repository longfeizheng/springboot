package cn.merryyou.service;

import cn.merryyou.entity.News;

/**
 * Created on 2016/9/17 0017.
 *
 * @author zlf
 * @since 1.0
 */
public interface NewService {
    News findOne(Integer id);
}
