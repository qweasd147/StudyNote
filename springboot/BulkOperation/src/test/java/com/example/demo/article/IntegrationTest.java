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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArticleService articleService;

    @Test
    @DisplayName("Article bulk 수정 연산 테스트")
    public void writeTest() throws Exception {

        //Article beforeArticle = articleService.findByIdx(1L);

        mockMvc.perform(put("/api/article")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }

    @Test
    @DisplayName("tag bulk 수정 연산 테스트")
    public void wrongWrite() throws Exception {

        mockMvc.perform(put("/api/tag")
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }
}
