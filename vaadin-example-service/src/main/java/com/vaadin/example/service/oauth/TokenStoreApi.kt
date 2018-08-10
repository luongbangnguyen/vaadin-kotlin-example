package com.vaadin.example.service.oauth

import com.vaadin.example.domain.dto.OAuth2AuthenticationDto
import com.vaadin.example.domain.dto.OAuth2RefreshTokenDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2RefreshToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("tokenStore")
class TokenStoreApi @Autowired constructor(@Qualifier("tokenStoreImpl") private val tokenStore: TokenStore){

    @PostMapping("readAuthenticationForRefreshToken")
    fun readAuthenticationForRefreshToken(@RequestBody p0: OAuth2RefreshTokenDto?): OAuth2AuthenticationDto {
        return OAuth2AuthenticationDto(this.tokenStore.readAuthenticationForRefreshToken(p0?.toOAuth2RefreshToken()))
    }

    @RequestMapping("readRefreshToken")
    fun readRefreshToken(@RequestParam p0: String?): OAuth2RefreshToken {
        return this.tokenStore.readRefreshToken(p0)
    }

    @RequestMapping("findTokensByClientId")
    fun findTokensByClientId(@RequestParam p0: String?): MutableCollection<OAuth2AccessToken> {
        return this.tokenStore.findTokensByClientId(p0)
    }

    @PostMapping("removeRefreshToken")
    fun removeRefreshToken(@RequestBody p0: OAuth2RefreshTokenDto?) {
        this.tokenStore.removeRefreshToken(p0?.toOAuth2RefreshToken())
    }

    @PostMapping("removeAccessTokenUsingRefreshToken")
    fun removeAccessTokenUsingRefreshToken(@RequestBody p0: OAuth2RefreshTokenDto?) {
        this.tokenStore.removeAccessTokenUsingRefreshToken(p0?.toOAuth2RefreshToken())
    }

    @PostMapping("storeAccessToken")
    fun storeAccessToken(@RequestBody value: Pair<OAuth2AccessToken?, OAuth2AuthenticationDto?>?) {
        this.tokenStore.storeAccessToken(value?.first, value?.second?.toOAuth2Authentication())
    }

    @GetMapping("readAccessToken")
    fun readAccessToken(@RequestParam p0: String?): OAuth2AccessToken? {
        return this.tokenStore.readAccessToken(p0)
    }

    @PostMapping("storeRefreshToken")
    fun storeRefreshToken(value: Pair<OAuth2RefreshToken?, OAuth2AuthenticationDto?>?) {
        if (value?.first == null || value.second == null)
        {
            return
        }
        this.tokenStore.storeRefreshToken(value.first, value.second?.toOAuth2Authentication())
    }

    @PostMapping("getAccessToken")
    fun getAccessToken(@RequestBody p0: OAuth2AuthenticationDto?): OAuth2AccessToken? {
        return this.tokenStore.getAccessToken(p0?.toOAuth2Authentication())
    }

    @GetMapping("findTokensByClientIdAndUserName")
    fun findTokensByClientIdAndUserName(@RequestParam p0: String?, @RequestParam p1: String?): MutableCollection<OAuth2AccessToken> {
        return this.tokenStore.findTokensByClientIdAndUserName(p0, p1)
    }

    @PostMapping("removeAccessToken")
    fun removeAccessToken(@RequestBody p0: OAuth2AccessToken?) {
        this.tokenStore.removeAccessToken(p0)
    }

    @PostMapping("readAuthentication")
    fun readAuthentication(@RequestBody p0: OAuth2AccessToken?): OAuth2AuthenticationDto? {
        return OAuth2AuthenticationDto(this.tokenStore.readAuthentication(p0))
    }

    @GetMapping("readAuthentication")
    fun readAuthentication(@RequestParam p0: String?): OAuth2AuthenticationDto? {
        return OAuth2AuthenticationDto(this.tokenStore.readAuthentication(p0))
    }
}