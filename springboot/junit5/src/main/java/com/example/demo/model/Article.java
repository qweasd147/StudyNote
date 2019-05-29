package com.example.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "article_id")
    @OrderBy("idx ASC ")
    @BatchSize(size = 20)
    private Set<Tag> tags = new LinkedHashSet<>();

    @Builder
    public Article(String subject, String contents, Set<Tag> tags) {
        this.subject = subject;
        this.contents = contents;
        this.tags = tags;
    }

    public void addTag(String tag){
        this.tags.add(new Tag(tag));
    }

    public void initTags(List<String> tags){
        if(tags != null)
            this.tags = tags.stream()
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public Article updateContents(String subject, String contents) {
        this.subject = subject;
        this.contents = contents;

        return this;
    }
}
