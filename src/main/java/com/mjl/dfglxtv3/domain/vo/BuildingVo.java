package com.mjl.dfglxtv3.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BuildingVo {
    private Long id;
    private String name;
    private Long electrovalencyTypeId;
    private String electrovalencyTypeName;
    private BigDecimal price;
}
