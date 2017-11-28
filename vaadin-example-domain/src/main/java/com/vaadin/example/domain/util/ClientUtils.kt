package com.vaadin.example.domain.util

import com.vaadin.example.domain.entity.*
import com.vaadin.example.domain.util.Constants.CLIENT_RESOURCE

object ClientUtils {
    fun createScopesDefault(clientId: String): List<Scopes> {
        val scope1 = Scopes().apply {
            this.clientId = clientId
            this.scope = "read"
        }

        val scope2 = Scopes().apply {
            this.clientId = clientId
            this.scope = "write"
        }

        return arrayListOf(scope1, scope2)
    }

    fun createGrantTypesDefaults(clientId: String): List<AuthorizedGrantTypes> {
        val grantTypes1 = AuthorizedGrantTypes().apply {
            this.authorizedGrantType = "authorization_code"
            this.clientId = clientId
        }

        val grantTypes2 = AuthorizedGrantTypes().apply {
            this.authorizedGrantType = "password"
            this.clientId = clientId
        }

        val grantTypes3 = AuthorizedGrantTypes().apply {
            this.authorizedGrantType = "refresh_token"
            this.clientId = clientId
        }

        val grantTypes4 = AuthorizedGrantTypes().apply {
            this.authorizedGrantType = "implicit"
            this.clientId = clientId
        }

        return arrayListOf(grantTypes1, grantTypes2, grantTypes3, grantTypes4)
    }

    fun createResourceIdsDefault(clientId: String): ResourceIds = ResourceIds().apply {
        this.clientId = clientId
        this.resourceId = CLIENT_RESOURCE
    }


    fun createRedirectUrlsDefault(clientId: String): WebServerRedirectUris = WebServerRedirectUris().apply {
        this.clientId = clientId
        this.webServerRedirectUri = "http://localhost:8181"
    }

    fun createRolesDefault(clientId: String): Authority = Authority().apply {
        this.clientId = clientId
        this.role = "ROLE_USER"
    }
}