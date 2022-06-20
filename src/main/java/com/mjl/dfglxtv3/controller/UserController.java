package com.mjl.dfglxtv3.controller;

import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.temp.SaTempUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mjl.dfglxtv3.common.PasswordEncoder;
import com.mjl.dfglxtv3.common.Result;
import com.mjl.dfglxtv3.common.ResultCode;
import com.mjl.dfglxtv3.domain.User;
import com.mjl.dfglxtv3.domain.vo.UserVO;
import com.mjl.dfglxtv3.service.UserService;
import com.mjl.dfglxtv3.util.EmailUtils;
import com.wf.captcha.utils.CaptchaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (user)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {


    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;


    @PostMapping("/login")
    @ResponseBody
    public Result<String> login(String username, String password, String code) {
        if (CaptchaUtil.ver(code, request)) {
            CaptchaUtil.clear(request);
            password = PasswordEncoder.encode(password);
            User user = userService.login(username, password);
            if (user != null) {
                StpUtil.login(user.getId());
                return Result.success("登录成功");
            } else {
                return Result.failed(ResultCode.USERNAME_OR_PASSWORD_ERROR);
            }
        } else {
            return Result.failed(ResultCode.CAPTCHA_ERROR);
        }
    }

    @PostMapping("/logout")
    @ResponseBody
    public Result<String> logout() {
        StpUtil.logout();
        return Result.success("退出成功");
    }

    @PostMapping("/register")
    @ResponseBody
    public Result<String> register(String username, String name, String password, String email, Long dormId, String code) {
        if (EmailUtils.verifyCode(code, request)) {
            EmailUtils.clearCode(request);
            password = PasswordEncoder.encode(password);
            User user = userService.register(username, name, email, dormId, password);
            if (user != null) {
                StpUtil.login(user.getId());
                return Result.success("注册成功");
            } else {
                return Result.failed(ResultCode.USERNAME_OR_EMAIL_EXIST);
            }
        } else {
            return Result.failed(ResultCode.EMAIL_VERIFY_CODE_ERROR);
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public Result<String> add(@RequestBody User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", user.getUsername());
        userQueryWrapper.or().eq("email", user.getEmail());
        if (userService.count(userQueryWrapper) > 0) {
            return Result.failed(ResultCode.USERNAME_OR_EMAIL_EXIST);
        }
        String  password = PasswordEncoder.encode("123456");
        user.setPassword(password);
        boolean b = userService.save(user);
        if (b) {
            return Result.success("添加成功，默认密码为123456，请尽快通知用户修改密码");
        } else {
            return Result.failed(ResultCode.USERNAME_OR_EMAIL_EXIST);
        }
    }

    @PostMapping("/forget")
    @ResponseBody
    public Result<String> forget(String username, String email, String code) {
        if (EmailUtils.verifyCode(code, request)) {
            EmailUtils.clearCode(request);
            User user = userService.forget(username, email);
            if (user != null) {
                // 向用户发送邮件，提示重置密码
                String token = SaTempUtil.createToken(user.getId(), 60 * 5);
                EmailUtils.sendEmail(email, "重置密码", "请点击链接重置密码, 链接有效期为5分钟：http://localhost:8080/reset?token=" + token);
                return Result.success("已发送重置密码链接至该邮件");
            } else {
                return Result.failed(ResultCode.USERNAME_OR_PASSWORD_ERROR);
            }
        } else {
            return Result.failed(ResultCode.EMAIL_VERIFY_CODE_ERROR);
        }
    }

    @PostMapping("/reset")
    @ResponseBody
    public Result<String> reset(String token, String password) throws ServletException, IOException {
        Long aLong = SaTempUtil.parseToken(token, Long.class);
        if (aLong != null) {
            if (password == null) {
                return Result.failed(ResultCode.PASSWORD_NULL);
            }
            password = PasswordEncoder.encode(password);
            boolean b = userService.reset(aLong, password);
            if (b) {
                SaTempUtil.deleteToken(token);
                return Result.success("重置密码成功");
            } else {
                return Result.failed(ResultCode.PASSWORD_RESET_ERROR);
            }
        }
        throw new NotPermissionException("你没有权限访问该页面, 或者链接已失效");
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(String username, String name, String dormId ,String roleId, String email, String buildingId, String page, String limit) {
        Map<String, Object> map = new HashMap<>();
        Page<User> page1 = new Page<>();
        page1.setCurrent(Integer.parseInt(page));
        page1.setSize(Integer.parseInt(limit));
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (username != null && !username.equals("")) {
            userQueryWrapper.like("username", username);
        }
        if (name != null && !name.equals("")) {
            userQueryWrapper.like("name", name);
        }
        if (dormId != null && !dormId.equals("")) {
            userQueryWrapper.eq("dorm_id", dormId);
        }
        if (roleId != null && !roleId.equals("")) {
            userQueryWrapper.eq("role_id", roleId);
        }
        if (email != null && !email.equals("")) {
            userQueryWrapper.like("email", email);
        }
        userQueryWrapper.orderByDesc("id");
        List<UserVO> userVOS = userService.pageInfo(page1, userQueryWrapper, buildingId);
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", userVOS.size());
        map.put("data", userVOS);
        return map;
    }

    @PostMapping("/update")
    @ResponseBody
    public Result<String> update(@RequestBody User user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", user.getUsername());
        userQueryWrapper.or().eq("email", user.getEmail());
        userQueryWrapper.ne("id", user.getId());
        if (userService.count(userQueryWrapper) > 0) {
            return Result.failed(ResultCode.USERNAME_OR_EMAIL_EXIST);
        }
        boolean b = userService.updateById(user);
        if (b) {
            return Result.success("修改成功");
        } else {
            return Result.failed(ResultCode.USERNAME_OR_EMAIL_EXIST);
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public Result<String> delete(String id) {
        boolean b = userService.removeById(id);
        if (b) {
            return Result.success("删除成功");
        } else {
            return Result.failed(ResultCode.USERNAME_OR_EMAIL_EXIST);
        }
    }

    @PostMapping("/batchRemove")
    @ResponseBody
    public Result<String> batchRemove(@RequestBody List<Long> ids) {
        boolean b = userService.removeByIds(ids);
        if (b) {
            return Result.success("删除成功");
        } else {
            return Result.failed("删除失败");
        }
    }

}
