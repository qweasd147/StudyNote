package com.example.demo.service;

import com.example.demo.model.Article;
import com.example.demo.model.ArticleDto;
import com.example.demo.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Page<Article> searchAll(Pageable pageable){
        return articleRepository.findAll(pageable);
    }

    public Article searchOne(Long articleIdx){
        return articleRepository.findById(articleIdx).orElse(null);
    }

    public Article register(ArticleDto.CreateReq createReq){
        return articleRepository.save(createReq.toEntity());
    }

    public Article modify(Long articleIdx, ArticleDto.ModifyReq modifyReq){

        Article article = articleRepository.findById(articleIdx)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 번호" + articleIdx));

        article
            .updateContents(modifyReq.getSubject(), modifyReq.getContents())
            .initTags(modifyReq.getTags());

        return article;
    }

    public void remove(Long articleIdx){
        articleRepository.deleteById(articleIdx);
    }
}
