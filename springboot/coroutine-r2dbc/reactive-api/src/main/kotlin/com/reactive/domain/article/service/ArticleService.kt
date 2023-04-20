package com.reactive.domain.article.service

import com.coroutine.r2dbc.repository.ArticleRepository
import kotlinx.coroutines.*
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.system.measureTimeMillis
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
) {

    val log = KotlinLogging.logger {}

    @Transactional(readOnly = true)
    suspend fun callQueries() = coroutineScope {

        val runningSeconds = measureTimeMillis {

            val deffer1 = async { articleRepository.blockingFor5Sec() }
            val deffer2 = async { articleRepository.blockingFor(3) }

            awaitAll(deffer1, deffer2)

        }.toDuration(DurationUnit.MILLISECONDS).toDouble(DurationUnit.SECONDS)

        log.info("running time $runningSeconds 초")
    }

    @Transactional(readOnly = true)
    suspend fun maybe8Seconds() {

        articleRepository.blockingFor(5)
        articleRepository.blockingFor(3)
    }

    @Transactional(readOnly = true)
    suspend fun maybe5Seconds() {

        articleRepository.blockingFor(3)
        articleRepository.blockingFor(2)
    }

    fun blocking5Seconds(): Int {
        Thread.sleep(5000L)
        return 1
    }

    suspend fun nonBlocking5Seconds(): Int = coroutineScope {

        return@coroutineScope withContext(Dispatchers.IO) {
            Thread.sleep(5000L)
            return@withContext 1
        }
    }
}