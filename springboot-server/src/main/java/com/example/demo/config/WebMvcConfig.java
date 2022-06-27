package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //    告知系统static 当成 静态资源访问
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\uploads\\";
        String uapath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\avataruploads\\";
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:" + path);
        registry.addResourceHandler("/avataruploads/**").addResourceLocations("file:" + uapath);
    }
}
