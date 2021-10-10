package com.graphql.demo.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import graphql.kickstart.tools.SchemaParserOptions;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;

//@Configuration
public class GraphqlConfig {

    //@Bean
    public GraphQLScalarType dateTimeType() {
        return ExtendedScalars.DateTime;
    }

    //@Bean
    public SchemaParserOptions schemaParserOptions(){
        return SchemaParserOptions.newOptions().objectMapperConfigurer((mapper, context) -> {
            mapper.registerModule(new JavaTimeModule());
        }).build();
    }
}
