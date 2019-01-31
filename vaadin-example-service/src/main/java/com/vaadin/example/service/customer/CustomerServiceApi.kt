package com.vaadin.example.service.customer

import com.vaadin.example.domain.criteria.CustomerCriteria
import com.vaadin.example.domain.entity.Customer
import com.vaadin.example.domain.service.customer.CustomerService
import com.vaadin.example.domain.util.pageable.PageJacksonModule
import com.vaadin.example.domain.util.pageable.PageableWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customerService")
class CustomerServiceApi @Autowired constructor(private val customerService: CustomerService){

    @PostMapping("findAll")
    fun findAll(@RequestBody pageableWrapper: PageableWrapper<CustomerCriteria>) : Page<Customer>  {
        val page = this.customerService.findAll(pageableWrapper.criteria, pageableWrapper.toPageable())
        return PageJacksonModule.PageImplSimple(page.content, pageableWrapper.pageable.page, pageableWrapper.pageable.size, page.totalElements)
    }

    @PostMapping("save")
    fun save(@RequestBody customer: Customer): Customer {
        val result = this.customerService.save(customer)
        return result
    }

    @PostMapping("delete")
    fun delete(@RequestBody customer: Customer) {
        this.customerService.delete(customer)
    }

    @DeleteMapping("deleteById/{id}")
    fun deleteById(@PathVariable id: Long) {
        this.customerService.deleteById(id)
    }

    @GetMapping("findByClientId/{id}")
    fun findById(@PathVariable id: Long): Customer = this.customerService.findById(id)
}