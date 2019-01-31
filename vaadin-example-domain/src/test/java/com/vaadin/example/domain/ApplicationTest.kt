package com.vaadin.example.domain

import org.elasticsearch.client.Client
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.node.Node
import org.elasticsearch.node.NodeValidationException
import org.elasticsearch.transport.Netty4Plugin
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.Arrays.asList

@SpringBootTest
@ComponentScan(basePackages = ["com.vaadin.example"])
@TestPropertySource(locations = ["classpath:vaadin-example-domain/application.properties"])
@DataJpaTest
@EnableTransactionManagement
open class ApplicationTest {

    @Bean
    @Throws(NodeValidationException::class)
    fun elasticSearchTestNode(): Node {
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

    @Bean()
    fun client(node: Node): Client? {
        return node.client()
    }

}
