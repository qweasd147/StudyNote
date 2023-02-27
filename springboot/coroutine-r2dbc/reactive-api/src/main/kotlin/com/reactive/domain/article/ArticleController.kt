package com.reactive.domain.article

import com.reactive.domain.article.service.ArticleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/article")
class ArticleController(
    private val articleService: ArticleService
) {

    @GetMapping
    suspend fun test2(): Map<String, String> {

        articleService.test()

        return mapOf("test" to "")
    }
}