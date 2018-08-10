package com.vaadin.example.batch.customer

import com.vaadin.example.domain.entity.Customer
import com.vaadin.example.domain.repository.elastic.CustomerElasticRepository
import com.vaadin.example.domain.repository.jpa.CustomerJpaRepository
import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.logging.Logger

@Component
class CustomerWriter @Autowired constructor(private val customerJpaRepository: CustomerJpaRepository,
                                                private val customerElasticRepository: CustomerElasticRepository) : ItemWriter<List<Customer>> {

    companion object {
        val LOG = Logger.getLogger(CustomerWriter::class.java.name)!!
    }

    override fun write(customers: MutableList<out List<Customer>>) = customers.forEach{
        this.saveCustomerJpa(it)
        this.saveCustomersElastic(it)
    }

    private fun saveCustomerJpa(customers: List<Customer>) {
        LOG.info("Customer List write by jpa")
        if (customerJpaRepository.count() > 0) {
            return
        }
        this.customerJpaRepository.saveAll(customers)
    }

    private fun saveCustomersElastic(customers: List<Customer>) {
        LOG.info("Customer List write by elastic")
        if(customerElasticRepository.count() > 0) {
            return
        }
        this.customerElasticRepository.saveAll(customers)
    }
}