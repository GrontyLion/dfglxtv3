package com.mjl.dfglxtv3.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@TableName(value = "building")
public class Building implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "electrovalency_type_id")
    private Long electrovalencyTypeId;

    @TableField(value = "`name`")
    private String name;

    @Serial
    private static final long serialVersionUID = 1L;
}