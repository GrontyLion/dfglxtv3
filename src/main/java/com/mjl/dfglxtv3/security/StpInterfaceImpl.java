package com.mjl.dfglxtv3.security;

import cn.dev33.satoken.stp.StpInterface;
import com.mjl.dfglxtv3.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限验证接口扩展
 */
@Slf4j
@Component    // 保证此类被SpringBoot扫描，完成Sa-Token的自定义权限验证扩展 
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private RoleService roleService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> permissionList = new ArrayList<>();
        log.error("loginId: {}", loginId);
        permissionList.add(roleService.getById(Long.parseLong((String) loginId)).getName());
        return permissionList;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> roleList = new ArrayList<>();
        roleList.add(roleService.getById((Long) loginId).getName());
        return roleList;
    }

}
