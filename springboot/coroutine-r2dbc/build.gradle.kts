import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management")

    kotlin("jvm")
    kotlin("plugin.spring") apply false
    kotlin("plugin.jpa")

    kotlin("kapt") version "1.7.22" apply true
}

val projectGroup: String by project
val applicationVersion: String by project
allprojects {

    group = projectGroup
    version = applicationVersion

    repositories {
        mavenCentral()
    }
}

java.sourceCompatibility = JavaVersion.VERSION_17

/*
configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}
*/

subprojects {

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-jpa")

    /*
    dependencyManagement {
        val springCloudDependenciesVersion: String by project
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudDependenciesVersion}")
        }
    }
    */

    dependencies {

        //implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
        //implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        //implementation("org.springframework.boot:spring-boot-starter-validation")
        //implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        //implementation("org.flywaydb:flyway-core")
        //implementation("org.flywaydb:flyway-mysql")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        //developmentOnly("org.springframework.boot:spring-boot-devtools")
        runtimeOnly("com.mysql:mysql-connector-j")

        api("io.github.microutils:kotlin-logging-jvm:3.0.4")
        api("org.slf4j:slf4j-api:2.0.5")

        //for test
        //testImplementation("io.kotest:kotest-extensions-spring:4.4.3")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.mockk:mockk:1.13.2")
        testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")

        testImplementation("io.kotest:kotest-runner-junit5-jvm:5.5.4")
        testImplementation("io.kotest:kotest-assertions-core-jvm:5.5.4")

    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.getByName("bootJar") {
        enabled = false
    }

    tasks.getByName("jar") {
        enabled = true
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
}