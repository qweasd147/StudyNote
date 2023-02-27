package com.coroutine.r2dbc.entity

import org.springframework.data.relational.core.mapping.Table

@Table("article")
class Article(
    title: String,
    contents: String,
) : BaseEntity() {

    var title: String = title
        protected set

    var contents: String = contents
        protected set
}