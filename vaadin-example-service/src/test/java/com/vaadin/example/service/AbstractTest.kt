package com.vaadin.example.service

import com.vaadin.example.domain.repository.elastic.customer.CustomerElasticRepository
import org.junit.After
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [ApplicationTest::class])
open class AbstractTest {

    @Autowired
    private lateinit var customerElasticRepository: CustomerElasticRepository


    @After
    fun rollBackElasticsearch() {
        customerElasticRepository.deleteAll()
    }
}