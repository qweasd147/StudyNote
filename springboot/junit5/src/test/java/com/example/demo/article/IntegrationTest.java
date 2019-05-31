package com.example.demo.article;

import com.example.demo.TestUtils;
import com.example.demo.model.Article;
import com.example.demo.model.ArticleDto;
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

import java.util.Arrays;
import java.util.LinkedHashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * mock을 사용하지 않고 통합테스트
 */
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class IntegrationTest {

    private static final String ARTICLE_API = "/api/article";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleService articleService;

    @Test
    @DisplayName("정상적인 글쓰기 테스트")
    public void writeTest() throws Exception {

        ArticleDto.CreateReq createReqDto = ArticleDto.CreateReq.builder()
                .subject("mock subject")
                .contents("mock contents")
                .tags(new LinkedHashSet<>(Arrays.asList("tag1", "tag2", "tag3")))
                .build();

        mockMvc.perform(TestUtils.addDtoParamsInRequestBody(post(ARTICLE_API), createReqDto)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idx", is(notNullValue())))
                .andExpect(jsonPath("$.subject", is(createReqDto.getSubject())))
                .andExpect(jsonPath("$.contents", is(createReqDto.getContents())))
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.tags", hasItem("tag1")))
                .andExpect(jsonPath("$.tags", hasItem("tag2")))
                .andExpect(jsonPath("$.tags", hasItem("tag3")));
    }

    @Test
    @DisplayName("부족한 파라미터로 글쓰기 요청")
    public void wrongWrite() throws Exception {

        ArticleDto.CreateReq createReqDto = ArticleDto.CreateReq.builder()
                .contents("mock contents")
                .build();

        mockMvc.perform(TestUtils.addDtoParamsInRequestBody(post(ARTICLE_API), createReqDto)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("정상적인 글 수정 테스트")
    public void modify() throws Exception {

        //등록
        ArticleDto.CreateReq createReqDto = ArticleDto.CreateReq.builder()
                .subject("mock subject")
                .contents("mock contents")
                .tags(new LinkedHashSet<>(Arrays.asList("tag1", "tag2", "tag3")))
                .build();

        Article article = articleService.register(createReqDto);

        assertNotNull(article);
        assertNotNull(article.getIdx());

        //get
        mockMvc.perform(get(ARTICLE_API+"/"+article.getIdx())
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.subject", is(createReqDto.getSubject())))
                .andExpect(jsonPath("$.contents", is(createReqDto.getContents())))
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.tags", hasItem("tag1")))
                .andExpect(jsonPath("$.tags", hasItem("tag2")))
                .andExpect(jsonPath("$.tags", hasItem("tag3")));
    }

    @Test
    @DisplayName("정상적인 삭제 테스트")
    public void remove() throws Exception {

        //등록
        ArticleDto.CreateReq createReqDto = ArticleDto.CreateReq.builder()
                .subject("mock subject")
                .contents("mock contents")
                .tags(new LinkedHashSet<>(Arrays.asList("tag1", "tag2", "tag3")))
                .build();

        Article article = articleService.register(createReqDto);

        assertNotNull(article);
        assertNotNull(article.getIdx());

        Long articleIdx = article.getIdx();

        Article articleBeforeRemove = articleService.searchOne(articleIdx);

        assertNotNull(articleBeforeRemove);

        //delete
        mockMvc.perform(delete(ARTICLE_API+"/"+article.getIdx())
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        Article articleAfterRemove = articleService.searchOne(articleIdx);

        assertThat(articleAfterRemove, is(nullValue()));
    }
}
