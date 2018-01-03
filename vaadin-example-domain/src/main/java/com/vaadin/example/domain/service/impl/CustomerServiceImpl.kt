package com.vaadin.example.domain.service.impl

import com.vaadin.example.domain.criteria.CustomerCriteria
import com.vaadin.example.domain.entity.Customer
import com.vaadin.example.domain.entity.QCustomer
import com.vaadin.example.domain.exeption.BusinessException
import com.vaadin.example.domain.repository.elastic.CustomerElasticRepository
import com.vaadin.example.domain.repository.jpa.CustomerJpaRepository
import com.vaadin.example.domain.service.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.concurrent.thread

@Service
@Transactional
class CustomerServiceImpl @Autowired constructor(
        private val customerRepository: CustomerJpaRepository,
        private val customerElasticRepository: CustomerElasticRepository
) : CustomerService {

    override fun findAll(customerCriteria: CustomerCriteria, pageable: Pageable): Page<Customer> =
            customerElasticRepository.findAllCustomer(customerCriteria = customerCriteria, pageable = pageable)


    override fun save(customer: Customer): Customer {
        if(customer.isCreateNew()) {
            return createCustomer(customer)
        }
        return updateCustomer(customer)
    }

    override fun findById(id: Long): Customer =
            this.customerElasticRepository.findById(id).orElseThrow { BusinessException("Customer has $id not found") }

    override fun delete(customer: Customer) {
        thread {
           this.deleteCustomerJpa(customer.id)
        }
        this.customerElasticRepository.delete(customer)
    }

    override fun deleteById(id: Long) {
        thread {
            this.deleteCustomerJpa(id)
        }

        this.customerElasticRepository.deleteById(id)
    }

    private fun deleteCustomerJpa(customerId: Long) {
        this.customerRepository.deleteById(customerId)
    }

    private fun  updateCustomer(customer: Customer): Customer {
        if (customer.isUpdateEmail() && customer.isExistEmail()) {
            throw BusinessException("Email Existed")
        }
        return customer.saveDb()
    }

    private fun createCustomer(customer: Customer): Customer {
        if(customer.isExistEmail()) {
            throw BusinessException("Email Existed")
        }
        return customer.saveDb()
    }

    private fun Customer.saveDb(): Customer {
        customerRepository.save(this)
        return customerElasticRepository.save(this)
    }

    private fun Customer.isCreateNew() : Boolean = this.id == null

    private fun Customer.isUpdateEmail() : Boolean {
        val customerDb = customerRepository.findOne(QCustomer.customer.id.eq(this.id)).get()
        return customerDb.email != this.email
    }

    private fun Customer.isExistEmail(): Boolean = customerRepository.findOne(QCustomer.customer.email.eq(this.email)).isPresent
}