package com.vaadin.example.domain.repository.jpa

import com.vaadin.example.domain.entity.AuthorizedGrantTypes
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface AuthorizedGrantTypesRepository : JpaRepository<AuthorizedGrantTypes, Long>, QuerydslPredicateExecutor<AuthorizedGrantTypes>