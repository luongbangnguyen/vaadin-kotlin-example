package com.vaadin.example.service.customer

import com.google.gson.Gson
import com.vaadin.example.domain.entity.Customer
import com.vaadin.example.domain.enums.CustomerStatus
import com.vaadin.example.domain.service.customer.CustomerService
import com.vaadin.example.service.AbstractTest
import org.elasticsearch.node.Node
import org.hamcrest.Matchers.containsString
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate


@RunWith(SpringRunner::class)
open class CustomerServiceApiTest : AbstractTest(){
    @Autowired
    private var mvc: MockMvc? = null

    @Autowired
    private var gson: Gson? = null

    @Autowired
    private lateinit var customerService: CustomerService

    @Autowired
    private lateinit var node:Node

    private val customerTest = Customer().apply {
        firstName = "firstName test"
        lastName = "lastName test"
        birthDate = LocalDate.now()
        description = "description test"
        email = "email@test.com"
        status = CustomerStatus.Customer
    }



    @Test
    fun testSaveCustomer() {
        mvc!!.perform(post("/customerService/save")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(this.gson!!.toJson(this.customerTest)))
                .andExpect(status().isOk)
                .andExpect(content().string(containsString("firstName test")))
                .andExpect(content().string(containsString("lastName test")))
                .andExpect(content().string(containsString("description test")))
                .andExpect(content().string(containsString("email@test.com")))
    }

    @Test
    fun testReadCustomer() {
        val customer = this.customerService.save(this.customerTest)
        this.mvc!!.perform(get("/customerService/findByClientId/${customer.id}"))
                .andExpect(status().isOk)
                .andExpect(content().string(containsString("firstName test")))
                .andExpect(content().string(containsString("lastName test")))
                .andExpect(content().string(containsString("description test")))
                .andExpect(content().string(containsString("email@test.com")))
    }
}