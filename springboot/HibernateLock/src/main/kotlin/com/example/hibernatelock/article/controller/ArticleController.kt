package com.example.hibernatelock.article.controller

import com.example.hibernatelock.article.dto.ArticleCreateRequest
import com.example.hibernatelock.article.dto.ArticleDetailDto
import com.example.hibernatelock.article.dto.ArticleDto
import com.example.hibernatelock.article.dto.ArticleUpdateRequest
import com.example.hibernatelock.article.entity.Article
import com.example.hibernatelock.article.entity.ArticleDetail
import com.example.hibernatelock.article.service.ArticleService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/article")
class ArticleController(
    private val articleService: ArticleService,
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(requestParam: ArticleCreateRequest) {

        this.articleService.create(requestParam)
    }

    @GetMapping("/optimistic/{id}")
    fun findByIdWithOptimisticLock(
        @PathVariable id: Long
    ): ArticleDto = ArticleDto.of(this.articleService.findByIdWithOptimisticLock(id))

    @GetMapping("/optimistic-force/{id}")
    fun findByIdWithOptimisticForceLock(
        @PathVariable id: Long
    ): ArticleDto = ArticleDto.of(this.articleService.findByIdWithOptimisticForceLock(id))

    @GetMapping
    fun findAll(): List<Article> = this.articleService.findAll()

    @GetMapping("/detail/optimistic/{id}")
    fun findDetailByIdWithOptimisticLock(
        @PathVariable id: Long
    ): ArticleDetailDto {

        return ArticleDetailDto.of(
            this.articleService.findDetailByIdWithOptimisticLock(id)
        )
    }

    @GetMapping("/detail/optimistic-force/{id}")
    fun findDetailByIdWithOptimisticForceLock(
        @PathVariable id: Long
    ): ArticleDetailDto {

        return ArticleDetailDto.of(
            this.articleService.findDetailByIdWithOptimisticForceLock(id)
        )
    }

    @PutMapping("/detail/optimistic-force/{id}")
    fun updateDetailByIdWithOptimisticForceLock(
        @PathVariable id: Long,
        articleUpdateRequest: ArticleUpdateRequest,
    ): ArticleDetailDto {

        val articleDetail = this.articleService.updateDetailWithOptimisticForceLock(id, articleUpdateRequest)
        return ArticleDetailDto.of(
            articleDetail
        )
    }

    @GetMapping("/shared/{id}")
    fun findByIdWithSharedLock(
        @PathVariable id: Long
    ): ArticleDto = ArticleDto.of(this.articleService.findByIdWithSharedLock(id))

    @GetMapping("/write/{id}")
    fun findByIdWithWriteLock(
        @PathVariable id: Long
    ): ArticleDto = ArticleDto.of(this.articleService.findByIdWithWriteLock(id))

    @GetMapping("/detail/shared/{id}")
    fun findDetailByIdWithSharedLock(
        @PathVariable id: Long
    ): ArticleDetailDto {

        return ArticleDetailDto.of(
            this.articleService.findDetailByIdWithSharedLock(id)
        )
    }

    @GetMapping("/detail/write/{id}")
    fun findDetailByIdWithWriteLock(
        @PathVariable id: Long
    ): ArticleDetailDto {

        return ArticleDetailDto.of(
            this.articleService.findDetailByIdWithWriteLock(id)
        )
    }
}