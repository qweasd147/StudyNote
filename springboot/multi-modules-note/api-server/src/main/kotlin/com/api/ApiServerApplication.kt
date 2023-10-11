package com.api

import com.core.EnableModuleCore
import com.core.config.ConfigModules
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableModuleCore(
	initConfigs = [ConfigModules.ModuleOneCore]
)
class ApiServerApplication

fun main(args: Array<String>) {
	runApplication<ApiServerApplication>(*args)
}
