package com.mjl.dfglxtv3.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mjl.dfglxtv3.domain.*;
import com.mjl.dfglxtv3.domain.vo.UserVO;
import com.mjl.dfglxtv3.mapper.DormMapper;
import com.mjl.dfglxtv3.mapper.SettingMapper;
import com.mjl.dfglxtv3.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class UserService extends ServiceImpl<UserMapper, User> {

    @Resource
    private DormMapper dormMapper;

    @Resource
    private SettingMapper settingMapper;

    @Resource
    private BuildingService buildingService;

    @Resource
    private RoleService roleService;


    @Resource
    private DormTypeService dormTypeService;

    public User login(String username, String password) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username)
                .or().eq("email", username);
        User user = baseMapper.selectOne(userQueryWrapper);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User register(String username, String name, String email, Long dormId, String password) {
        // 先判断用户名是否存在
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username)
                .or().eq("email", email);
        User user = baseMapper.selectOne(userQueryWrapper);
        if (user != null) {
            return null;
        }
        // 判断宿舍是否存在
        QueryWrapper<Dorm> dormQueryWrapper = new QueryWrapper<>();
        dormQueryWrapper.eq("id", dormId);
        Dorm dorm = dormMapper.selectOne(dormQueryWrapper);
        if (dorm == null) {
            return null;
        }

        // 从settingMapper 中获取默认角色，name为”默认角色“
        QueryWrapper<Setting> settingQueryWrapper = new QueryWrapper<>();
        settingQueryWrapper.eq("name", "默认角色");
        Setting setting = settingMapper.selectOne(settingQueryWrapper);
        if (setting == null) {
            return null;
        }
        user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setEmail(email);
        user.setDormId(dormId);
        user.setRoleId(Long.valueOf(setting.getValue()));
        user.setPassword(password);
        int insert = baseMapper.insert(user);
        if (insert == 1) {
            return user;
        }
        return null;
    }

    public User forget(String username, String email) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username)
                .eq("email", email);
        return baseMapper.selectOne(userQueryWrapper);
    }

    public boolean reset(Long id, String password) {
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        int update = baseMapper.updateById(user);
        return update == 1;
    }

    public List<UserVO> pageInfo(Page<User> page1, QueryWrapper<User> userQueryWrapper, String buildingId) {
        List<User> users = this.page(page1, userQueryWrapper).getRecords();
        return users.stream().filter(user -> {
            if (buildingId == null || buildingId.equals("")) {
                return true;
            }
            Dorm dorm = dormMapper.selectById(user.getDormId());
            Building building = buildingService.getById(dorm.getBuildingId());
            System.out.println(building.getId().toString());
            return building.getId().toString().equals(buildingId);
        }).map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            Dorm dorm = dormMapper.selectById(user.getDormId());
            Role role = roleService.getById(user.getRoleId());
            Building building = buildingService.getById(dorm.getBuildingId());
            userVO.setDormName(dorm.getName());
            userVO.setRoleName(role.getName());
            userVO.setBuildingName(building.getName());
            return userVO;
        }).toList();
    }
}
