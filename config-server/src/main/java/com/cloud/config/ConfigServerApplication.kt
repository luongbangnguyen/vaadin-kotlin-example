package com.cloud.config

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.config.server.EnableConfigServer

@EnableConfigServer
@SpringBootApplication
open class ConfigServerApplication

fun main(args: Array<String>) {
    SpringApplication.run(ConfigServerApplication::class.java, *args)
}