package com.graphql.demo.article;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@DynamicUpdate
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String contents;

    private LocalDateTime createdDate;

    @Builder
    private Article(String subject, String contents) {
        this.subject = subject;
        this.contents = contents;
        this.createdDate = LocalDateTime.now();
    }
}
