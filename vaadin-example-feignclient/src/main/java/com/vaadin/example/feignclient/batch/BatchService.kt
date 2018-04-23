package com.vaadin.example.feignclient.batch

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@FeignClient("\${server.service.name}")
@RequestMapping("batchService")
interface BatchService {

    @GetMapping("initialBatchSchema")
    fun initialBatchSchema()
}