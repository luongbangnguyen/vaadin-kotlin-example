package com.vaadin.example.api.controller.customer

import com.vaadin.example.domain.criteria.CustomerCriteria
import com.vaadin.example.domain.entity.Customer
import com.vaadin.example.domain.util.pageable.PageableWrapper
import com.vaadin.example.feignclient.customer.CustomerService
import org.dozer.Mapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customer")
class CustomerApi {

    @Autowired
    private lateinit var customerService: CustomerService

    @Autowired
    private lateinit var mapper: Mapper

    @GetMapping
    fun findAllCustomer(customerCriteria: CustomerCriteria, pageable: Pageable): Page<Customer> = this.customerService.findAll(PageableWrapper.covert(customerCriteria, pageable))

    @GetMapping("{id}")
    fun findCustomerById(@PathVariable id: Long): Customer = this.customerService.findById(id)

    @PostMapping
    fun createCustomer(customerForm: CustomerForm): Customer {
        val customer = Customer()
        this.mapper.map(customerForm, customer)
        return this.customerService.save(customer)
    }

    @PutMapping
    fun updateCustomer(customerForm: CustomerForm) = this.createCustomer(customerForm)
}