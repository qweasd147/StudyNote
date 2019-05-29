package com.example.demo.article;

import com.example.demo.TestUtils;
import com.example.demo.api.ArticleController;
import com.example.demo.model.Article;
import com.example.demo.model.ArticleDto;
import com.example.demo.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ArticleController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    private static final String ARTICLE_API = "/api/article";

    @Test
    @DisplayName("정상적인 글쓰기 테스트")
    public void write() throws Exception {

        ArticleDto.CreateReq createReqDto = ArticleDto.CreateReq.builder()
                .subject("mock subject")
                .contents("mock contents")
                .tags(Arrays.asList("tag1", "tag2", "tag3"))
                .build();

        Article article = createReqDto.toEntity();
        TestUtils.injectValue(article, "idx", 10L);

        given(articleService.register(any())).willReturn(article);

        mockMvc.perform(TestUtils.addDtoParamsInRequestBody(post(ARTICLE_API), createReqDto)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idx", is(10)))
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

        Article article = createReqDto.toEntity();
        TestUtils.injectValue(article, "idx", 10L);

        given(articleService.register(any())).willReturn(article);

        mockMvc.perform(TestUtils.addDtoParamsInRequestBody(post(ARTICLE_API), createReqDto)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
