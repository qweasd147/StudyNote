package com.es.demo;

import com.es.demo.docs.Article;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ArticleDto {

    @Getter
    @NoArgsConstructor
    public static class CreateReq {

        @NotBlank(message = "제목 필수 입력")
        private String subject;

        @NotBlank(message = "내용 필수 입력")
        private String contents;
        private Set<String> tags = new LinkedHashSet<>();

        @Builder
        public CreateReq(String subject, String contents, Set<String> tags) {
            this.subject = subject;
            this.contents = contents;
            this.tags = tags;
        }

        public Article toEntity(){

            Article article = Article.builder()
                    .subject(this.subject)
                    .contents(this.contents)
                    .build();

            if(tags != null)
                tags.stream()
                .forEach(tag-> article.addTag(tag));

            return article;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ModifyReq {

        private String subject;
        private String contents;
        private Set<String> tags = new LinkedHashSet<>();

        @Builder
        public ModifyReq(String subject, String contents, Set<String> tags) {
            this.subject = subject;
            this.contents = contents;
            this.tags = tags;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Resp {

        private String idx;
        private String subject;
        private String contents;
        private List<String> tags;

        @Builder
        public Resp(String idx, String subject, String contents, List<String> tags) {
            this.idx = idx;
            this.subject = subject;
            this.contents = contents;
            this.tags = tags;
        }

        public static Resp of(Article article){

            return Resp.builder()
                    .idx(article.getIdx())
                    .subject(article.getSubject())
                    .contents(article.getContents())
                    .tags(article.getTags())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ListReq {

        private List<String> tags;

        @Builder
        public ListReq(List<String> tags) {
            this.tags = tags;
        }
    }
}
