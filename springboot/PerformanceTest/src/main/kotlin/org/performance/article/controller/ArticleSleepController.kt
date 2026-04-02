package org.performance.article.controller

import org.performance.article.dto.ArticleCreateRequest
import org.performance.article.service.ArticleService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sleep")
class ArticleSleepController(
    private val articleService: ArticleService,
) {

    @GetMapping("/query")
    fun sleepQuery(requestParam: ArticleCreateRequest) {

        this.articleService.sleepQuery3()
    }

    @GetMapping("/thread")
    fun sleepThread() {
        this.articleService.sleepThread3()
    }
}