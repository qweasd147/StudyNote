package com.example.demo.article;

import com.example.demo.model.Article;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.TagRepository;
import com.example.demo.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.PersistenceUtil;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class PersistenceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    @PersistenceContext
    private EntityManager em;


    @Test
    @DisplayName("bulk 연산 후 영속성 테스트")
    public void bulkTest(){

        Article beforeArticle = articleService.findByIdx(1L);

        assertThat(beforeArticle.getSubject(), is("init subject1"));    //최초 데이터
        assertThat(beforeArticle.getContents(), is("init contents1"));  //최초 데이터

        String updateSubject = "bulk subject";
        String updateContent = "bulk content";

        //벌크 연산 후, 초기 데이터로 데이터 복구 method
        Article article = articleService.recoveryAfterBulk(1L, updateSubject, updateContent);

        assertThat(article.getSubject(), is("init subject1"));
        assertThat(article.getContents(), is("init contents1"));

        Article afterArticle = articleService.findByIdx(1L);

        assertThat(afterArticle.getSubject(), is("init subject1"));
        assertThat(afterArticle.getContents(), is("init contents1"));

        //em.flush();
        em.clear();

        Article afterClear = articleService.findByIdx(1L);

        assertThat(afterClear.getSubject(), is(updateSubject));
        assertThat(afterClear.getContents(), is(updateContent));
    }

    @Test
    @DisplayName("현재 영속성 관리하지 않은 엔티티 상태 테스트")
    public void otherEntity(){

        articleRepository.findById(2L).get();   //캐싱 유도

        articleRepository.updateByQuery(2L, "update subject", "update contents");

        Article article = articleRepository.findById(2L).get();   //영속성 관리 된 객체가 db에서 조회 되는지 테스트

        assertThat(article.getSubject(), not("update subject"));
        assertThat(article.getContents(), not("update contents"));

        Article article3 = articleRepository.findById(3L).get();

        assertThat(article3.getSubject(), is("init subject3"));
        assertThat(article3.getContents(), is("init contents3"));
    }

    @Test
    @DisplayName("flush 사용하여 캐싱 데이터 DB 반영 테스트")
    public void useFlush(){

        articleRepository.findById(4L).get();   //캐싱 유도

        articleRepository.updateByQuery(4L, "update subject", "update contents");

        Article afterBulk = articleRepository.findById(4L).get();

        assertThat(afterBulk.getSubject(), not("update subject"));
        assertThat(afterBulk.getContents(), not("update contents"));

        afterBulk.updateSubject("flush subject");
        afterBulk.updateContents("flush content");

        em.flush(); //캐싱된 데이터를 db에 반영한다.
        em.clear();

        Article afterFlushAndClear = articleRepository.findById(4L).get();  //반영된 데이터 db에서 조회한다.

        assertThat(afterFlushAndClear.getSubject(), is("flush subject"));
        assertThat(afterFlushAndClear.getContents(), is("flush content"));
    }
}
