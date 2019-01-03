package com.vaadin.example.env.config

import org.elasticsearch.client.Client
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate

@Configuration
open class ElasticsearchTemplateConfiguration {
    @Bean
    open fun elasticsearchTemplate(client: Client): ElasticsearchTemplate {
        return ElasticsearchTemplate(client, CustomEntityMapper())
    }
}
