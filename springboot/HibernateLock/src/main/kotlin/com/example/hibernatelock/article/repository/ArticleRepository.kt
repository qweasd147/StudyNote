package com.example.hibernatelock.article.repository

import com.example.hibernatelock.article.entity.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import javax.persistence.LockModeType

interface ArticleRepository: JpaRepository<Article, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @Query("from Article article where article.idx = :idx")
    fun findByIdWithOptimisticLock(idx: Long): Article?

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("from Article article where article.idx = :idx")
    fun findByIdWithOptimisticForceLock(idx: Long): Article?

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("from Article article where article.idx = :idx")
    fun findByIdWithSharedLock(idx: Long): Article?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("from Article article where article.idx = :idx")
    fun findByIdWithWriteLock(idx: Long): Article?
}