package com.example.demo.article;

import com.example.demo.TestUtils;
import com.example.demo.api.ArticleController;
import com.example.demo.model.Article;
import com.example.demo.model.ArticleDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.LinkedHashSet;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * mock을 사용하지 않고 통합테스트
 */
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class IntegrationTest {


    private static final String ARTICLE_API = "/api/article";

    @Autowired
    private MockMvc mockMvc;

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
}
