package com.example.demo.repository;

import com.example.demo.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TagRepository extends JpaRepository<Tag, Long> {

    @Modifying
    @Query("UPDATE Tag tag SET tag.name = :tag WHERE tag.idx = :idx")
    void updateByQuery(@Param("idx") Long idx
            , @Param("tag") String tag);
}
