package com.hedatou.wedding.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ComponentScan(basePackages = "com.hedatou.wedding", useDefaultFilters = true, excludeFilters = @Filter({
        Controller.class, ControllerAdvice.class, Configuration.class }))
public class AppConfig {

    @Value("${redis.host}")
    private String redisHost;
    @Value("${redis.port}")
    private int redisPort;
    @Value("${redis.max.total}")
    private int redisMaxTotal;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholder() {
        PropertySourcesPlaceholderConfigurer placeholder = new PropertySourcesPlaceholderConfigurer();
        placeholder.setLocation(new ClassPathResource("wedding.properties"));
        return placeholder;
    }

    @Bean(destroyMethod = "destroy")
    public JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(redisMaxTotal);
        return new JedisPool(config, redisHost, redisPort);
    }

}
