group = "org.nice.soft"
version = "0.01"

plugins {
    kotlin("jvm") version "1.7.22" apply false
    id("org.jetbrains.kotlin.plugin.spring") version "1.7.22" apply false
    id("org.jetbrains.kotlin.plugin.noarg") version "1.7.22" apply false
}


repositories {
    maven {
        url = uri("https://repo.maven.apache.org/maven2")
        name = "Maven Central"
    }
}
