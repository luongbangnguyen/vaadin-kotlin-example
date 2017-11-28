package com.vaadin.example.domain.oauth

import com.vaadin.example.domain.entity.*
import com.vaadin.example.domain.exeption.BusinessException
import com.vaadin.example.domain.repository.jpa.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.ClientRegistrationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Qualifier("oAuthClientDetailsService")
@Transactional
class OAuthClientDetailsService : ClientDetailsService {

    @Autowired
    private lateinit var clientsRepository: ClientsRepository

    @Autowired
    private lateinit var authorizedGrantTypesRepository: AuthorizedGrantTypesRepository

    @Autowired
    private lateinit var resourceIdsRepository: ResourceIdsRepository

    @Autowired
    private lateinit var scopesRepository: ScopesRepository

    @Autowired
    private lateinit var webServerRedirectUrisRepository: WebServerRedirectUrisRepository

    @Autowired
    private lateinit var authorityRepository: AuthorityRepository

    override fun loadClientByClientId(clientId: String): ClientDetails {
        val client = this.getClient(clientId)
        val clientGrants = this.authorizedGrantTypesRepository.findAll(QAuthorizedGrantTypes.authorizedGrantTypes.clientId.eq(clientId)).map { it.authorizedGrantType }.toSet()
        val clientResources = this.resourceIdsRepository.findAll(QResourceIds.resourceIds.clientId.eq(clientId)).map { it.resourceId }.toSet()
        val clientScopes = this.scopesRepository.findAll(QScopes.scopes.clientId.eq(clientId)).map { it.scope }.toSet()
        val clientRedirectUris = this.webServerRedirectUrisRepository.findAll(QWebServerRedirectUris.webServerRedirectUris.clientId.eq(clientId)).map { it.webServerRedirectUri }.toSet()
        val clientRoles: List<GrantedAuthority> = this.authorityRepository.findAll(QAuthority.authority.clientId.eq(clientId)).map { SimpleGrantedAuthority(it.role) }

        return OAuthClientDetails().apply {
            this.clients = client
            this.clientId = client.clientId
            this.clientSecret = client.clientSecret
            this.accessTokenValiditySeconds = client.accessTokenValidity
            this.refreshTokenValiditySeconds = client.refreshTokenValidity
            this.setResourceIds(clientResources)
            this.setScope(clientScopes)
            this.setAuthorizedGrantTypes(clientGrants)
            this.registeredRedirectUri = clientRedirectUris
            this.authorities = clientRoles
        }
    }

    private fun getClient(clientId: String) : Clients {
        val clientList = this.clientsRepository.findAll(QClients.clients.clientId.eq(clientId)).toList()
        if (clientList.isEmpty()) {
            throw ClientRegistrationException("client not found")
        }
        return clientList.first()
    }
}