package com.core.module_one.core

import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class ModuleOneComponent {

    private val log = KotlinLogging.logger {}

    init {
        log.info("init ModuleOneComponent")
    }

    fun test() {
        log.info("ready")
    }
}