package com.asuransi_akses.AsuransiServer.configs;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Mengizinkan semua endpoint
                .allowedOrigins("http://localhost:4200", "http://10.10.13.31:4200", "http://192.168.0.121:4200") // Tambahkan origin yang diizinkan
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Mengizinkan metode spesifik
                .allowedHeaders("*"); // Mengizinkan semua header
    }
}

