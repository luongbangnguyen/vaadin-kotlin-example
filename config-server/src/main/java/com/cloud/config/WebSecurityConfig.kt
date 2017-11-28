package com.cloud.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User

/**
 * Created by nlbang on 11/14/2017.
 */
@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Value("\${application.username}")
    private lateinit var username: String

    @Value("\${application.password}")
    private lateinit var password: String

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().antMatchers("/**").authenticated().and().httpBasic()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        val userDetail = User.withDefaultPasswordEncoder().username(username).password(password).roles("USER").build()
        auth.inMemoryAuthentication().withUser(userDetail)
    }
}