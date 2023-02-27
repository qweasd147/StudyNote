package com.reactive

import com.coroutine.r2dbc.EnableR2dbcCore
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableR2dbcCore
class ReactiveApiServerApplication

fun main(args: Array<String>) {
    runApplication<ReactiveApiServerApplication>(*args)
}