package com.vaadin.example.api

import org.dozer.spring.DozerBeanMapperFactoryBean
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer


@SpringBootApplication
@ComponentScan(basePackages =  ["com.vaadin.example.feignclient", "com.vaadin.example.api"])
class VaadinExampleApiApplication {
    @Bean
    fun dozerBeanMapperFactoryBean(): DozerBeanMapperFactoryBean = DozerBeanMapperFactoryBean()
}

fun main(args: Array<String>) {
    runApplication<VaadinExampleApiApplication>(*args)
}
