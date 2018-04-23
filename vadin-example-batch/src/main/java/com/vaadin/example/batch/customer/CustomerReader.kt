package com.vaadin.example.batch.customer

import com.vaadin.example.domain.entity.Customer
import com.vaadin.example.domain.util.getFormatValue
import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemStream
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*

@Component

class CustomerReader : ItemReader<List<Customer>>, ItemStream {

    var interval = 0

    val names = listOf( "Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
            "Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson",
            "Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson",
            "Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
            "Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
            "Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
            "Jaydan Jackson", "Bernard Nilsen")

    override fun read(): List<Customer>? {
        if (interval > 0) {
            return null
        }
        interval++
        val r = Random(0)
        return names.map { createCustomer(it.split(" "), r) }
    }

    private fun createCustomer(split: List<String>, r: Random): Customer = Customer().apply {
        firstName = split[0]
        lastName = split[1]
        email = split[0].toLowerCase() + "@" + split[1].toLowerCase() + ".com"
        status = com.vaadin.example.domain.enums.CustomerStatus.values()[r.nextInt(com.vaadin.example.domain.enums.CustomerStatus.values().size)]
        birthDate = LocalDate.now().plusDays((0 - r.nextInt(365 * 15 + 365 * 60)).toLong())
        description = "$firstName $lastName $email $status ${birthDate.getFormatValue()}"
    }

    private fun resetInterval() {
        this.interval = 0
    }

    override fun update(p0: ExecutionContext?) {
    }

    override fun open(p0: ExecutionContext?) {
    }

    override fun close() {
       this.resetInterval()
    }
}