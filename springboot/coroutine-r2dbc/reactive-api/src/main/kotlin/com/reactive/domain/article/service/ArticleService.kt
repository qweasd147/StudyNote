package com.reactive.domain.article.service

import com.coroutine.r2dbc.entity.Article
import com.coroutine.r2dbc.repository.ArticleRepository
import kotlinx.coroutines.flow.toList
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlinx.coroutines.coroutineScope
import kotlin.system.measureTimeMillis
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Service
@Transactional
class ArticleService(
    private val articleRepository: ArticleRepository,
) {

    val log = KotlinLogging.logger {}

    suspend fun test(): List<Article> = coroutineScope {

        val runningSeconds = measureTimeMillis {

            articleRepository.blockingFor5Sec()
            articleRepository.blockingFor5Sec()
        }.toDuration(DurationUnit.MILLISECONDS).toDouble(DurationUnit.SECONDS)

        log.info("running time $runningSeconds 초")

        return@coroutineScope articleRepository.findAll().toList()
    }
}