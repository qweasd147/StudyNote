package com.example.hibernatelock.article.entity

import javax.persistence.*

@Entity
class ArticleDetail(
    subject: String,
    contents: String,
    article: Article,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var idx: Long? = null
        protected set

    @Column(nullable = false)
    var subject: String = subject
        protected set

    @Column(nullable = false)
    @Lob
    var contents: String = contents
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "articleIdx", updatable = false, insertable = false)
    var article: Article = article
        protected set

    @Version
    var version: Int = 0
        protected set


    fun update(subject: String, contents: String) {

        this.subject = subject
        this.contents = contents

//        this.article.updateContent(
//            subject = subject
//        )
    }
}