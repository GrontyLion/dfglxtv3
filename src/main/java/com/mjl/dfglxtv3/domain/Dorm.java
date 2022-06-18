package com.mjl.dfglxtv3.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName(value = "dorm")
public class Dorm implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "building_id")
    private Long buildingId;

    @TableField(value = "deposit")
    private BigDecimal deposit;

    @TableField(value = "dorm_type_id")
    private Long dormTypeId;

    @TableField(value = "`name`")
    private String name;

    @Serial
    private static final long serialVersionUID = 1L;
}