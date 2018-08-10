package com.vaadin.example.batch

import com.vaadin.example.domain.service.BatchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration

@Configuration("initialConfiguration")
class InitialConfiguration @Autowired constructor(private val batchService: BatchService) {

    init {
        this.batchService.initialBatchSchema()
    }
}