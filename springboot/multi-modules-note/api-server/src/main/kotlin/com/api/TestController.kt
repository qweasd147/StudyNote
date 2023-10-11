package com.api

import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController {

    private val log = KotlinLogging.logger {}

    @GetMapping
    fun test(){

        log.info("test")
    }
}