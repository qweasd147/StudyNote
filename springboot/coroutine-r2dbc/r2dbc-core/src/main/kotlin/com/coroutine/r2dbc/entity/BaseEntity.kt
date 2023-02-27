package com.coroutine.r2dbc.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

//@MappedSuperclass
//@EntityListeners(value = [AuditingEntityListener::class])
open class BaseEntity {

    @Id
    var idx: Long? = null
        protected set

    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()
        protected set

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()
        protected set
}
