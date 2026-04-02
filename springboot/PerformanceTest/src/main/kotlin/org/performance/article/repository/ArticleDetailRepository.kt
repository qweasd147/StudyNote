package org.performance.article.repository

import jakarta.persistence.*
import org.performance.article.entity.ArticleDetail
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface ArticleDetailRepository : JpaRepository<ArticleDetail, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @Query("from ArticleDetail articleDetail where articleDetail.idx = :idx")
    @EntityGraph(attributePaths = ["article"])
    fun findByIdWithOptimisticLock(idx: Long): ArticleDetail?

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("from ArticleDetail articleDetail where articleDetail.idx = :idx")
    @EntityGraph(attributePaths = ["article"])
    fun findByIdWithOptimisticForceLock(idx: Long): ArticleDetail?

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("from ArticleDetail articleDetail where articleDetail.idx = :idx")
    @EntityGraph(attributePaths = ["article"])
    fun findByIdWithSharedLock(idx: Long): ArticleDetail?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("from ArticleDetail articleDetail where articleDetail.idx = :idx")
    fun findByIdWithWriteLock(idx: Long): ArticleDetail?

    @Query("from ArticleDetail articleDetail where articleDetail.idx = :idx")
    @EntityGraph(attributePaths = ["article"])
    fun findByIdWithArticle(articleDetailId: Long): ArticleDetail?
}