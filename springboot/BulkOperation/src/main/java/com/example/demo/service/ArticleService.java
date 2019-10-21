package com.example.demo.service;

import com.example.demo.model.Article;
import com.example.demo.model.ArticleDto;
import com.example.demo.model.Tag;
import com.example.demo.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;


@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TagService tagService;

    public Article tagModifyWithBulk(){

        Article article = articleRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 번호" + 1L));

        tagService.updateTagByBulk(article.getTags().get(0));

        Tag tag = article.getTags().get(0);
        log.info("before bulk");
        log.info(tag.toString());

        tag.updateTagName("update tag name");

        log.info("after bulk");
        log.info(tag.toString());

        return article;
    }

    public Article recoveryAfterBulk(Long idx, String bulkSubject, String bulkContents){

        Article article = articleRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 번호" + idx));

        @NotNull String oldSubject = article.getSubject();
        @NotNull String oldContents = article.getContents();

        log.info("before bulk");
        log.info(article.toString());

        articleRepository.updateByQuery(article.getIdx(), bulkSubject, bulkContents);

        log.info("after bulk");
        log.info(article.toString());

        //subject, content 원복
        article.updateSubject(oldSubject)
                .updateContents(oldContents);

        log.info("after recovery");
        log.info(article.toString());

        return article;
    }

    public Article findByIdx(Long idx){
        return articleRepository.findById(idx)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 번호" + idx));
    }
}
