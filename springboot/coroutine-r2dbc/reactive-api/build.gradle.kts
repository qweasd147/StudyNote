tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {

    api(project(":r2dbc-core"))

    implementation("org.springframework.boot:spring-boot-starter-webflux")
}