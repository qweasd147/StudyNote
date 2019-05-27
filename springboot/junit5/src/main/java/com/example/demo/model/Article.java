package com.example.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    @NotNull
    private String subject;

    @Column(nullable = false)
    @NotNull
    @Lob
    private String contents;

    @Transient
    private Set<String> tags = new HashSet<>();

    @Builder
    public Article(String subject, String contents, Set<String> tags) {
        this.subject = subject;
        this.contents = contents;
        this.tags = tags;
    }

    public void addTag(String tag){
        this.tags.add(tag);
    }

    public void initTags(List<String> Tags){
        this.tags = new HashSet<>(tags);
    }

    public Article updateContents(String subject, String contents) {
        this.subject = subject;
        this.contents = contents;

        return this;
    }
}
