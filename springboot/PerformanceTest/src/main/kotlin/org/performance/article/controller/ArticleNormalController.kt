package org.performance.article.controller

import org.performance.article.dto.ArticleDetailDto
import org.performance.article.service.ArticleService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/normal")
class ArticleNormalController(
    private val articleService: ArticleService,
) {

    // /normal/detail/1/optimistic-lock
    @GetMapping("/detail/{articleDetailId}/optimistic-lock")
    fun optimisticLock(
        @PathVariable articleDetailId: Long
    ): ArticleDetailDto {

        return this.articleService
            .findArticleDetailWithOptimisticLock(articleDetailId)
    }

    // /normal/detail/2/shared-lock
    @GetMapping("/detail/{articleDetailId}/shared-lock")
    fun sharedLock(
        @PathVariable articleDetailId: Long
    ): ArticleDetailDto {

        return this.articleService
            .findArticleDetailWithSharedLock(articleDetailId)
    }
}