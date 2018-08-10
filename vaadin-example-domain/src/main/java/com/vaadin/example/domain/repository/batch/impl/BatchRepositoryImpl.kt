package com.vaadin.example.domain.repository.batch.impl

import com.vaadin.example.domain.repository.batch.BatchRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.ScriptUtils
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class BatchRepositoryImpl @Autowired constructor(private val dataSource: DataSource) : BatchRepository {
    companion object {
        private const val SCHEMA_BATCH_FILE = "/mysql/schema-batch.sql"
    }


    override fun createBatchTables() {
        val resource = ClassPathResource(SCHEMA_BATCH_FILE, javaClass)
        ScriptUtils.executeSqlScript(this.dataSource.connection, resource)
    }
}