package com.core.module_two

import com.core.config.ModuleConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [ModuleTwoConfig::class])
class ModuleTwoConfig: ModuleConfiguration