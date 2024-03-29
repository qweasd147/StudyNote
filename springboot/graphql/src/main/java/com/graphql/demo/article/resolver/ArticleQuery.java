package com.graphql.demo.article.resolver;

import com.graphql.demo.article.Article;
import com.graphql.demo.article.service.ArticleService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ArticleQuery implements GraphQLQueryResolver {

    private final ArticleService articleService;

    public Article article(Long articleIdx){

        return this.articleService.findByIdx(articleIdx)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 번호" + articleIdx));
    }

    public List<Article> articles(){
        return this.articleService.findAll();
    }
}
