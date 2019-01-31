package com.vaadin.example.domain.repository.elastic.customer

import com.vaadin.example.domain.entity.Customer
import com.vaadin.example.domain.repository.elastic.customer.custom.CustomerElasticRepositoryCustom
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface CustomerElasticRepository : ElasticsearchRepository<Customer, Long>, CustomerElasticRepositoryCustom<Customer>