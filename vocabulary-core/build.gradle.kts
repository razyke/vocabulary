plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.jetbrains.kotlin.plugin.noarg")
}

noArg {
    annotation("javax.persistence.Entity")
}

repositories {
    mavenCentral()
}

val springVersion = "5.3.18"
dependencies {

    /* Spring Dependencies */
    implementation("org.springframework:spring-core:$springVersion")
    implementation("org.springframework:spring-context:$springVersion")
    implementation("org.springframework.data:spring-data-jpa:2.6.3")

    /* DB part */
    implementation("com.h2database:h2:2.1.210")
    implementation("org.hibernate:hibernate-core:5.6.7.Final")
    implementation("com.zaxxer:HikariCP:5.0.1")

    /* Logs */
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("ch.qos.logback:logback-core:1.2.11")

    implementation("javax.annotation:javax.annotation-api:1.3.2")
    /* kotlin */
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.6.20")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.20")
}
