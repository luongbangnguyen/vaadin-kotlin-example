package com.vaadin.example.api.config.oauth

import com.vaadin.example.domain.dto.OAuth2AuthenticationDto
import com.vaadin.example.domain.dto.OAuth2RefreshTokenDto
import com.vaadin.example.feignclient.oauth.TokenStoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2RefreshToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.stereotype.Service

@Service("tokenStoreImp")
class TokenStoreImpl @Autowired constructor(private val tokenStoreService: TokenStoreService) : TokenStore{

    override fun readAuthenticationForRefreshToken(p0: OAuth2RefreshToken?): OAuth2Authentication? {
        return this.tokenStoreService.readAuthenticationForRefreshToken(OAuth2RefreshTokenDto(p0))?.toOAuth2Authentication()
    }

    override fun readRefreshToken(p0: String?): OAuth2RefreshToken? {
        return this.tokenStoreService.readRefreshToken(p0)
    }

    override fun findTokensByClientId(p0: String?): MutableCollection<OAuth2AccessToken>? {
        return this.tokenStoreService.findTokensByClientId(p0)
    }

    override fun removeRefreshToken(p0: OAuth2RefreshToken?) {
        this.tokenStoreService.removeRefreshToken(OAuth2RefreshTokenDto(p0))
    }

    override fun removeAccessTokenUsingRefreshToken(p0: OAuth2RefreshToken?) {
        this.tokenStoreService.removeAccessTokenUsingRefreshToken(OAuth2RefreshTokenDto(p0))
    }

    override fun storeAccessToken(p0: OAuth2AccessToken?, p1: OAuth2Authentication?) {
        this.tokenStoreService.storeAccessToken(Pair(p0, OAuth2AuthenticationDto(p1)))
    }

    override fun readAccessToken(p0: String?): OAuth2AccessToken? {
        return this.tokenStoreService.readAccessToken(p0)
    }

    override fun storeRefreshToken(p0: OAuth2RefreshToken?, p1: OAuth2Authentication?) {
        this.tokenStoreService.storeRefreshToken(Pair(p0, OAuth2AuthenticationDto(p1)))
    }

    override fun getAccessToken(p0: OAuth2Authentication?): OAuth2AccessToken? {
        return this.tokenStoreService.getAccessToken(OAuth2AuthenticationDto(p0))
    }

    override fun findTokensByClientIdAndUserName(p0: String?, p1: String?): MutableCollection<OAuth2AccessToken>? {
        return this.tokenStoreService.findTokensByClientIdAndUserName(p0, p1)
    }

    override fun removeAccessToken(p0: OAuth2AccessToken?) {
        this.tokenStoreService.removeAccessToken(p0)
    }

    override fun readAuthentication(p0: OAuth2AccessToken?): OAuth2Authentication? {
        return this.tokenStoreService.readAuthentication(p0)?.toOAuth2Authentication()
    }

    override fun readAuthentication(p0: String?): OAuth2Authentication? {
        return this.tokenStoreService.readAuthentication(p0)?.toOAuth2Authentication()
    }
}