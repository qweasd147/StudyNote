package com.es.demo;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.moreLikeThisQuery;

public abstract class AbstractCustomElasticsearchRepository {

    public static NativeSearchQueryBuilder defaultQueryBuilder(){

        //BoolQueryBuilder filter = QueryBuilders.boolQuery();
        //must(and) , should(or)
        //filter.must(QueryBuilders.matchQuery("contents","12345"));
        //SearchRequest searchRequest = new SearchRequest().indices("x_1").types("type_14");



        return new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery());
                //.withFilter(filter);
                //.withQuery(moreLikeThisQuery(new String[]{String.valueOf(keywords.toArray())}));
    }
}
