package cn.merryyou.service;

import cn.merryyou.conf.MyMapper;
import cn.merryyou.entity.BaseEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * Created on 2016/9/18 0018.
 *
 * @author zlf
 * @since 1.0
 */
public abstract class BaseService <T extends BaseEntity>{
    @Autowired
    private MyMapper<T> mapper;

    private Class<?> clazz;

    public Mapper<T> getMapper(){
        return this.mapper;
    };

    @SuppressWarnings("unchecked")
    public BaseService() {
        Type type = this.getClass().getGenericSuperclass();

        ParameterizedType ptype = (ParameterizedType) type;

        this.clazz = (Class<T>) ptype.getActualTypeArguments()[0];

    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    public T queryById(Long id) {
        return this.getMapper().selectByPrimaryKey(id);
    }

    /**
     * 查询全部
     *
     * @return
     */
    public List<T> queryAll() {
        return this.getMapper().select(null);
    }

    /**
     * 根据条件查询
     *
     * @param t
     * @return
     */
    public List<T> queryListByWhere(T t) {
        return this.getMapper().select(t);
    }

    /**
     * 根据条件分页查询
     *
     * @param t
     * @param page
     * @param rows
     * @return
     */
    public PageInfo<T> queryListByPageAndWhere(T t, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);

        List<T> list = this.getMapper().select(t);

        return new PageInfo<T>(list);
    }

    /**
     * 查询数据总条数
     *
     * @return
     */
    public long queryAllCount() {
        return this.getMapper().selectCount(null);
    }

    /**
     * 根据条件查询一条数据
     *
     * @param t
     * @return
     */
    public T queryOne(T t) {
        return this.getMapper().selectOne(t);
    }

    /**
     * 保存
     *
     * @param t
     * @return
     */
    public Integer save(T t) {
        if(t.getCreateTime()==null){
            t.setCreateTime(new Date());
            t.setUpdateTime(t.getCreateTime());
        }else{
            t.setUpdateTime(t.getCreateTime());
        }
        return this.getMapper().insert(t);
    }

    /**
     * 保存忽略null
     *
     * @param t
     * @return
     */
    public Integer saveSelective(T t) {
        return this.getMapper().insertSelective(t);
    }

    /**
     * 更新
     *
     * @param t
     * @return
     */
    public Integer update(T t) {
        t.setUpdateTime(new Date());
        return this.getMapper().updateByPrimaryKey(t);
    }

    /**
     * 更新忽略null
     *
     * @param t
     * @return
     */
    public Integer updateSelective(T t) {
        t.setUpdateTime(new Date());
        return this.getMapper().updateByPrimaryKeySelective(t);
    }

    /**
     * 删除通过主键
     *
     * @param id
     * @return
     */
    public Integer deleteById(Long id) {
        return this.getMapper().deleteByPrimaryKey(id);
    }

    public Integer deleteByIds(List<Object> ids) {
        Example example = new Example(this.clazz);
        example.createCriteria().andIn("id", ids);

        return this.getMapper().deleteByExample(example);
    }
    /**
     * 通用分页查询
     *
     * @param t
     * @param page
     * @param rows
     * @param order
     * @return
     * @throws Exception
     */
    public PageInfo<T> queryListByPage(T t, Integer page, Integer rows, String order)
            throws Exception {
        PageHelper.startPage(page, rows);

        // 声明example
        Example example = new Example(this.clazz);

        // 判断order为非空
        if (StringUtils.isNotBlank(order)) {
            // 加入排序
            example.setOrderByClause(order);
        }

        if (t != null) {
            // 声明条件
            Example.Criteria criteria = example.createCriteria();

            // 获取field
            Field[] fields = t.getClass().getDeclaredFields();

            for (Field field : fields) {
                // 声明，获取私有属性的值
                field.setAccessible(true);

                // 判断field的值是否为非空
                if (field.get(t) != null) {
                    // 如果非空，这设置为查询条件
                    criteria.andEqualTo(field.getName(), field.get(t));
                }
            }

        }

        // 查询
        List<T> list = this.getMapper().selectByExample(example);
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        return pageInfo;

    }

    public int queryCountByWhere(T t) {
        return this.queryListByWhere(t).size();
    }

}
