package com.coroutine.r2dbc.repository

import com.coroutine.r2dbc.entity.Article
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : CoroutineCrudRepository<Article, Long> {

    @Query("SELECT sleep(1) FROM Article article LIMIT 5")
    suspend fun blockingFor5Sec(): List<Int>

    @Query("SELECT sleep(1) FROM Article article LIMIT :seconds")
    suspend fun blockingFor(seconds: Int): List<Int>
}