package com.blanquita.blanquitagestion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilita la protección CSRF, común para APIs REST
                .csrf(csrf -> csrf.disable())
                // Permite todas las peticiones sin autenticación
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }
}