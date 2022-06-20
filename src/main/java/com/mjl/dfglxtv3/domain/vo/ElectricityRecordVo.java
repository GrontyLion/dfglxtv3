package com.mjl.dfglxtv3.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ElectricityRecordVo implements Serializable {

    private Long id;

    private Long dormId;

    private String dormName;

    private Long buildingId;

    private String buildingName;

    private BigDecimal powerConsumption;

    private BigDecimal price;

    private BigDecimal totalPrice;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime recordDate;

    @Serial
    private static final long serialVersionUID = 1L;
}
