package com.example.hibernatelock.article.entity

import javax.persistence.*


@Entity
@Table(name = "article3")
class Article(
    subject: String,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idx: Long? = null
        protected set

    @Column(nullable = false)
    var subject: String = subject
        protected set

    var viewCount: Long = 0
        protected set

    @Version
    var version: Int = 0
        protected set

    fun updateContent(subject: String) {

        this.subject = subject
    }
}