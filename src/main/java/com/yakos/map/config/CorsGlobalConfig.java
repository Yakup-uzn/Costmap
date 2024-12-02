package com.yakos.map.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsGlobalConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Tarayıcıda kimlik bilgilerini gönderime izin verir
        config.addAllowedOriginPattern("*"); // Tüm kaynaklara izin ver
        config.addAllowedHeader("*"); // Tüm header'lara izin ver
        config.addAllowedMethod("*"); // GET, POST, PUT, DELETE gibi tüm HTTP metodlarına izin ver

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
    
    
}
