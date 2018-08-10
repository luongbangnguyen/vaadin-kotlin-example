package com.vaadin.example.domain.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.OAuth2Request
import org.springframework.security.oauth2.provider.TokenRequest
import java.io.Serializable
import java.util.*

class OAuth2AuthenticationDto {
     var resourceIds: Set<String>? = null
     var authorities: Collection<String>? = null
     var approved: Boolean? = false
     var refresh: TokenRequest? = null
     var redirectUri: String? = ""
     var responseTypes: Set<String>? = null
     var extensions: Map<String, Serializable>? = null
     var principal: Any? = null
     var credentials: Any? = null

    var clientId: String? = null
    var scope: Set<String>? = HashSet()
    var requestParameters: Map<String, String>? = null

    constructor()

    constructor(oAuth: OAuth2Authentication?) {
        this.resourceIds = oAuth?.oAuth2Request?.resourceIds
        this.authorities = oAuth?.oAuth2Request?.authorities?.map { it.authority }
        this.approved = oAuth?.oAuth2Request?.isApproved
        this.refresh = oAuth?.oAuth2Request?.refreshTokenRequest
        this.redirectUri = oAuth?.oAuth2Request?.redirectUri
        this.responseTypes = oAuth?.oAuth2Request?.responseTypes
        this.extensions = oAuth?.oAuth2Request?.extensions

        this.principal = oAuth?.userAuthentication?.principal
        this.credentials = oAuth?.userAuthentication?.credentials
        this.clientId = oAuth?.oAuth2Request?.clientId
        this.scope = oAuth?.oAuth2Request?.scope
        this.requestParameters = oAuth?.oAuth2Request?.requestParameters
    }

    fun toOAuth2Authentication() : OAuth2Authentication {
        val authoritySimples = this.authorities?.map { SimpleGrantedAuthority(it) }
        val storedRequest = OAuth2Request(this.requestParameters, this.clientId, authoritySimples, approved!!,  scope, resourceIds, redirectUri, responseTypes, extensions)
        val userAuthentication = UsernamePasswordAuthenticationToken(this.credentials, authoritySimples)
        return OAuth2Authentication(storedRequest, userAuthentication)
    }
}