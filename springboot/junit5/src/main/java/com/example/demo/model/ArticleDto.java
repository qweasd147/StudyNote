package com.example.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ArticleDto {

    @Getter
    @NoArgsConstructor
    public static class CreateReq {

        @NotBlank(message = "제목 필수 입력")
        private String subject;

        @NotBlank(message = "내용 필수 입력")
        private String contents;
        private List<String> tags = new ArrayList<>();

        @Builder
        public CreateReq(String subject, String contents, List<String> tags) {
            this.subject = subject;
            this.contents = contents;
            this.tags = tags;
        }

        public Article toEntity(){

            Set<Tag> tagSet;

            if(tags == null){
                tagSet = Collections.EMPTY_SET;
            }else{
                tagSet = tags.stream()
                        .map(Tag::new)
                        .collect(Collectors.toSet());
            }

            return Article.builder()
                    .subject(this.subject)
                    .contents(this.contents)
                    .tags(tagSet)
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

            List<String> tagList = article.getTags().stream()
                    .map(Tag::getTag)
                    .collect(Collectors.toList());

            return Resp.builder()
                    .idx(article.getIdx())
                    .subject(article.getSubject())
                    .contents(article.getContents())
                    .tags(tagList)
                    .build();
        }
    }
}
