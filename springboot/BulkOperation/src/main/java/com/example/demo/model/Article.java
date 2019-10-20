package com.example.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@ToString
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

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "article")
    @OrderBy("idx ASC ")
    @BatchSize(size = 20)
    private List<Tag> tags = new ArrayList<>();

    @Builder
    public Article(String subject, String contents, List<Tag> tags) {
        this.subject = subject;
        this.contents = contents;
        this.tags = tags;
    }

    public void addTag(String tag){

        if(this.tags == null)   this.tags = new ArrayList<>();

        if(isPassValidTag(tag)){

            this.tags.add(Tag.builder()
                    .article(this)
                    .name(tag.trim())
                    .build());
        }
    }

    public boolean isPassValidTag(String tag){
        if(tag == null) return false;
        String trimTag = tag.trim();

        if(StringUtils.isEmpty(trimTag))    return false;
        if(this.tags.contains(trimTag)) return false;

        return true;
    }

    public Article updateSubject(String subject) {
        this.subject = subject;

        return this;
    }

    public Article updateContents(String contents) {
        this.contents = contents;

        return this;
    }
}
