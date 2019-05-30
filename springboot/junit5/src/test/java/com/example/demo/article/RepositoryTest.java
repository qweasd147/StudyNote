package com.example.demo.article;

import com.example.demo.model.Article;
import com.example.demo.model.ArticleDto;
import com.example.demo.model.Tag;
import com.example.demo.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;


    @Test
    @DisplayName("정상적인 게시물 입력 및 입력한 데이터 조회 테스트")
    public void write(){
        ArticleDto.CreateReq createReq = ArticleDto.CreateReq.builder()
                .subject("mock subject")
                .contents("mock contents")
                .tags(new LinkedHashSet<>(Arrays.asList("tag1", "tag2", "tag3")))
                .build();

        Article saveArticle = articleRepository.save(createReq.toEntity());

        assertNotNull(saveArticle);
        assertNotNull(saveArticle.getIdx());

        Article searchArticle = articleRepository.findById(saveArticle.getIdx()).orElseGet(null);

        assertNotNull(searchArticle);

        assertThat(createReq.getSubject(), is(searchArticle.getSubject()));
        assertThat(createReq.getContents(), is(searchArticle.getContents()));

        Set<String> tagsFromRepository = searchArticle.getTags().stream()
                .map(Tag::getTag)
                .collect(Collectors.toSet());

        assertEquals(createReq.getTags(), tagsFromRepository);
    }
}
