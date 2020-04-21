package com.es.demo.service;

import com.es.demo.ArticleDto;
import com.es.demo.docs.Article;
import com.es.demo.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
@AllArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Page<Article> searchAll(ArticleDto.ListReq searchRequestDto, Pageable pageable){

        if(StringUtils.isEmpty(searchRequestDto.getKeyword()))
            return articleRepository.findAll(pageable);

        return articleRepository.findAllByContentsLike("*"+searchRequestDto.getKeyword()+"*", pageable);
    }

    public Page<Article> searchAllLike(ArticleDto.ListReq searchRequestDto, Pageable pageable){

        return articleRepository.findAllByRequestDto(searchRequestDto, pageable);
    }

    public Article searchOne(String articleIdx){
        return articleRepository.findById(articleIdx).orElse(null);
    }

    public Article register(ArticleDto.CreateReq createReq){
        return articleRepository.save(createReq.toEntity());
    }

    public Article modify(String articleIdx, ArticleDto.ModifyReq modifyReq){
        Article article = articleRepository.findById(articleIdx)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 번호" + articleIdx));

        article.getTags().clear();
        article
                .updateContents(modifyReq.getSubject(), modifyReq.getContents())
                .addTags(modifyReq.getTags());

        return article;
    }

    public void remove(String articleIdx){
        articleRepository.deleteById(articleIdx);
    }
}
