package com.graphql.demo.config;

import graphql.scalars.ExtendedScalars;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphqlConfig {

    @Bean
    public void graphqlType(){
        //RuntimeWiring.newRuntimeWiring().scalar(ExtendedScalars.Date);
        RuntimeWiring.newRuntimeWiring().scalar(ExtendedScalars.Date);
    }
}
