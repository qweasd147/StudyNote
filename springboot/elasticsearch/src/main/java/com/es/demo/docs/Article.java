package com.es.demo.docs;

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
import java.util.ArrayList;
import java.util.Date;
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

    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    @JsonProperty(value = "@timestamp")
    private Date createdDate = new Date();

    private List<String> tags = new ArrayList<>();

    @Builder
    public Article(String subject, String contents, Date createdDate, List<String> tags) {
        this.subject = subject;
        this.contents = contents;
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
