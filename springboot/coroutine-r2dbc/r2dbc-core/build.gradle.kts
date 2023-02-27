allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

/*
val querydslVersion = "5.0.0"
val querydslSrcDir = "src/main/generated"
sourceSets {
    main {
        java {
            srcDirs(querydslSrcDir)
        }
    }
}
*/

dependencies {

    api("org.springframework.boot:spring-boot-starter-data-r2dbc")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    api("io.projectreactor.kotlin:reactor-kotlin-extensions")

    //implementation("com.github.jasync-sql:jasync-r2dbc-mysql:2.1.7")
    implementation("com.github.jasync-sql:jasync-r2dbc-mysql:2.1.23")
    //implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.flywaydb:flyway-mysql")
    implementation("org.flywaydb:flyway-core")

    //for querydsl
    //annotationProcessor("com.querydsl:querydsl-apt:${dependencyManagement.importedProperties["querydsl.version"]}:jakarta")
    //implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")

    //implementation("com.querydsl:querydsl-jpa:$querydslVersion:jakarta")
    //kapt("com.querydsl:querydsl-apt:$querydslVersion:jakarta")

    //for logging
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth:3.1.5")


}