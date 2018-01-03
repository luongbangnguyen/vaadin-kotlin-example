package com.vaadin.example.domain.service

import com.vaadin.example.domain.dto.ClientDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ClientService {
    fun createOrUpdate(clientDto: ClientDto)
    fun findAll(keyWord: String?, pageable: Pageable?) : Page<ClientDto>
    fun delete(clientId: String)
}