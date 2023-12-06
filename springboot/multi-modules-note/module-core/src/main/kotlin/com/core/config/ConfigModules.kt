package com.core.config

import com.core.module_one.ModuleOneConfig
import com.core.module_two.ModuleTwoConfig
import kotlin.reflect.KClass

enum class ConfigModules(
    val configClass: KClass<out ModuleConfiguration>
) {

    ModuleOneCore(ModuleOneConfig::class),
    ModuleTwoCore(ModuleTwoConfig::class),
}