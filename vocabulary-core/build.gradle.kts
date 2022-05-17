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

val springVersion = "5.3.20"
val aspectJVersion = "1.9.9.1"
dependencies {

    /* Spring Dependencies */
    implementation("org.springframework:spring-core:$springVersion")
    implementation("org.springframework:spring-context:$springVersion")
    implementation("org.springframework.data:spring-data-jpa:2.6.4")

    /* DB part */
    implementation("com.h2database:h2:2.1.212")
    implementation("org.hibernate:hibernate-core:5.6.7.Final")
    implementation("org.hibernate:hibernate-validator:5.4.3.Final")
    implementation("com.zaxxer:HikariCP:5.0.1")

    /* Logs */
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("ch.qos.logback:logback-core:1.2.11")

    /* Other */
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("org.aspectj:aspectjrt:$aspectJVersion")
    implementation("org.aspectj:aspectjweaver:$aspectJVersion")

    /* kotlin */
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.6.21")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.21")
}
