package com.example.hibernatelock.article.dto

import com.example.hibernatelock.article.entity.Article
import com.example.hibernatelock.article.entity.ArticleDetail
import javax.validation.constraints.NotBlank

class ArticleCreateRequest(

    var subject: String = "",
    var contents: String = "",
) {

    fun toEntity(): Pair<Article, ArticleDetail> {

        val article = Article(
            subject = subject
        )

        val articleDetail = ArticleDetail(
            subject = subject, contents = contents, article = article
        )

        return article to articleDetail
    }
}


class ArticleDto(
    val subject: String,
    val viewCount: Long,
    val version: Int,
) {


    companion object {

        fun of(
            article: Article
        ): ArticleDto = ArticleDto(
            subject = article.subject,
            viewCount = article.viewCount,
            version = article.version,
        )
    }
}

class ArticleDetailDto(
    val subject: String,
    val contents: String,
    val viewCount: Long,
    val articleVersion: Int,
    val detailVersion: Int,
) {

    companion object {

        fun of(
            articleDetail: ArticleDetail
        ): ArticleDetailDto = ArticleDetailDto(
            subject = articleDetail.subject,
            contents = articleDetail.contents,
            viewCount = articleDetail.article.viewCount,
            articleVersion = articleDetail.article.version,
            detailVersion = articleDetail.version,
        )
    }
}

class ArticleUpdateRequest(

    @field:NotBlank
    var subject: String = "",
    @field:NotBlank
    var contents: String = ""
)