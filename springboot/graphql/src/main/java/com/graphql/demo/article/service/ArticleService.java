package com.graphql.demo.article.service;

import com.graphql.demo.article.Article;
import com.graphql.demo.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> findAll(){
        return articleRepository.findAll();
    }

    public Optional<Article> findByIdx(Long articleIdx){

        return articleRepository.findByIdx(articleIdx);
    }
}
