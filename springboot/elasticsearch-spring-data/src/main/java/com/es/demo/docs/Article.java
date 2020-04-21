package com.es.demo.docs;

import com.es.demo.ArticleType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@Document(indexName = "articles", type = "article1")
public class Article {

    @Id
    private String id;

    @NotNull
    private String subject;

    @NotNull
    private String contents;

    private ArticleType articleType;

    @Field(type = FieldType.Date, store = true, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonProperty("@timestamp")
    private LocalDateTime createdDate = LocalDateTime.now();

    private List<String> tags = new ArrayList<>();

    @Builder
    private Article(String subject, String contents, ArticleType articleType
            , LocalDateTime createdDate, List<String> tags) {
        this.subject = subject;
        this.contents = contents;
        this.articleType = articleType;
        this.createdDate = createdDate;
        this.tags = tags;
    }

    public void addTag(String tag){

        if(this.tags == null)   this.tags = new ArrayList<>();

        if(!StringUtils.isEmpty(tag));
        this.tags.add(tag.trim());
    }

    public void addTags(Set<String> tags){

        if(this.tags == null)   this.tags = new ArrayList<>();

        tags.stream()
                .forEach(tag -> addTag(tag));
    }

    public Article updateContents(String subject, String contents) {
        this.subject = subject;
        this.contents = contents;

        return this;
    }
}
