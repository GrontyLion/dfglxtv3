package com.mjl.dfglxtv3.domain;

import lombok.Getter;

@Getter
public enum OpenType {
    _iframe("_iframe"), _blank("_blank"), _self("_self"), _parent("_parent"), _top("_top");
    private String name;

    OpenType(String name) {
        this.name = name;
    }
}
