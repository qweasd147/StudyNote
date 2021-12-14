package com.example.demo.article;

import com.example.demo.model.Article;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class PersistenceTest {

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

        articleRepository.findById(4L).get();   //1차 캐싱 유도

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

    @Test
    @DisplayName("flush를 유도해서 사용하여 캐싱 데이터 DB 반영 테스트. annotation 기반으로")
    public void useFlushByAnnotation(){

        Article data = articleRepository.findById(5L).get();   //캐싱 유도

        //articleRepository.updateByQuery(5L, "update subject", "update contents");

        data.updateSubject("subject");

        articleRepository.updateByQueryAndAutoClear(5L, "flush subject", "flush content");


        Article afterFlushAndClear = articleRepository.findById(5L).get();  //반영된 데이터 db에서 조회한다.

        assertThat(afterFlushAndClear.getSubject(), is("flush subject"));
        assertThat(afterFlushAndClear.getContents(), is("flush content"));
    }

    @Test
    @DisplayName("벌크 연산 후, clear를 안하고 조회하면 기존 영속성 컨텍스트는 실제랑 안맞음")
    public void contextTest1(){

        //1차 캐싱 유도
        articleRepository.findById(6L).get();

        //벌크 연산을 통해 영속성 컨텍스트를 깨트림
        articleRepository.updateByQuery(6L, "update subject", "update contents");

        //실제 db가 아닌 영속성 컨텍스트에서 값조회(정확하지가 않음)
        Article afterBulk = articleRepository.findById(6L).get();

        //잘못된 영속성 컨텍스트에서 조회해서 값이 정확하지가 않다.
        then(afterBulk.getSubject())
                .isNotEqualTo("update subject");
        then(afterBulk.getContents())
                .isNotEqualTo("update contents");
    }

    @Test
    @DisplayName("벌크 연산 후, clear하고 재조회 하면 정확한 값을 얻을 수가 있다.")
    public void contextTest2(){

        //1차 캐싱 유도
        articleRepository.findById(7L).get();

        //벌크 연산을 통해 영속성 컨텍스트를 깨뜨리고 clear한다.
        articleRepository.updateByQueryAndAutoClear(7L, "update subject", "update contents");

        //실제 db에서 재조회 한다.
        Article afterBulkAndClear = articleRepository.findById(7L).get();

        //잘못된 영속성 컨텍스트에서 조회해서 값이 정확하지가 않다.
        then(afterBulkAndClear.getSubject())
                .isEqualTo("update subject");
        then(afterBulkAndClear.getContents())
                .isEqualTo("update contents");
    }
}
