package com.es.demo.docs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
public class Article {

    private String id;

    @NotNull
    private String subject;

    @NotNull
    private String contents;

    @JsonProperty("@timestamp")
    private LocalDateTime createdDate = LocalDateTime.now();

    private List<String> tags = new ArrayList<>();

    @Builder
    public Article(String subject, String contents, LocalDateTime createdDate, List<String> tags) {
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
