package com.hedatou.wedding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Configuration
@ComponentScan(basePackages = "com.hedatou.wedding", useDefaultFilters = true, excludeFilters = @Filter({
        Controller.class, ControllerAdvice.class, Configuration.class }))
public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholder() {
        PropertySourcesPlaceholderConfigurer placeholder = new PropertySourcesPlaceholderConfigurer();
        placeholder.setLocation(new ClassPathResource("wedding.properties"));
        return placeholder;
    }

}
