package cn.merryyou.service;

import cn.merryyou.entity.SysPermission;
import cn.merryyou.entity.SysUser;

import java.util.List;

/**
 * Created on 2016/9/21 0021.
 *
 * @author zlf
 * @since 1.0
 */
public interface SysService {
    //根据用户账号查询用户信息
    SysUser findSysUserByUserCode(String userCode) throws Exception;

    //根据用户id查询权限范围的菜单
    public List<SysPermission> findMenuListByUserId(String userid) throws Exception;

    //根据用户id查询权限范围的url
    public List<SysPermission> findPermissionListByUserId(String userid) throws Exception;

}
