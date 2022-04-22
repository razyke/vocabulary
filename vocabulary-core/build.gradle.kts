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

val springVersion = "5.3.9"
dependencies {

    /* Spring Dependencies */
    implementation("org.springframework:spring-core:$springVersion")
    implementation("org.springframework:spring-context:$springVersion")
    implementation("org.springframework.data:spring-data-jpa:2.5.3")

    /* DB part */
    implementation("com.h2database:h2:1.4.200")
    implementation("org.hibernate:hibernate-core:5.5.5.Final")
    implementation("com.zaxxer:HikariCP:5.0.0")

    /* Logs */
    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("ch.qos.logback:logback-classic:1.2.5")
    implementation("ch.qos.logback:logback-core:1.2.5")

    implementation("javax.annotation:javax.annotation-api:1.3.2")
    /* kotlin */
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.5.21")
    implementation(kotlin("stdlib"))
}
