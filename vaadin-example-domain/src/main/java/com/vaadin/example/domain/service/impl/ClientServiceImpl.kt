package com.vaadin.example.domain.service.impl

import com.querydsl.core.BooleanBuilder
import com.vaadin.example.domain.constants.DomainConstants
import com.vaadin.example.domain.dto.ClientDto
import com.vaadin.example.domain.entity.*
import com.vaadin.example.domain.exeption.BusinessException
import com.vaadin.example.domain.repository.jpa.*
import com.vaadin.example.domain.service.ClientService
import com.vaadin.example.domain.util.ClientUtils
import com.vaadin.example.domain.util.Constants
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
open class ClientServiceImpl : ClientService {
    @Autowired
    private lateinit var clientRepository: ClientsRepository

    @Autowired
    private lateinit var resourceIdRepository: ResourceIdsRepository

    @Autowired
    private lateinit var scopeRepository: ScopesRepository

    @Autowired
    private lateinit var webServerRedirectUriRepository: WebServerRedirectUrisRepository

    @Autowired
    private lateinit var authorityRepository: AuthorityRepository

    @Autowired
    private lateinit var authorityGrantTypesRepository: AuthorizedGrantTypesRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun createOrUpdate(clientDto: ClientDto) {
        this.validateClientExited(clientDto)
        this.saveClientToDb(clientDto)
    }

    private fun saveClientToDb(clientDto: ClientDto) {
        this.saveClient(clientDto)
        this.saveResource(clientDto)
        this.saveScopes(clientDto)
        this.saveRedirectUris(clientDto)
        this.saveRoles(clientDto)
        this.saveGrants(clientDto)
    }

    private fun validateClientExited(clientDto: ClientDto) {
        if (clientDto.id != null) {
            return
        }
        val client = this.clientRepository.findAll(QClients.clients.clientId.eq(clientDto.clientId)).toList()
        if (client.isNotEmpty()) {
            throw BusinessException("Client existed")
        }
    }

    private fun saveGrants(clientDto: ClientDto) {
        val query = QAuthorizedGrantTypes.authorizedGrantTypes.clientId.eq(clientDto.clientId)
        val grantsByClient = this.authorityGrantTypesRepository.findAll(query).toList()

        if (grantsByClient.isEmpty()) {
            this.createGrants(clientDto)
        } else {
            this.authorityGrantTypesRepository.deleteAll(grantsByClient)
            this.createGrants(clientDto)
        }
    }

    private fun createGrants(clientDto: ClientDto) {
        val grants = clientDto.toGrants()
        if (CollectionUtils.isNotEmpty(grants)) {
            this.authorityGrantTypesRepository.saveAll(grants!!)
        } else {
            this.authorityGrantTypesRepository.saveAll(ClientUtils.createGrantTypesDefaults(clientDto.clientId!!))
        }
    }

    private fun saveRoles(clientDto: ClientDto) {
        val rolesByClient = this.authorityRepository.findAll(QAuthority.authority.clientId.eq(clientDto.clientId)).toList()
        if (rolesByClient.isEmpty()) {
            this.createRoles(clientDto)
        } else {
            this.authorityRepository.deleteAll(rolesByClient)
            this.createRoles(clientDto)
        }
    }

    private fun createRoles(clientDto: ClientDto) {
        val roles = clientDto.toRoles()
        if (CollectionUtils.isNotEmpty(roles)) {
            this.authorityRepository.saveAll(roles!!)
        } else {
            this.authorityRepository.save(ClientUtils.createRolesDefault(clientDto.clientId!!))
        }
    }

    private fun saveRedirectUris(clientDto: ClientDto) {
        val urlsByClient = this.webServerRedirectUriRepository.findAll(QWebServerRedirectUris.webServerRedirectUris.clientId.eq(clientDto.clientId)).toList()
        if (urlsByClient.isEmpty()) {
            this.createRedirectUris(clientDto)
        }else {
            this.webServerRedirectUriRepository.deleteAll(urlsByClient)
            this.createRedirectUris(clientDto)
        }
    }

    private fun createRedirectUris(clientDto: ClientDto) {
        val redirectUris = clientDto.toRedirectUris()
        if (CollectionUtils.isNotEmpty(redirectUris)) {
            this.webServerRedirectUriRepository.saveAll(redirectUris!!)
        } else {
            this.webServerRedirectUriRepository.save(ClientUtils.createRedirectUrlsDefault(clientDto.clientId!!))
        }
    }

    private fun saveClient(clientDto: ClientDto) {
        val client = transformClient(clientDto)
        if(StringUtils.equals(client.clientSecret, Constants.PASSWORD_DISPLAY_END_USER))
        {
            client.setAgainClientSecret()
        }
        this.clientRepository.save(client)
    }

    private fun Clients.setAgainClientSecret() {
        val clientCurrent = clientRepository.findById(this.clientId).get()
        this.clientSecret = clientCurrent.clientSecret
    }

    private fun saveResource(clientDto: ClientDto) {
        val resourcesByClient = this.resourceIdRepository.findAll(QResourceIds.resourceIds.clientId.eq(clientDto.clientId)).toList()
        if (resourcesByClient.isEmpty()) {
            this.createResources(clientDto)
        } else {
            this.resourceIdRepository.deleteAll(resourcesByClient)
            this.createResources(clientDto)
        }
    }

    private fun createResources(clientDto: ClientDto) {
        this.resourceIdRepository.save(ResourceIds().apply {
            this.clientId = clientDto.clientId
            this.resourceId = DomainConstants.CLIENT_RESOURCE
        })
    }

    private fun saveScopes(clientDto: ClientDto) {
        val scopesByClient = this.scopeRepository.findAll(QScopes.scopes.clientId.eq(clientDto.clientId)).toList()
        if (scopesByClient.isEmpty()) {
            this.createScopes(clientDto)
        } else {
            this.scopeRepository.deleteAll(scopesByClient)
            this.createScopes(clientDto)
        }
    }

    private fun createScopes(clientDto: ClientDto) {
        val scopes = clientDto.toScopes()
        if (CollectionUtils.isNotEmpty(scopes)) {
            this.scopeRepository.saveAll(scopes!!)
        } else {
            this.scopeRepository.saveAll(ClientUtils.createScopesDefault(clientDto.clientId!!))
        }
    }

    private fun transformClient(dto: ClientDto): Clients {
        val client = dto.toClient()
        if (StringUtils.isNotEmpty(client.clientSecret)) {
            val secretPassword = this.passwordEncoder.encode(client.clientSecret)
            client.clientSecret = secretPassword
        }
        return client
    }

    override fun findAll(keyWord: String?, pageable: Pageable?) : Page<ClientDto> {
        val booleanExpression = BooleanBuilder()

        if (StringUtils.isNotEmpty(keyWord))
        {
            booleanExpression.and(QClients.clients.clientId.contains(keyWord).or(QClients.clients.clientName.contains(keyWord)))
        }

        val pageClients = this.clientRepository.findAll(booleanExpression, pageable!!)

        if (pageClients.size == 0) {
            return PageImpl<ClientDto>(mutableListOf(ClientDto()), pageable, pageClients.totalElements)
        }

        val clientDTOs : List<ClientDto> = pageClients.content.map {
            it.toClientDto().apply {
                clientSecret = Constants.PASSWORD_DISPLAY_END_USER
            }
        }

        return PageImpl<ClientDto>(clientDTOs, pageable, pageClients.totalElements)
    }


    override fun findByClientId(id: String) : ClientDto {
        val clients = this.clientRepository.findOne(QClients.clients.clientId.eq(id))
        if (!clients.isPresent) {
            throw BusinessException("ClientId: $id not found")
        }
        return clients.get().toClientDto()

    }

    override fun delete(clientId: String) {
        this.deleteWebServerRedirectUri(clientId)
        this.deleteAuthorities(clientId)
        this.deleteAuthorityGrantTypes(clientId)
        this.deleteScopes(clientId)
        this.deleteResources(clientId)
        this.deleteClients(clientId)
    }

    private fun deleteAuthorities(clientId: String)
    {
        val authorities = this.authorityRepository.findAll(QAuthority.authority.clientId.eq(clientId))
        this.authorityRepository.deleteAll(authorities)
    }

    private fun deleteWebServerRedirectUri(clientId: String) {
        val webUrls =  this.webServerRedirectUriRepository.findAll(QWebServerRedirectUris.webServerRedirectUris.clientId.eq(clientId))
        this.webServerRedirectUriRepository.deleteAll(webUrls)
    }

    private fun deleteAuthorityGrantTypes(clientId: String) {
        val grantTypes = this.authorityGrantTypesRepository.findAll(QAuthorizedGrantTypes.authorizedGrantTypes.clientId.eq(clientId))
        this.authorityGrantTypesRepository.deleteAll(grantTypes)
    }

    private fun deleteScopes(clientId: String) {
        val scopes = this.scopeRepository.findAll(QScopes.scopes.clientId.eq(clientId))
        this.scopeRepository.deleteAll(scopes)
    }

    private fun deleteResources(clientId: String) {
        val resources = this.resourceIdRepository.findAll(QResourceIds.resourceIds.clientId.eq(clientId))
        this.resourceIdRepository.deleteAll(resources)
    }

    private fun deleteClients(clientId: String) {
        val client = this.clientRepository.findAll(QClients.clients.clientId.eq(clientId)).first()
        this.clientRepository.delete(client)
    }

    private fun Clients.toClientDto(): ClientDto {
        val clientDto = ClientDto()
        clientDto.id = this.id
        clientDto.clientId = this.clientId
        clientDto.clientName = this.clientName
        clientDto.clientSecret = this.clientSecret
        clientDto.accessTokenValidity = this.accessTokenValidity
        clientDto.refreshTokenValidity = this.refreshTokenValidity
        clientDto.accessTokenValidity = this.refreshTokenValidity

        clientDto.roles = authorityRepository.findAll(QAuthority.authority.clientId.eq(this.clientId)).map { it.role }
        clientDto.scopes = scopeRepository.findAll(QScopes.scopes.clientId.eq(this.clientId)).map { it.scope }
        clientDto.redirectUris = webServerRedirectUriRepository
                .findAll(QWebServerRedirectUris.webServerRedirectUris.clientId.eq(this.clientId)).map { it.webServerRedirectUri }
        clientDto.grants = authorityGrantTypesRepository
                .findAll(QAuthorizedGrantTypes.authorizedGrantTypes.clientId.eq(this.clientId)).map { it.authorizedGrantType }

        return clientDto
    }
}