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
@TableName(value = "recharge_record")
public class RechargeRecord implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "amount")
    private BigDecimal amount;

    @TableField(value = "dorm_id")
    private Long dormId;

    @TableField(value = "payment_time")
    private LocalDateTime paymentTime;

    @TableField(value = "user_id")
    private Long userId;

    @Serial
    private static final long serialVersionUID = 1L;
}