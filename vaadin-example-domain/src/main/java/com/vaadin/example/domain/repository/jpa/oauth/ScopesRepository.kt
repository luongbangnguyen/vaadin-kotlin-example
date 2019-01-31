package com.vaadin.example.domain.repository.jpa.oauth

import com.vaadin.example.domain.entity.Scopes
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface ScopesRepository : JpaRepository<Scopes, Long>, QuerydslPredicateExecutor<Scopes>