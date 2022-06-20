package com.mjl.dfglxtv3.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RechargeRecordVo implements Serializable {

    private Long id;

    private BigDecimal amount;

    private Long dormId;

    private String dormName;

    private Long buildingId;

    private String buildingName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime paymentTime;

    private Long userId;

    private String username;

    @Serial
    private static final long serialVersionUID = 1L;
}
