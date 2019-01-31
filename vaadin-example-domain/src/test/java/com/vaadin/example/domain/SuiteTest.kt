package com.vaadin.example.domain

import com.vaadin.example.domain.customer.CustomerServiceTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(CustomerServiceTest::class)
open class SuiteTest