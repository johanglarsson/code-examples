package com.ninja;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean(name = "capitalizer")
    StringFunction uppercaseFunction() {
        return String::toUpperCase;
    }

    @Bean(name = "lowercase")
    StringFunction lowerCaseFunction() {
        return String::toLowerCase;
    }

}