package com.es.demo.docs;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Document(indexName = "articles", type = "article1")
public class Article {

    @Id
    private String idx;

    @NotNull
    private String subject;

    @NotNull
    private String contents;

    private List<String> tags = new ArrayList<>();

    @Builder
    public Article(String subject, String contents, List<String> tags) {
        this.subject = subject;
        this.contents = contents;
        this.tags = tags;
    }

    public void addTag(String tag){

        if(this.tags == null)   this.tags = new ArrayList<>();

        if(!StringUtils.isEmpty(tag));
        this.tags.add(tag.trim());
    }

    public Article updateContents(String subject, String contents) {
        this.subject = subject;
        this.contents = contents;

        return this;
    }
}
