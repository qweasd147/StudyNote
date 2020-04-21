package com.es.demo;

import com.es.demo.docs.Article;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
        @NotNull(message = "타입 필수 입력")
        private ArticleType type;
        private Set<String> tags = new LinkedHashSet<>();

        @Builder
        public CreateReq(String subject, String contents, ArticleType type, Set<String> tags) {
            this.subject = subject;
            this.contents = contents;
            this.type = type;
            this.tags = tags;
        }

        public Article toEntity(){

            Article article = Article.builder()
                    .subject(this.subject)
                    .contents(this.contents)
                    .articleType(this.type)
                    .createdDate(LocalDateTime.now())
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

        private String id;
        private String subject;
        private String contents;
        private LocalDateTime createdDate;
        private List<String> tags;

        @Builder
        public Resp(String id, String subject, String contents, LocalDateTime createdDate, List<String> tags) {
            this.id = id;
            this.subject = subject;
            this.contents = contents;
            this.createdDate = createdDate;
            this.tags = tags;
        }

        public static Resp of(Article article){

            return Resp.builder()
                    .id(article.getId())
                    .subject(article.getSubject())
                    .contents(article.getContents())
                    .createdDate(article.getCreatedDate())
                    .tags(article.getTags())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ListReq {

        private String type;
        private String keyword = "";
        private List<String> tags;

        @Builder
        private ListReq(String type, String keyword, List<String> tags) {
            this.type = type;
            this.keyword = keyword;
            this.tags = tags;
        }
    }
}
