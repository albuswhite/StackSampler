package com.example.customerbff.config;

/**
 * @author Wenyu Chen
 * @version 1.0.0
 * @ClassName WebMvcConfig.java
 * @andrewID wenyuc2
 * @Description TODO
 */
import com.example.customerbff.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor());
    }
}