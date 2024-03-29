import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {

	id("org.springframework.boot") apply false
	//id("io.spring.dependency-management") apply false
	id("io.spring.dependency-management")

	//kotlin("jvm") apply false
	kotlin("jvm")
	kotlin("plugin.spring") apply false
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


//java {
//	sourceCompatibility = JavaVersion.VERSION_17
//}

java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

//val springCloudDependenciesVersion: String by project
//extra["springCloudVersion"] = springCloudDependenciesVersion

subprojects {

	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")

	dependencies {

		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

		//logger
		api("io.github.microutils:kotlin-logging-jvm:3.0.4")
		api("org.slf4j:slf4j-api:2.0.5")

		annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	}

	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
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
}
