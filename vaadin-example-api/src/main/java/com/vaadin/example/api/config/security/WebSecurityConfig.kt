package com.vaadin.example.api.config.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService


@Configuration
@EnableWebSecurity
open class WebSecurityConfig : WebSecurityConfigurerAdapter(){

    @Autowired
    @Qualifier("passwordEncoder")
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    @Qualifier("clientDetailsServiceImpl")
    private lateinit var clientDetailsService: ClientDetailsService

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(this.passwordEncoder)
    }

    @Bean(name = ["userDetailsService"])
    open fun clientDetailsUserService(): UserDetailsService {
        val userDetailsService = ClientDetailsUserDetailsService(this.clientDetailsService)
        userDetailsService.setPasswordEncoder(this.passwordEncoder)
        return userDetailsService
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean(name = ["passwordEncoder"])
    open fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
}
