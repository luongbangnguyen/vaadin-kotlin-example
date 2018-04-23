package com.vaadin.example.feignclient.oauth

import com.vaadin.example.domain.dto.OAuth2AuthenticationDto
import com.vaadin.example.domain.dto.OAuth2RefreshTokenDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2RefreshToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.bind.annotation.*

@FeignClient("\${server.service.name}")
@RequestMapping("tokenStore")
interface TokenStoreService{

    @PostMapping("readAuthenticationForRefreshToken")
    fun readAuthenticationForRefreshToken(@RequestBody p0: OAuth2RefreshTokenDto?): OAuth2AuthenticationDto?

    @RequestMapping("readRefreshToken")
    fun readRefreshToken(@RequestParam p0: String?): OAuth2RefreshToken?

    @RequestMapping("findTokensByClientId")
    fun findTokensByClientId(@RequestParam p0: String?): MutableCollection<OAuth2AccessToken>?

    @PostMapping("removeRefreshToken")
    fun removeRefreshToken(@RequestBody p0: OAuth2RefreshTokenDto?)

    @PostMapping("removeAccessTokenUsingRefreshToken")
    fun removeAccessTokenUsingRefreshToken(@RequestBody p0: OAuth2RefreshTokenDto?)

    @PostMapping("storeAccessToken")
    fun storeAccessToken(@RequestBody value: Pair<OAuth2AccessToken?, OAuth2AuthenticationDto?>?)

    @GetMapping("readAccessToken")
    fun readAccessToken(@RequestParam p0: String?): OAuth2AccessToken?

    @PostMapping("storeRefreshToken")
    fun storeRefreshToken(value: Pair<OAuth2RefreshToken?, OAuth2AuthenticationDto?>?)

    @PostMapping("getAccessToken")
    fun getAccessToken(@RequestBody p0: OAuth2AuthenticationDto?): OAuth2AccessToken?

    @GetMapping("findTokensByClientIdAndUserName")
    fun findTokensByClientIdAndUserName(@RequestParam p0: String?, @RequestParam p1: String?): MutableCollection<OAuth2AccessToken>?

    @PostMapping("removeAccessToken")
    fun removeAccessToken(@RequestBody p0: OAuth2AccessToken?)

    @PostMapping("readAuthentication")
    fun readAuthentication(@RequestBody p0: OAuth2AccessToken?): OAuth2AuthenticationDto?

    @GetMapping("readAuthentication")
    fun readAuthentication(@RequestParam p0: String?): OAuth2AuthenticationDto?
}