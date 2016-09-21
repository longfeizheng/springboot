package cn.merryyou.mapper;

import cn.merryyou.entity.SysPermission;

import java.util.List;

/**
 * Created on 2016/9/21 0021.
 *
 * @author zlf
 * @since 1.0
 */
public interface SysPermissionMapperCustom {
    //根据用户id查询菜单
     List<SysPermission> findMenuListByUserId(String userid)throws Exception;
    //根据用户id查询权限url
     List<SysPermission> findPermissionListByUserId(String userid)throws Exception;
}
