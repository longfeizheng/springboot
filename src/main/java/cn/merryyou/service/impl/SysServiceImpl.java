package cn.merryyou.service.impl;

import cn.merryyou.entity.SysPermission;
import cn.merryyou.entity.SysUser;
import cn.merryyou.entity.SysUserExample;
import cn.merryyou.mapper.SysPermissionMapperCustom;
import cn.merryyou.mapper.SysUserMapper;
import cn.merryyou.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2016/9/21 0021.
 *
 * @author zlf
 * @since 1.0
 */
@Service
public class SysServiceImpl implements SysService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysPermissionMapperCustom sysPermissionMapperCustom;

    @Override
    public SysUser findSysUserByUserCode(String userCode) throws Exception{
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria().andUsercodeEqualTo(userCode);
        List<SysUser> sysUsers = sysUserMapper.selectByExample(example);
        if(sysUsers!=null&&sysUsers.size()>0){
            return sysUsers.get(0);
        }
        return null;
    }

    @Override
    public List<SysPermission> findMenuListByUserId(String userid) throws Exception {
        return sysPermissionMapperCustom.findMenuListByUserId(userid);
    }

    @Override
    public List<SysPermission> findPermissionListByUserId(String userid) throws Exception {
        return sysPermissionMapperCustom.findPermissionListByUserId(userid);
    }
}
