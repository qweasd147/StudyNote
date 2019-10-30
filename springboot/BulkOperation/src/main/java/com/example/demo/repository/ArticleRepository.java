package com.example.demo.repository;

import com.example.demo.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Modifying
    @Query("UPDATE Article article SET article.subject = :subject, article.contents = :contents WHERE article.idx = :idx")
    void updateByQuery(@Param("idx") Long idx
                        , @Param("subject") String subject
                        , @Param("contents") String contents);
}
