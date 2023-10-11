package com.core.config

import com.core.EnableModuleCore
import mu.KotlinLogging
import org.springframework.context.annotation.DeferredImportSelector
import org.springframework.core.type.AnnotationMetadata

class CoreImportSelector : DeferredImportSelector {

    private val log = KotlinLogging.logger {}

    override fun selectImports(importingClassMetadata: AnnotationMetadata): Array<String> {
        log.info("test")

        return getValues(importingClassMetadata).map {
            it.configClass.java.name as String
        }.toTypedArray()
    }

    @Suppress("UNCHECKED_CAST")
    private fun getValues(metadata: AnnotationMetadata): Array<ConfigModules> {
        val configs = metadata.getAnnotationAttributes(
            EnableModuleCore::class.java.name
        )!!["value"] as? Array<ConfigModules>

        return configs!!
    }
}