package com.mjl.dfglxtv3.common;

import cn.dev33.satoken.secure.SaSecureUtil;

public class PasswordEncoder {

    public static String encode(String password) {
        return SaSecureUtil.md5BySalt(password, "salt");
    }

    public static boolean matches(String password, String encodedPassword) {
        return false;
    }

    public static String decode(String encodedPassword) {

        return null;

    }
}
