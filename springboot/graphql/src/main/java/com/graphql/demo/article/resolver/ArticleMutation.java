package com.graphql.demo.article.resolver;


import com.graphql.demo.article.Article;
import com.graphql.demo.article.ArticleDTO;
import com.graphql.demo.article.service.ArticleService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleMutation implements GraphQLMutationResolver {

    private final ArticleService articleService;

    public Article createArticle(ArticleDTO.CreateRequest requestParam){

        return articleService.create(requestParam);
    }
}
