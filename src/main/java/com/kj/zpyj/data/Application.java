package com.kj.zpyj.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author nick
 * @date 2024/7/21
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.kj.zpyj.data"})
@MapperScan(basePackages = {"com.kj.zpyj.data.south.adapter.repository"})
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        System.out.println(context.getBeanDefinitionCount());
    }
}
