package com.es.demo.config;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ElasticSearchConfiguration {


    /*
    @Bean
    public RestHighLevelClient client(ElasticsearchProperties properties) {

        return new RestHighLevelClient(
                RestClient.builder(properties.hosts())
        );
    }
    */

}
