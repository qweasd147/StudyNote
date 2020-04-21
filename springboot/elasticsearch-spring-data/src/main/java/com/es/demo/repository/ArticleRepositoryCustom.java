package com.es.demo.repository;


import com.es.demo.ArticleDto;
import com.es.demo.docs.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepositoryCustom  {


    Page<Article> findAllByRequestDto(ArticleDto.ListReq listReq, Pageable pageable);
}
