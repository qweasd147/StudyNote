package com.es.demo.repository;

import com.es.demo.AbstractCustomElasticsearchRepository;
import com.es.demo.ArticleDto;
import com.es.demo.docs.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class ArticleRepositoryImpl extends AbstractCustomElasticsearchRepository implements ArticleRepositoryCustom{

    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public Page<Article> findAllByRequestDto(ArticleDto.ListReq listReq, Pageable pageable) {

        String keyword = listReq.getKeyword();

        BoolQueryBuilder filter = QueryBuilders.boolQuery();
        filter.must(QueryBuilders.matchQuery("contents","*"+keyword+"*"));

        NativeSearchQuery searchQuery = defaultQueryBuilder()
                .withPageable(pageable)
                .withFilter(filter)
                .build();

        List<Article> articles = elasticsearchTemplate.queryForList(searchQuery, Article.class);
        long totalCount = elasticsearchTemplate.count(searchQuery);

        return new PageImpl<>(articles, pageable, totalCount);
    }
}
