package com.vaadin.example.domain

import com.vaadin.example.domain.repository.elastic.customer.CustomerElasticRepository
import org.junit.After
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.EnableTransactionManagement


open class AbstractTest {

    @Autowired
    private lateinit var customerElasticRepository: CustomerElasticRepository

    @After
    fun rollBackElasticsearch() {
        customerElasticRepository.deleteAll()
    }
}