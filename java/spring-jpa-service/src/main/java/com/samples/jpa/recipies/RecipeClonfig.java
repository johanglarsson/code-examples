package com.samples.jpa.recipies;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
public class RecipeClonfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:com/samples/jpa/recipies/changelog/db.changelog-master.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

}