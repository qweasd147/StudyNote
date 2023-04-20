package com.reactive.domain.article.router

import com.reactive.domain.article.service.ArticleService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import kotlin.jvm.optionals.getOrDefault
import kotlin.jvm.optionals.getOrNull
import kotlin.system.measureTimeMillis
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Configuration
class ArticleRouter(
    private val articleApiRouter: ArticleApiRouter,
) {

    @Bean
    fun router() = coRouter {
        GET("/queries", articleApiRouter::callQueries)
        GET("/maybe8", articleApiRouter::maybe8Seconds)
        GET("/blocking", articleApiRouter::blockingApi)
        GET("/non-blocking", articleApiRouter::nonBlockingApi)
    }
}

@Component
class ArticleApiRouter(
    private val articleService: ArticleService,
) {
    val log = KotlinLogging.logger {}

    suspend fun callQueries(request: ServerRequest): ServerResponse {

        this.articleService.callQueries()

        return ServerResponse.ok().buildAndAwait()
    }

    suspend fun maybe8Seconds(request: ServerRequest): ServerResponse = coroutineScope {

        val runningSeconds = measureTimeMillis {

            val deffer1 = async { articleService.maybe5Seconds() }
            val deffer2 = async { articleService.maybe8Seconds() }

            awaitAll(deffer1, deffer2)
        }.toDuration(DurationUnit.MILLISECONDS).toDouble(DurationUnit.SECONDS)

        log.info("running time $runningSeconds 초") //전체 걸린 시간 8.x초

        ServerResponse.ok().bodyValueAndAwait(mapOf("message" to "success"))
    }

    suspend fun blockingApi(request: ServerRequest): ServerResponse {

        articleService.blocking5Seconds()

        return ServerResponse.ok().buildAndAwait()
    }

    suspend fun nonBlockingApi(request: ServerRequest): ServerResponse {

        articleService.nonBlocking5Seconds()

        return ServerResponse.ok().buildAndAwait()
    }
}