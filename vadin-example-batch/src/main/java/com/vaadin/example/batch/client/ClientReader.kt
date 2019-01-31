package com.vaadin.example.batch.client

import com.vaadin.example.domain.entity.Clients
import com.vaadin.example.domain.repository.jpa.client.ClientsRepository
import com.vaadin.example.domain.util.ClientUtils
import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemStream
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class ClientReader @Autowired constructor(private val passwordEncoder: PasswordEncoder,
                                          private val clientsRepository: ClientsRepository) : ItemReader<Clients>, ItemStream{
    private var interval = 0

    override fun read(): Clients? {

        if (interval > 0) {
            return null
        }

        if (this.clientsRepository.count() > 0) {
            return null
        }

        interval++
        return ClientUtils.createClientDefault(this.passwordEncoder)
    }

    fun resetInterval() {
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
