package com.vaadin.example.domain.oauth

import com.vaadin.example.domain.entity.Clients
import org.springframework.security.oauth2.provider.client.BaseClientDetails

class OAuthClientDetails : BaseClientDetails() {
    var clients: Clients? = null
}