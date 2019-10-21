package com.example.demo.article;

import com.example.demo.TestUtils;
import com.example.demo.model.Article;
import com.example.demo.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.PersistenceUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
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

    @PersistenceContext
    private EntityManager em;


    @Test
    @DisplayName("bulk 연산 후 영속성 테스트")
    public void bulkTest(){

        Article beforeArticle = articleService.findByIdx(1L);

        assertThat(beforeArticle.getSubject(), is("init subject1"));
        assertThat(beforeArticle.getContents(), is("init contents1"));

        String updateSubject = "bulk subject";
        String updateContent = "bulk content";

        Article article = articleService.recoveryAfterBulk(1L, updateSubject, updateContent);

        assertThat(article.getSubject(), is("init subject1"));
        assertThat(article.getContents(), is("init contents1"));

        Article afterArticle = articleService.findByIdx(1L);

        assertThat(afterArticle.getSubject(), is("init subject1"));
        assertThat(afterArticle.getContents(), is("init contents1"));

        //em.flush();
        em.clear();

        Article afterFlush = articleService.findByIdx(1L);

        assertThat(afterFlush.getSubject(), is(updateSubject));
        assertThat(afterFlush.getContents(), is(updateContent));
    }
}
