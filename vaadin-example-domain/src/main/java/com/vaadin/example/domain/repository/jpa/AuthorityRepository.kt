package com.vaadin.example.domain.repository.jpa

import com.vaadin.example.domain.entity.Authority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface AuthorityRepository : JpaRepository<Authority, Long>, QuerydslPredicateExecutor<Authority>