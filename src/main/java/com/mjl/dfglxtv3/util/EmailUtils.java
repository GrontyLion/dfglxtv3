package com.mjl.dfglxtv3.util;

import com.mjl.dfglxtv3.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@Component
public class EmailUtils {
    private static EmailService emailService;

    @Autowired
    private EmailService emailServiceBean;

    @PostConstruct
    public void init() {
        EmailUtils.emailService = this.emailServiceBean;
    }

    public static void sendEmail(String to, HttpServletRequest request) throws MailException {
        String code = StringUtils.randomAlphanumeric(6);
        emailService.sendSimpleMail(to, "闽南师范大学宿舍电费管理系统", "验证码：" + code);
        request.getSession().setAttribute("emailCode", code);
    }

    public static boolean verifyCode(String code, HttpServletRequest request) {
        String emailCode = (String) request.getSession().getAttribute("emailCode");
        if (emailCode == null) {
            return false;
        }
        return emailCode.equals(code);
    }

    public static void clearCode(HttpServletRequest request) {
        request.getSession().removeAttribute("emailCode");
    }
}
