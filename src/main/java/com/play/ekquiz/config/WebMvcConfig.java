package com.play.ekquiz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private MvcInterceptor mvcInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("WebMvcConfig..........");
        registry.addInterceptor(mvcInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/")
                .excludePathPatterns("/favicon.ico")
                .excludePathPatterns("/resources/**")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/error/**")
                .excludePathPatterns("/login")
                ;
    }
}