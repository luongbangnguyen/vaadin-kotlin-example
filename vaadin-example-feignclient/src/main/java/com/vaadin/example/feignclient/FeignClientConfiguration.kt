package com.vaadin.example.feignclient

import com.fasterxml.jackson.databind.Module
import com.vaadin.example.domain.util.pageable.PageJacksonModule
import feign.Request
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.ConfigurableEnvironment




@EnableDiscoveryClient
@EnableFeignClients(basePackages = ["com.vaadin.example.feignclient"])
@Configuration
open class FeignClientConfiguration
{
    @Bean
    open fun pageJacksonModule(): Module {
        return PageJacksonModule()
    }

    @Bean
    open fun requestOptions(env: ConfigurableEnvironment): Request.Options {
        val ribbonReadTimeout = env.getProperty("ribbon.ReadTimeout", Int::class.javaPrimitiveType!!, 70000)
        val ribbonConnectionTimeout = env.getProperty("ribbon.ConnectTimeout", Int::class.javaPrimitiveType!!, 60000)

        return Request.Options(ribbonConnectionTimeout, ribbonReadTimeout)
    }
}