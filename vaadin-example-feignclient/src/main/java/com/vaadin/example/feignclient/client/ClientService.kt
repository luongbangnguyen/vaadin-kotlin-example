package com.vaadin.example.feignclient.client

import com.vaadin.example.domain.dto.ClientDto
import com.vaadin.example.domain.util.pageable.PageableWrapper
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@FeignClient("\${server.service.name}")
@RequestMapping("clientService")
interface ClientService{

    @PostMapping("createOrUpdate")
    fun createOrUpdate(@RequestBody clientDto: ClientDto)

    @PostMapping("findAll")
    fun findAll(pageableWrapper: PageableWrapper<String>) : Page<ClientDto>

    @DeleteMapping("delete/{clientId}")
    fun delete(@PathVariable clientId: String)

    @GetMapping("findByClientId/{id}")
    fun findByClientId(@PathVariable id: String) : ClientDto
}