package org.performance.article.entity

import jakarta.persistence.*


@Entity
@Table(name = "article")
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

    fun updateSubject(subject: String) {

        this.subject = subject
    }
}