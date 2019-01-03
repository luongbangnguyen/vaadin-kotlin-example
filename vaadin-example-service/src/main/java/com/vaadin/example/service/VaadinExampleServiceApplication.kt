package com.vaadin.example.service

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = ["com.vaadin.example"])
open class VaadinProjectExampleApplication

fun main(args: Array<String>) {
    SpringApplication.run(VaadinProjectExampleApplication::class.java, *args)
}
