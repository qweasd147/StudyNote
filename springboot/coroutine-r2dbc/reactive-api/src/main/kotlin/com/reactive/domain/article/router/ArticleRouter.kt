package com.reactive.domain.article.router

import com.reactive.domain.article.service.ArticleService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ArticleRouter(
    private val articleApiRouter: ArticleApiRouter
) {


    @Bean
    fun router() = coRouter {
        GET("/test", articleApiRouter::test1)
    }
}

@Component
class ArticleApiRouter(
    private val articleService: ArticleService
){

    suspend fun test1(request: ServerRequest): ServerResponse{

        this.articleService.test()

        return ServerResponse.ok().buildAndAwait()
    }
}