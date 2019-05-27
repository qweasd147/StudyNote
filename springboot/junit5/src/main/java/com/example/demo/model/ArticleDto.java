package com.example.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ArticleDto {

    @Getter
    @NoArgsConstructor
    public static class CreateReq {

        private String subject;
        private String contents;
        private List<String> tags;

        @Builder
        public CreateReq(String subject, String contents, List<String> tags) {
            this.subject = subject;
            this.contents = contents;
            this.tags = tags;
        }

        public Article toEntity(){
            return Article.builder()
                    .subject(this.subject)
                    .contents(this.contents)
                    .tags(new HashSet<>(tags))
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ModifyReq {

        private String subject;
        private String contents;
        private List<String> tags;

        @Builder
        public ModifyReq(String subject, String contents, List<String> tags) {
            this.subject = subject;
            this.contents = contents;
            this.tags = tags;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Resp {

        private Long idx;
        private String subject;
        private String contents;
        private List<String> tags;

        @Builder
        public Resp(Long idx, String subject, String contents, List<String> tags) {
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
                    .tags(new ArrayList<>(article.getTags()))
                    .build();
        }
    }
}
