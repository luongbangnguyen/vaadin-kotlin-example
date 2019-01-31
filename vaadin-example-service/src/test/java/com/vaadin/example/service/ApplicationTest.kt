package com.vaadin.example.service

import com.google.gson.Gson
import org.elasticsearch.client.Client
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.node.Node
import org.elasticsearch.node.NodeValidationException
import org.elasticsearch.transport.Netty4Plugin
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.util.FileSystemUtils
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import java.io.File
import java.util.Arrays.asList
import com.google.gson.GsonBuilder
import java.time.LocalDate


@ComponentScan(basePackages = ["com.vaadin.example"], excludeFilters = [ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes =  [VaadinProjectExampleApplication::class])])
@TestPropertySource(locations = ["classpath:vaadin-example-service-test/application.properties"])
@DataJpaTest
@AutoConfigureMockMvc
@EnableTransactionManagement
@EnableWebMvc
open class ApplicationTest {

    @Bean
    @Throws(NodeValidationException::class)
    fun elasticSearchTestNode(): Node {
        removeOldDataDir("elasticsearch-data")
        val node = ElasticsearchEmbed(
                Settings.builder()
                        .put("transport.type", "netty4")
                        .put("http.type", "netty4")
                        .put("http.enabled", "true")
                        .put("path.home", "elasticsearch-data")
                        .build(),
                asList(Netty4Plugin::class.java))
        node.start()
        return node
    }

    @Bean(destroyMethod = "close")
    fun client(node: Node): Client? = node.client()

    fun Client.close()
    {
        print("client is closed")
    }

    @Throws(Exception::class)
    private fun removeOldDataDir(datadir: String) {
        val dataDir = File(datadir)
        if (dataDir.exists()) {
            FileSystemUtils.deleteRecursively(dataDir)
        }
    }

    @Bean
    fun gson(): Gson = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .create()
}
