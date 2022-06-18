package com.mjl.dfglxtv3.controller;

import com.mjl.dfglxtv3.common.Result;
import com.mjl.dfglxtv3.common.ResultCode;
import com.mjl.dfglxtv3.util.EmailUtils;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;

/**
 * (user)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/api/email")
public class EmailController {


    @Resource
    private HttpServletRequest request;


    @PostMapping("/send")
    @ResponseBody
    public Result<String> send(@Email String email) {
        try {
            EmailUtils.sendEmail(email, request);
        } catch (MailException e) {
            e.printStackTrace();
            return Result.failed(ResultCode.EMAIL_SEND_FAILED);
        }
        return Result.success("邮件发送成功");
    }

}
