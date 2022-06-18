package com.mjl.dfglxtv3.domain.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class DormVo implements Serializable {

    private Long id;

    private Long buildingId;

    private String buildingName;

    private BigDecimal deposit;

    private Long dormTypeId;

    private String dormTypeName;

    private String name;

    @Serial
    private static final long serialVersionUID = 1L;
}
