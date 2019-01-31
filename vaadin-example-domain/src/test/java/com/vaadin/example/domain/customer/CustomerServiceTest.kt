package com.vaadin.example.domain.customer

import com.vaadin.example.domain.AbstractTest
import com.vaadin.example.domain.criteria.CustomerCriteria
import com.vaadin.example.domain.entity.Customer
import com.vaadin.example.domain.enums.CustomerStatus
import com.vaadin.example.domain.repository.elastic.customer.CustomerElasticRepository
import com.vaadin.example.domain.service.customer.CustomerService
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDate

@RunWith(SpringRunner::class)
open class CustomerServiceTest : AbstractTest(){

    @Autowired
    private var customerService: CustomerService? = null

    @Autowired
    private var customerJpaRepository: CustomerElasticRepository? = null

    @Autowired
    private var customerElasticRepository: CustomerElasticRepository? = null

    private val customerTest = Customer().apply {
        firstName = "firstName test"
        lastName = "lastName test"
        birthDate = LocalDate.now()
        description = "description test"
        email = "email@test.com"
        status = CustomerStatus.Customer
    }

    @Test
    fun testSaveAndReadCustomer() {

        this.customerService!!.save(customerTest)

        val pageable = this.customerService!!.findAll(CustomerCriteria(filterString = customerTest.firstName, birthdayBegin = null, birthdayEnd = null, status = null), pageable = PageRequest.of(0, 1))
        val customerList = pageable.content
        Assert.assertNotEquals(0, customerList.size)
        val customer = customerList[0]
        Assert.assertEquals(customerTest.firstName, customer.firstName)
        Assert.assertEquals(customerTest.lastName, customer.lastName)
        Assert.assertEquals(customerTest.birthDate, customer.birthDate)
        Assert.assertEquals(customerTest.description, customer.description)
        Assert.assertEquals(customerTest.email, customer.email)
        Assert.assertEquals(customerTest.status, customer.status)

        val customerJpaList = customerJpaRepository!!.findAll().toList()
        val customerJpa = customerJpaList[0]
        Assert.assertEquals(customerTest.firstName, customerJpa.firstName)
        Assert.assertEquals(customerTest.lastName, customerJpa.lastName)
        Assert.assertEquals(customerTest.birthDate, customerJpa.birthDate)
        Assert.assertEquals(customerTest.description, customerJpa.description)
        Assert.assertEquals(customerTest.email, customerJpa.email)
        Assert.assertEquals(customerTest.status, customerJpa.status)

        val customerElasticList = customerElasticRepository!!.findAll().toList()
        val customerElastic = customerElasticList[0]
        Assert.assertEquals(customerTest.firstName, customerElastic.firstName)
        Assert.assertEquals(customerTest.lastName, customerElastic.lastName)
        Assert.assertEquals(customerTest.birthDate, customerElastic.birthDate)
        Assert.assertEquals(customerTest.description, customerElastic.description)
        Assert.assertEquals(customerTest.email, customerElastic.email)
        Assert.assertEquals(customerTest.status, customerElastic.status)

    }

    @Test
    fun testReadByIdCustomer() {
        this.customerService!!.save(customerTest)
        val pageable = this.customerService!!.findAll(CustomerCriteria(filterString = customerTest.firstName, birthdayBegin = null, birthdayEnd = null, status = null), pageable = PageRequest.of(0, 1))
        val customerList = pageable.content
        val customerId = customerList[0].id

        val customer = this.customerService!!.findById(customerId)
        Assert.assertEquals(customerTest.firstName, customer.firstName)
        Assert.assertEquals(customerTest.lastName, customer.lastName)
        Assert.assertEquals(customerTest.birthDate, customer.birthDate)
        Assert.assertEquals(customerTest.description, customer.description)
        Assert.assertEquals(customerTest.email, customer.email)
        Assert.assertEquals(customerTest.status, customer.status)
    }
}