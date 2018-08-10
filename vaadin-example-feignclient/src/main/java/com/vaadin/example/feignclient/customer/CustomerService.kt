package com.vaadin.example.feignclient.customer

import com.vaadin.example.domain.criteria.CustomerCriteria
import com.vaadin.example.domain.entity.Customer
import com.vaadin.example.domain.util.pageable.PageableWrapper
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@FeignClient("\${server.service.name}")
@RequestMapping("customerService")
interface CustomerService{

    @PostMapping("findAll")
    fun findAll(pageableWrapper: PageableWrapper<CustomerCriteria>) : Page<Customer>

    @PostMapping("save")
    fun save(@RequestBody customer: Customer): Customer

    @PostMapping("delete")
    fun delete(@RequestBody customer: Customer)

    @DeleteMapping("deleteById/{id}")
    fun deleteById(@PathVariable id: Long)

    @GetMapping("findByClientId/{id}")
    fun findById(@PathVariable id: Long): Customer
}