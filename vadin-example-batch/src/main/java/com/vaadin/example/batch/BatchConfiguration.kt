package com.vaadin.example.batch

import com.vaadin.example.batch.client.ClientReader
import com.vaadin.example.batch.client.ClientWriter
import com.vaadin.example.batch.customer.CustomerReader
import com.vaadin.example.batch.customer.CustomerWriter
import com.vaadin.example.domain.entity.Clients
import com.vaadin.example.domain.entity.Customer
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.step.tasklet.TaskletStep
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn

@Configuration
@EnableBatchProcessing
@DependsOn("initialConfiguration")
open class BatchConfiguration @Autowired constructor(private val jobBuilderFactory: JobBuilderFactory,
                                                private val stepBuilderFactory: StepBuilderFactory,
                                                private val clientReader: ClientReader,
                                                private val clientWriter: ClientWriter,
                                                private val customerReader: CustomerReader,
                                                private val customerWriter: CustomerWriter) {
    @Bean
    open fun initClientJob() : Job  = this.jobBuilderFactory
            .get("initClientJob")
            .incrementer(RunIdIncrementer())
            .flow(clientStep())
            .end()
            .build()


    @Bean
    open fun clientStep(): TaskletStep = this.stepBuilderFactory
            .get("clientStep")
            .chunk<Clients, Clients>(1)
            .reader(this.clientReader)
            .writer(this.clientWriter)
            .build()

    @Bean
    open fun initCustomerJob() : Job  = this.jobBuilderFactory
            .get("initCustomerJob")
            .incrementer(RunIdIncrementer())
            .flow(customerStep())
            .end()
            .build()


    @Bean
    open fun customerStep(): Step = this.stepBuilderFactory
            .get("customerStep")
            .chunk<List<Customer>, List<Customer>>(1)
            .reader(this.customerReader)
            .writer(this.customerWriter)
            .build()
}