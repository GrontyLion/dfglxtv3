package com.mjl.dfglxtv3.domain.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;

@Data
public class UserVO implements Serializable {

    /**
     * 用户id
     */
    private Long id;


    /**
     * 用户名（学号）
     */
    @Pattern(regexp = "^[0-9a-zA-Z]{8,16}$", message = "用户名只能包含数字和字母，长度为8-16位")
    private String username;


    /**
     * 姓名
     */
    @Pattern(regexp = "^[\u4E00-\u9FA5]{2,4}$", message = "姓名只能包含汉字，长度为2-4位")
    private String name;


    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    private String email;


    /**
     * 密码
     */
    @Pattern(regexp = "^[^\\s]{8,255}$", message = "密码只能包含非空格的字符，长度最少8位")
    private String password;


    /**
     * 宿舍id
     */
    @NotNull(message = "宿舍id不能为空")
    private Long dormId;


    /**
     * 角色id
     */
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    @Serial
    private static final long serialVersionUID = 1L;
}
