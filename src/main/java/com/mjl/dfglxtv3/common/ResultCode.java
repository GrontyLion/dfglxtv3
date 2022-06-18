package com.mjl.dfglxtv3.common;

public enum ResultCode {

    SUCCESS(200, "请求成功"),
    FAILED(1000, "操作失败"),
    USERNAME_OR_PASSWORD_ERROR(1001, "用户名或密码错误"),
    USERNAME_EXIST(1002, "用户名已存在"),
    USERNAME_NOT_EXIST(1003, "用户名不存在"),

    CAPTCHA_ERROR(1004, "验证码错误"),
    TOKEN_FAILED(10002, "token失效"),

    USER_NOT_LOGIN(1005, "用户未登录"),
    ROLE_NOT_MATCH(1006, "角色不匹配"),
    PERMISSION_DENIED(1007, "权限不足"),
    USER_BE_LOCKED(1008, "用户被锁定"),
    USER_BE_DISABLED(1009, "用户被禁用"),
    USER_BE_DELETED(1010, "用户被删除"),
    USER_BE_EXPIRED(1011, "用户过期"),
    NOT_PERMISSION(1012, "没有权限"),
    ROLE_EXIST(1013, "角色已存在"),
    NONE(99999, "无"),
    EMAIL_SEND_FAILED(1014, "邮件发送失败"),
    EMAIL_VERIFY_CODE_ERROR(1015, "邮件验证码错误");


    private int code;
    private String msg;

    private ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
