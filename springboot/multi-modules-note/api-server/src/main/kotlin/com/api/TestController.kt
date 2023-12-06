package com.api

import com.core.module_one.core.ModuleOneComponent
import com.core.module_two.core.ModuleTwoComponent
import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController(
    private val moduleTwoComponent: ModuleTwoComponent
) {

    private val log = KotlinLogging.logger {}

    @GetMapping
    fun test(){

        moduleTwoComponent.test()
        log.info("test")
    }
}