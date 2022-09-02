package com.example.hibernatelock.article.service

import com.example.hibernatelock.article.dto.ArticleCreateRequest
import com.example.hibernatelock.article.dto.ArticleUpdateRequest
import com.example.hibernatelock.article.entity.Article
import com.example.hibernatelock.article.entity.ArticleDetail
import com.example.hibernatelock.article.repository.ArticleDetailRepository
import com.example.hibernatelock.article.repository.ArticleRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Service
@Transactional(readOnly = true)
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val articleDetailRepository: ArticleDetailRepository,
) {

    @Transactional
    fun create(
        requestParam: ArticleCreateRequest
    ): ArticleDetail {

        val (article, articleDetail) = requestParam.toEntity()

        articleRepository.save(article)
        articleDetailRepository.save(articleDetail)

        return articleDetail
    }

    fun findByIdWithOptimisticLock(
        id: Long
    ): Article = this.articleRepository.findByIdWithOptimisticLock(id)
        ?: throw IllegalArgumentException()

    @Transactional
    fun findByIdWithOptimisticForceLock(
        id: Long
    ): Article = this.articleRepository.findByIdWithOptimisticForceLock(id)
        ?: throw IllegalArgumentException()

    fun findAll(): List<Article> = this.articleRepository.findAll()

    @Transactional(isolation = Isolation.READ_COMMITTED)
    fun findDetailByIdWithOptimisticLock(id: Long): ArticleDetail {

        val articleDetail = this.articleDetailRepository.findByIdWithOptimisticLock(id)
            ?: throw IllegalArgumentException()

        return articleDetail
    }

    @Transactional
    fun findDetailByIdWithOptimisticForceLock(id: Long): ArticleDetail {

        return this.articleDetailRepository.findByIdWithOptimisticForceLock(id)
            ?: throw IllegalArgumentException()
    }

    @Transactional
    fun updateDetailWithOptimisticForceLock(
        id: Long, articleUpdateRequest: ArticleUpdateRequest
    ): ArticleDetail {

        val articleDetail = this.articleDetailRepository.findByIdWithOptimisticForceLock(id)
            ?: throw IllegalArgumentException()

        return articleDetail.also {

            it.update(
                subject = articleUpdateRequest.subject,
                contents = articleUpdateRequest.contents,
            )
        }
    }

    fun findByIdWithSharedLock(
        idx: Long
    ): Article = this.articleRepository.findByIdWithSharedLock(idx)
        ?: throw IllegalArgumentException()

    @Transactional
    fun findByIdWithWriteLock(
        idx: Long
    ): Article = this.articleRepository.findByIdWithWriteLock(idx)
        ?: throw IllegalArgumentException()

    fun findDetailByIdWithSharedLock(
        idx: Long
    ): ArticleDetail = this.articleDetailRepository.findByIdWithSharedLock(idx)
        ?: throw IllegalArgumentException()

    @Transactional
    fun findDetailByIdWithWriteLock(
        idx: Long
    ): ArticleDetail = this.articleDetailRepository.findByIdWithWriteLock(idx)
        ?: throw IllegalArgumentException()
}