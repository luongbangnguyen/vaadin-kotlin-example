package com.vaadin.example.domain.repository.jpa.customer

import com.vaadin.example.domain.entity.Customer
import com.vaadin.example.domain.repository.jpa.customer.custom.CustomerJpaRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface CustomerJpaRepository : JpaRepository<Customer, Long>,
        QuerydslPredicateExecutor<Customer>,
        CustomerJpaRepositoryCustom<Customer>