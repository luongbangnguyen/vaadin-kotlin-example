package com.vaadin.example.domain.service.client

import com.vaadin.example.domain.dto.ClientDto
import com.vaadin.example.domain.exeption.BusinessException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ClientService {
    fun createOrUpdate(clientDto: ClientDto)
    fun findAll(keyWord: String?, pageable: Pageable?) : Page<ClientDto>
    @Throws(BusinessException::class)
    fun findByClientId(id: String) : ClientDto
    fun delete(clientId: String)
}