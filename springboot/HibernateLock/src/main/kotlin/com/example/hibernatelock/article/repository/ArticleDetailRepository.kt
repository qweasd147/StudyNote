package com.example.hibernatelock.article.repository

import com.example.hibernatelock.article.entity.ArticleDetail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import javax.persistence.LockModeType

interface ArticleDetailRepository: JpaRepository<ArticleDetail, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @Query("from ArticleDetail articleDetail where articleDetail.idx = :idx")
    fun findByIdWithOptimisticLock(idx: Long): ArticleDetail?

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("from ArticleDetail articleDetail where articleDetail.idx = :idx")
    fun findByIdWithOptimisticForceLock(idx: Long): ArticleDetail?

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("from ArticleDetail articleDetail where articleDetail.idx = :idx")
    fun findByIdWithSharedLock(idx: Long): ArticleDetail?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("from ArticleDetail articleDetail where articleDetail.idx = :idx")
    fun findByIdWithWriteLock(idx: Long): ArticleDetail?
}