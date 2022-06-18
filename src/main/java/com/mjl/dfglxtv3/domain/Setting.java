package com.mjl.dfglxtv3.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@TableName(value = "setting")
public class Setting implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "`name`")
    private String name;

    @TableField(value = "`value`")
    private String value;

    @Serial
    private static final long serialVersionUID = 1L;
}