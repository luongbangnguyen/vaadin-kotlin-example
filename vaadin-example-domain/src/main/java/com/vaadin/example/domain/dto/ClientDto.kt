package com.vaadin.example.domain.dto

import com.vaadin.example.domain.entity.*

class ClientDto {
    var id: Long? = null
    var clientId: String? = null
    var clientSecret: String? = null
    var clientName: String? = null
    var accessTokenValidity: Int? = null
    var refreshTokenValidity: Int? = null

    var scopes: List<String>? = null
    var roles: List<String>? = null
    var grants: List<String>? = null
    var redirectUris: List<String>? = null

    fun toClient(): Clients = Clients().apply {
        this.id = this@ClientDto.id
        this.accessTokenValidity = this@ClientDto.accessTokenValidity
        this.clientId = this@ClientDto.clientId
        this.clientName = this@ClientDto.clientName
        this.clientSecret = this@ClientDto.clientSecret
        this.refreshTokenValidity = this@ClientDto.refreshTokenValidity
    }

    fun toScopes(): List<Scopes>? = this.scopes?.map {
        Scopes().apply {
            this.clientId = this@ClientDto.clientId
            this.scope = it
        }
    }

    fun toRoles(): List<Authority>? = this.roles?.map {
        Authority().apply {
            this.clientId =this@ClientDto.clientId
            this.role = it
        }
    }

    fun toGrants(): List<AuthorizedGrantTypes>? = this.grants?.map {
        AuthorizedGrantTypes().apply {
            this.clientId = this@ClientDto.clientId
            this.authorizedGrantType = it
        }
    }

    fun toRedirectUris(): List<WebServerRedirectUris>? = this.redirectUris?.map {
        WebServerRedirectUris().apply {
            this.clientId = this@ClientDto.clientId
            this.webServerRedirectUri = it
        }
    }
}