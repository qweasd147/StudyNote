package com.example.hibernatelock

import com.example.hibernatelock.article.service.ArticleService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.ExpectSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class OptimisticRockTest(
    private val articleService: ArticleService
): ExpectSpec({

    extensions(SpringTestExtension(SpringTestLifecycleMode.Root))

})