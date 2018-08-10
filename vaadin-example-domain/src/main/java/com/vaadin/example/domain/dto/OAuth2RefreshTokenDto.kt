package com.vaadin.example.domain.dto

import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken
import org.springframework.security.oauth2.common.OAuth2RefreshToken

class OAuth2RefreshTokenDto {

    lateinit var value: String

    constructor()

    constructor(oAuth2RefreshToken: OAuth2RefreshToken?) {
        this.value = oAuth2RefreshToken?.value!!
    }

    fun toOAuth2RefreshToken() : OAuth2RefreshToken  = DefaultOAuth2RefreshToken(this.value)
}