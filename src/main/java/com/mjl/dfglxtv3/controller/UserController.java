package com.mjl.dfglxtv3.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.mjl.dfglxtv3.common.PasswordEncoder;
import com.mjl.dfglxtv3.common.Result;
import com.mjl.dfglxtv3.common.ResultCode;
import com.mjl.dfglxtv3.domain.User;
import com.mjl.dfglxtv3.service.UserService;
import com.mjl.dfglxtv3.util.EmailUtils;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * (user)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/api/user")
public class UserController {


    @Resource
    private HttpServletRequest request;
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
    public Result<String> register(String username, String password, String email, Long dormId, String code) {
        if (EmailUtils.verifyCode(code, request)) {
            EmailUtils.clearCode(request);
            password = PasswordEncoder.encode(password);
            User user = userService.register(username, email, dormId, password);
            if (user != null) {
                StpUtil.login(user.getId());
                return Result.success("注册成功");
            } else {
                return Result.failed(ResultCode.USERNAME_OR_PASSWORD_ERROR);
            }
        } else {
            return Result.failed(ResultCode.EMAIL_VERIFY_CODE_ERROR);
        }
    }

    @PostMapping("/forget")
    @ResponseBody
    public Result<String> forget(String username, String email, String code) {
        if (EmailUtils.verifyCode(code, request)) {
            EmailUtils.clearCode(request);
            User user = userService.forget(username, email);
            if (user != null) {
                return Result.success("找回成功");
            } else {
                return Result.failed(ResultCode.USERNAME_OR_PASSWORD_ERROR);
            }
        } else {
            return Result.failed(ResultCode.EMAIL_VERIFY_CODE_ERROR);
        }
    }


}
