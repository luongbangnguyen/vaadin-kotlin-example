package com.vaadin.example.feignclient.oauth

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@FeignClient("\${server.service.name}")
@RequestMapping("oAuthClientDetailsService")
interface OAuthClientDetailsService {

    @GetMapping("loadClientByClientId/{clientId}")
    fun loadClientByClientId(@PathVariable clientId: String): ClientDetails
}