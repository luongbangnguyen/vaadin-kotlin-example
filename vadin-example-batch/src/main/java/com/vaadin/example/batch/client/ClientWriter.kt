package com.vaadin.example.batch.client

import com.vaadin.example.domain.entity.Clients
import com.vaadin.example.domain.repository.jpa.oauth.*
import com.vaadin.example.domain.repository.jpa.client.ClientsRepository
import com.vaadin.example.domain.util.ClientUtils
import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ClientWriter @Autowired constructor(private val clientsRepository: ClientsRepository,
                                          private val authorizedGrantTypesRepository: AuthorizedGrantTypesRepository,
                                          private val resourceIdsRepository: ResourceIdsRepository,
                                          private val scopesRepository: ScopesRepository,
                                          private val webServerRedirectUrisRepository: WebServerRedirectUrisRepository,
                                          private val authorityRepository: AuthorityRepository) : ItemWriter<Clients> {

    override fun write(clients: MutableList<out Clients>) {
        this.clientsRepository.saveAll(clients)
        this.authorizedGrantTypesRepository.saveAll(ClientUtils.createGrantTypesDefaults(clients.first().clientId))
        resourceIdsRepository.save(ClientUtils.createResourceIdsDefault(clients.first().clientId))
        scopesRepository.saveAll(ClientUtils.createScopesDefault(clients.first().clientId))
        webServerRedirectUrisRepository.save(ClientUtils.createRedirectUrlsDefault(clients.first().clientId))
        authorityRepository.save(ClientUtils.createRolesDefault(clients.first().clientId))
    }
}