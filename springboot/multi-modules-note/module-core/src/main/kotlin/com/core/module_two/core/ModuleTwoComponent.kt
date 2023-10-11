package com.core.module_two.core

import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class ModuleTwoComponent {

    private val log = KotlinLogging.logger {}

    init {
        log.info("init ModuleTwoComponent")
    }
}