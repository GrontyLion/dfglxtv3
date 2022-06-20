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
        request.getSession().setAttribute("emailTime", System.currentTimeMillis());
    }

    public static void sendEmail(String to, String title, String content) throws MailException {
        emailService.sendSimpleMail(to, title, content);
    }

    public static boolean verifyCode(String code, HttpServletRequest request) {
        String emailCode = (String) request.getSession().getAttribute("emailCode");
        Long emailTime = (Long) request.getSession().getAttribute("emailTime");
        if (emailCode == null) {
            return false;
        }
        if (System.currentTimeMillis() - emailTime > 1000 * 60 * 5) {
            return false;
        }
        return emailCode.equals(code);
    }

    public static void clearCode(HttpServletRequest request) {
        request.getSession().removeAttribute("emailCode");
        request.getSession().removeAttribute("emailTime");
    }
}
