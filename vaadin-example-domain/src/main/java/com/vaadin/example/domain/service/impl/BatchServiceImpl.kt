package com.vaadin.example.domain.service.impl

import com.vaadin.example.domain.repository.batch.BatchRepository
import com.vaadin.example.domain.service.BatchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
open class BatchServiceImpl @Autowired constructor(private val batchRepository: BatchRepository) : BatchService {

    override fun initialBatchSchema() {
        this.batchRepository.createBatchTables()
    }
}