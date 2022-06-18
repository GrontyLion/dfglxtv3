package com.mjl.dfglxtv3.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@TableName(value = "`user`")
public class User implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "dorm_id")
    private Long dormId;

    @TableField(value = "email")
    private String email;

    @TableField(value = "`name`")
    private String name;

    @TableField(value = "`password`")
    private String password;

    @TableField(value = "role_id")
    private Long roleId;

    @TableField(value = "username")
    private String username;

    @Serial
    private static final long serialVersionUID = 1L;
}