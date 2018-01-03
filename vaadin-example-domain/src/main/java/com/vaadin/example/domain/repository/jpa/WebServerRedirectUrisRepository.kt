package com.vaadin.example.domain.repository.jpa

import com.vaadin.example.domain.entity.WebServerRedirectUris
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface WebServerRedirectUrisRepository : JpaRepository<WebServerRedirectUris, Long>, QuerydslPredicateExecutor<WebServerRedirectUris>