package com.samples.reactive.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  SecurityWebFilterChain securityFilter(ServerHttpSecurity http) {
    return http.authorizeExchange()
        .anyExchange()
        .authenticated()
        .and()
        .httpBasic()
        .and()
        .csrf() // This is a REST interface, disable csrf key.
        .disable()
        .build();
  }
}
