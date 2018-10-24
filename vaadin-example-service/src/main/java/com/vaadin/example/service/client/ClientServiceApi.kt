package com.vaadin.example.service.client

import com.vaadin.example.domain.dto.ClientDto
import com.vaadin.example.domain.service.ClientService
import com.vaadin.example.domain.util.pageable.PageJacksonModule
import com.vaadin.example.domain.util.pageable.PageableWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("clientService")
class ClientServiceApi @Autowired constructor(private val clientService: ClientService){

    @PostMapping("createOrUpdate")
    fun createOrUpdate(@RequestBody clientDto: ClientDto) = this.clientService.createOrUpdate(clientDto)

    @PostMapping("findAll")
    fun findAll(@RequestBody pageableWrapper: PageableWrapper<String>) : Page<ClientDto> {
        val page = this.clientService.findAll(pageableWrapper.criteria, pageableWrapper.toPageable())
        return PageJacksonModule.PageImplSimple(page.content, pageableWrapper.pageable.page, pageableWrapper.pageable.size, page.totalElements)
    }

    @DeleteMapping("delete/{clientId}")
    fun delete(@PathVariable clientId: String) {
        this.clientService.delete(clientId)
    }

    @GetMapping("findByClientId/{id}")
    fun findByClientId(@PathVariable id: String) : ClientDto = this.clientService.findByClientId(id)
}