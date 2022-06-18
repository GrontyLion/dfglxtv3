package com.mjl.dfglxtv3.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName(value = "electricity_record")
public class ElectricityRecord implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "dorm_id")
    private Long dormId;

    @TableField(value = "power_consumption")
    private BigDecimal powerConsumption;

    @TableField(value = "price")
    private BigDecimal price;

    @TableField(value = "record_date")
    private LocalDateTime recordDate;

    @Serial
    private static final long serialVersionUID = 1L;
}