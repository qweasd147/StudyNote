package com.core

import com.core.config.ConfigModules
import com.core.config.CoreImportSelector
import org.springframework.context.annotation.Import
import org.springframework.core.annotation.AliasFor

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
//@Import(CoreApplication::class)
@Import(CoreImportSelector::class)
annotation class EnableModuleCore(

    @get:AliasFor(value = "value")
    vararg val initConfigs: ConfigModules = [],

    @get:AliasFor(value = "initConfigs")
    val value: Array<ConfigModules> = [],
)
