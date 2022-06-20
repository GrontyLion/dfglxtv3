package com.mjl.dfglxtv3.domain.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserVO implements Serializable {

    private Long id;

    private String username;

    private String name;

    private String email;

    private Long dormId;

    private String dormName;

    private Long buildingId;

    private String buildingName;

    private Long roleId;

    private String roleName;

    @Serial
    private static final long serialVersionUID = 1L;
}
