package com.core.module_one

import com.core.config.ModuleConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [ModuleOneConfig::class])
class ModuleOneConfig: ModuleConfiguration