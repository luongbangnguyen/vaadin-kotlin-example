package com.vaadin.example.domain.config

import com.vaadin.example.domain.entity.Clients
import com.vaadin.example.domain.entity.Customer
import com.vaadin.example.domain.repository.elastic.CustomerElasticRepository
import com.vaadin.example.domain.repository.jpa.*
import com.vaadin.example.domain.util.ClientUtils
import com.vaadin.example.domain.util.getFormatValue
import org.apache.commons.collections4.CollectionUtils
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*


@Component
class InitData {

    @Bean
    fun initCustomerData(customerJpaRepository: CustomerJpaRepository,
                         customerElasticRepository: CustomerElasticRepository): CommandLineRunner = CommandLineRunner {

        var isDataElasticNotExisted = true
        var isDataJpaNotExisted = true

        if(customerElasticRepository.count() > 0) {
            isDataElasticNotExisted = false
        }

        if(customerJpaRepository.count() > 0) {
            isDataJpaNotExisted = false
        }
        val names = listOf( "Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
                "Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson",
                "Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson",
                "Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
                "Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
                "Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
                "Jaydan Jackson", "Bernard Nilsen")

            val r = Random(0)
            names.map { it.split(" ") }.map{ createCustomer(it, r) }.forEach {
                if (isDataJpaNotExisted) {
                    customerJpaRepository.save(it)
                }
                if (isDataElasticNotExisted) {
                    customerElasticRepository.save(it)
                }
            }
        }

    private fun createCustomer(split: List<String>, r: Random): Customer = Customer().apply {
        firstName = split[0]
        lastName = split[1]
        email = split[0].toLowerCase() + "@" + split[1].toLowerCase() + ".com"
        status = com.vaadin.example.domain.enums.CustomerStatus.values()[r.nextInt(com.vaadin.example.domain.enums.CustomerStatus.values().size)]
        birthDate = LocalDate.now().plusDays((0 - r.nextInt(365 * 15 + 365 * 60)).toLong())
        description = "$firstName $lastName $email $status ${birthDate.getFormatValue()}"
    }

    @Bean
    fun intClientData(clientsRepository: ClientsRepository,
                      authorizedGrantTypesRepository: AuthorizedGrantTypesRepository,
                      resourceIdsRepository: ResourceIdsRepository,
                      scopesRepository: ScopesRepository,
                      webServerRedirectUrisRepository: WebServerRedirectUrisRepository,
                      authorityRepository: AuthorityRepository,
                      passwordEncoder: PasswordEncoder): CommandLineRunner = CommandLineRunner {

        val clientList = clientsRepository.findAll()

        val clients = Clients().apply {
            this.clientId = "user"
            this.clientName = "user"
            this.clientSecret = passwordEncoder.encode("123456")
            this.accessTokenValidity = 3600
            this.refreshTokenValidity = 3600
        }

        if (CollectionUtils.isEmpty(clientList))
        {
            clientsRepository.save(clients)
            authorizedGrantTypesRepository.saveAll(ClientUtils.createGrantTypesDefaults(clients.clientId))
            resourceIdsRepository.save(ClientUtils.createResourceIdsDefault(clients.clientId))
            scopesRepository.saveAll(ClientUtils.createScopesDefault(clients.clientId))
            webServerRedirectUrisRepository.save(ClientUtils.createRedirectUrlsDefault(clients.clientId))
            authorityRepository.save(ClientUtils.createRolesDefault(clients.clientId))
        }
    }
}