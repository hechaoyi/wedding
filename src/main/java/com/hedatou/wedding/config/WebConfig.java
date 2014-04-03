package com.hedatou.wedding.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.hedatou.wedding.web", useDefaultFilters = false, includeFilters = @Filter(Controller.class))
@Import(WebSocketConfig.class)
public class WebConfig extends WebMvcConfigurerAdapter {

    @Value("${resources.cache.period}")
    private int resourcesCachePeriod;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/**").addResourceLocations("/public/").setCachePeriod(resourcesCachePeriod);
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/")
                .setCachePeriod(resourcesCachePeriod);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholder() {
        PropertySourcesPlaceholderConfigurer placeholder = new PropertySourcesPlaceholderConfigurer();
        placeholder.setLocation(new ClassPathResource("wedding.properties"));
        return placeholder;
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

}
