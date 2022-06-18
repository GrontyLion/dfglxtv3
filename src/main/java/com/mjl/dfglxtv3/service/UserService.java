package com.mjl.dfglxtv3.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mjl.dfglxtv3.domain.User;
import com.mjl.dfglxtv3.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

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

    public User register(String username, String email, Long dormId, String password) {
        return null;
    }

    public User forget(String username, String email) {
        // 通过用户名或邮箱查找用户
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username)
                .or().eq("email", username);
        User user = baseMapper.selectOne(userQueryWrapper);
        return user;
    }
}
