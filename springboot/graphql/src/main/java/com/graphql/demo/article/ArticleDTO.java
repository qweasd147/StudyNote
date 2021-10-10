package com.graphql.demo.article;

import lombok.Getter;
import lombok.Setter;

public class ArticleDTO {

    @Getter
    @Setter
    public static class CreateRequest {

        private String subject;
        private String content;

        public Article toEntity(){

            return Article.builder()
                    .subject(this.subject)
                    .contents(this.content)
                    .build();
        }
    }
}
