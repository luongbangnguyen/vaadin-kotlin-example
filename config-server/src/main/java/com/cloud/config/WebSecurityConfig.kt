package com.cloud.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * Created by nlbang on 11/14/2017.
 */
@Configuration
@EnableWebSecurity
open class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Value("\${application.username}")
    private lateinit var username: String

    @Value("\${application.password}")
    private lateinit var password: String

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().antMatchers("/**").authenticated().and().httpBasic()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        val userDetail = User.builder().username(username).password(passwordEncoder.encode(password)).roles("USER").build()
        auth.inMemoryAuthentication().withUser(userDetail).passwordEncoder(passwordEncoder)
    }
}