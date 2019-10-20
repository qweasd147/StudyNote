package com.example.demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@ToString(exclude={"article"})
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name="article_idx", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article article;

    private String name;


    @Builder
    public Tag(Article article, String name){
        this.article = article;
        this.name = name;
    }

    public void updateTagName(String name){
        this.name = name;
    }
}
