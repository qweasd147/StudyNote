package org.performance.article.repository

import jakarta.persistence.*
import org.performance.article.entity.Article
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface ArticleRepository : JpaRepository<Article, Long> {

    @Query("select sleep(:seconds)", nativeQuery = true)
    fun sleep(seconds: Int)

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