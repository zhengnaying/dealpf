package com.Dealpf.demo.Util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//全局配置类----配置跨域请求
@Configuration
public class WebConfigure implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")               //域访问路径
                .allowedOrigins("http://localhost:8080","null")      //跨域来源
                .allowedMethods("GET","POST","PUT","OPTIONS","DELETE")    //允许跨域方法
                .allowCredentials(true)          //是否允许携带信息
                .maxAge(3600)  //最大响应时间
                .allowedHeaders("*");
    }
}
