package com.vaadin.example.domain.repository.jpa

import com.vaadin.example.domain.entity.Clients
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface ClientsRepository : JpaRepository<Clients, String>, QuerydslPredicateExecutor<Clients>