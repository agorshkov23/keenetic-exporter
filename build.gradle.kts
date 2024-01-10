plugins {
    kotlin("jvm") version "1.9.21"
    id("org.springframework.boot") version "3.2.1"
    id("org.jetbrains.kotlin.plugin.spring") version "1.9.22"
}

apply(plugin = "io.spring.dependency-management")

group = "io.github.ialegor.exporter.keenetic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
