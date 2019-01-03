package com.vaadin.example.api.config.oauth

import com.vaadin.example.domain.util.Constants.CLIENT_RESOURCE
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer

@Configuration
@EnableResourceServer
open class ResourceServerConfig : ResourceServerConfigurerAdapter() {
    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.resourceId(CLIENT_RESOURCE).stateless(false)
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().anyRequest().authenticated()
    }
}