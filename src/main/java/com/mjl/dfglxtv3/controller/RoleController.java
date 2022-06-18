package com.mjl.dfglxtv3.controller;

import com.mjl.dfglxtv3.common.Result;
import com.mjl.dfglxtv3.domain.Role;
import com.mjl.dfglxtv3.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * (role)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {
    /**
     * 服务对象
     */
    @Resource
    private RoleService roleService;

    @RequestMapping("/list")
    public Result<List<Role>> list() {
        List<Role> list = roleService.list();
        return Result.success(list);
    }

    @RequestMapping("/checkExist")
    public Result<Boolean> checkExist(String name) {
        roleService.list().stream().filter(role -> role.getName().equals(name)).findFirst().ifPresent(role -> Result.success(true));
        return Result.success(false);
    }




}
