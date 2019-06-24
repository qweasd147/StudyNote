package com.example.demo.repository;

import com.example.demo.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT article FROM Article article JOIN article.tags tags WHERE tags.tag IN :tags")
    List<Article> findBySeveralTags(@Param("tags") List<String> tags);
}
