package com.vaadin.example.batch

import com.vaadin.example.domain.service.BatchService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.vaadin.example")
class VaadinProjectExampleBatchApplication {
    @Bean
    fun createBatchSchema(batchService: BatchService) = CommandLineRunner {
        batchService.initialBatchSchema()
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(VaadinProjectExampleBatchApplication::class.java, *args)
}
