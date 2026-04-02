package org.performance.article.service

import org.performance.article.dto.ArticleDetailDto
import org.performance.article.entity.ArticleDetail
import org.performance.article.repository.ArticleDetailRepository
import org.performance.article.repository.ArticleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val articleDetailRepository: ArticleDetailRepository,
) {
    fun sleepQuery3() {
        this.articleRepository.sleep(1)
        this.articleRepository.sleep(2)
    }

    fun sleepThread3() {
        Thread.sleep(3000L)
    }

    fun findArticleDetailWithOptimisticLock(articleDetailId: Long): ArticleDetailDto {
        val articleDetail = this.articleDetailRepository
            .findByIdWithOptimisticLock(articleDetailId)!!

        return ArticleDetailDto.of(articleDetail)
    }

    fun findArticleDetailWithSharedLock(articleDetailId: Long): ArticleDetailDto {
        val articleDetail = this.articleDetailRepository
            .findByIdWithSharedLock(articleDetailId)!!

        return ArticleDetailDto.of(articleDetail)
    }
}