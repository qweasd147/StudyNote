package com.example.demo.article;

import com.example.demo.TestUtils;
import com.example.demo.model.Article;
import com.example.demo.model.ArticleDto;
import com.example.demo.model.Tag;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("정상적인 글쓰기 테스트")
    public void normalWrite(){

        final ArticleDto.CreateReq createReq =
                ArticleDto.CreateReq.builder()
                .subject("mock subject")
                .contents("mock contents")
                .tags(new LinkedHashSet<>(Arrays.asList("tag1", "tag2", "tag3")))
                .build();

        Article articleEntity = createReq.toEntity();
        TestUtils.injectValue(articleEntity, "idx", 10L);

        given(articleRepository.save(any())).willReturn(articleEntity);

        Article entityFromRepository = articleService.register(createReq);

        verify(articleRepository, atLeastOnce()).save(any());

        assertThat(createReq.getSubject(), is(entityFromRepository.getSubject()));
        assertThat(createReq.getContents(), is(entityFromRepository.getContents()));

        Set<String> tagsFromRepository = entityFromRepository.getTags().stream()
                .map(Tag::getTag)
                .collect(Collectors.toSet());

        assertEquals(createReq.getTags(), tagsFromRepository);
    }

    @Test
    @DisplayName("정상적인 글수정 테스트")
    public void modify(){

        //set before modify
        Article article = Article.builder()
                .subject("subject data")
                .contents("content data")
                .build();

        article.initTags(Arrays.asList("tag1", "tag2", "tag3"));

        TestUtils.injectValue(article, "idx", 10L);

        given(articleRepository.findById(any())).willReturn(Optional.of(article));

        ArticleDto.ModifyReq modifyReqDto = ArticleDto.ModifyReq.builder()
                .subject("수정 된 subject")
                .contents("수정 된 contents")
                .tags(new LinkedHashSet<>(Arrays.asList("수정1", "수정2", "수정3")))
                .build();

        //test modify
        Article modifiedEntity = articleService.modify(article.getIdx(), modifyReqDto);

        assertNotNull(modifiedEntity);
        assertThat(modifiedEntity.getIdx(), is(article.getIdx()));
        assertThat(modifiedEntity.getSubject(), is(modifyReqDto.getSubject()));
        assertThat(modifiedEntity.getContents(), is(modifyReqDto.getContents()));

        Set<String> tagsFromRepository = modifiedEntity.getTags().stream()
                .map(Tag::getTag)
                .collect(Collectors.toSet());

        assertEquals(modifyReqDto.getTags(), tagsFromRepository);
    }

    /**
     * repository 삭제 명령어를 호출 하는지 테스트
     */
    @Test
    @DisplayName("삭제 테스트")
    public void delete(){

        articleService.remove(10L);

        verify(articleRepository, atLeastOnce()).deleteById(any());
        //verify(articleRepository, times(1)).deleteById(any());
    }
}
