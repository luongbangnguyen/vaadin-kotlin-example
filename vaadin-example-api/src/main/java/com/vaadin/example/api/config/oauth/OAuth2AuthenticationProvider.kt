package com.vaadin.example.api.config.oauth

import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.provider.OAuth2Authentication

@Configuration
open class OAuth2AuthenticationProvider : AuthenticationProvider {
    override fun authenticate(p0: Authentication): Authentication {
        return p0
    }

    override fun supports(p0: Class<*>?): Boolean {
        return OAuth2Authentication::class.java.isAssignableFrom(p0)
    }
}