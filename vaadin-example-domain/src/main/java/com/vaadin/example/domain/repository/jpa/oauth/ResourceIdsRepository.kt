package com.vaadin.example.domain.repository.jpa.oauth

import com.vaadin.example.domain.entity.ResourceIds
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface ResourceIdsRepository : JpaRepository<ResourceIds, Long>, QuerydslPredicateExecutor<ResourceIds>