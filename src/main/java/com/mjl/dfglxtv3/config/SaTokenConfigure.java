package com.mjl.dfglxtv3.config;

import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.thymeleaf.dialect.SaTokenDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    @Bean
    public SaTokenDialect getSaTokenDialect() {
        return new SaTokenDialect();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaRouteInterceptor((req, res, handler) -> {
                    SaRouter.match("/api/user/**", r -> StpUtil.checkPermission("USER"));
                }))
                .addPathPatterns("/**")
                .excludePathPatterns("/login",
                        "/api/user/login",
                        "/api/user/logout",
                        "/api/role/list"
                );
    }
}
