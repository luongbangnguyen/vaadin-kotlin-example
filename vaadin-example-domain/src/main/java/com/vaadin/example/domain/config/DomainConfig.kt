package com.vaadin.example.domain.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableJpaRepositories(basePackages = ["com.vaadin.example.domain.repository.jpa"])
@EnableElasticsearchRepositories(basePackages = ["com.vaadin.example.domain.repository.elastic"])
@EntityScan("com.vaadin.example.domain.entity")
class DomainConfig {

    @Bean(name = ["passwordEncoder"])
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
}