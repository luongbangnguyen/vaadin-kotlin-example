package com.vaadin.example.batch.customer

import com.vaadin.example.domain.entity.Customer
import com.vaadin.example.domain.repository.elastic.CustomerElasticRepository
import com.vaadin.example.domain.repository.jpa.CustomerJpaRepository
import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CustomerWriter @Autowired constructor(private val customerJpaRepository: CustomerJpaRepository,
                                                private val customerElasticRepository: CustomerElasticRepository) : ItemWriter<List<Customer>> {

    override fun write(customers: MutableList<out List<Customer>>) = customers.forEach{
        this.saveCustomerJpa(it)
        this.saveCustomersElastic(it)
    }

    private fun saveCustomerJpa(customers: List<Customer>) {
        if (customerJpaRepository.count() > 0) {
            return
        }
        this.customerJpaRepository.saveAll(customers)
    }

    private fun saveCustomersElastic(customers: List<Customer>) {
        if(customerElasticRepository.count() > 0) {
            return
        }
        this.customerElasticRepository.saveAll(customers)
    }
}