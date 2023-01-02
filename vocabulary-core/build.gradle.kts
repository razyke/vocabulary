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

tasks.test {
    useJUnitPlatform()
}

val springVersion = "5.3.24"
val aspectJVersion = "1.9.19"
dependencies {

    /* Spring Dependencies */
    implementation("org.springframework:spring-core:$springVersion")
    implementation("org.springframework:spring-context:$springVersion")
    implementation("org.springframework.data:spring-data-jpa:2.6.4")

    /* DB part */
    implementation("com.h2database:h2:2.1.214")
    implementation("org.hibernate:hibernate-core:5.6.7.Final")
    implementation("org.hibernate:hibernate-validator:5.4.3.Final")
    implementation("javax.el:javax.el-api:3.0.0")
    implementation("org.glassfish.web:javax.el:2.2.6")
    implementation("com.zaxxer:HikariCP:5.0.1")

    /* Logs */
    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation("ch.qos.logback:logback-core:1.4.5")

    /* Other */
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("org.aspectj:aspectjrt:$aspectJVersion")
    implementation("org.aspectj:aspectjweaver:$aspectJVersion")
    testImplementation("org.mockito:mockito-junit-jupiter:4.10.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")

    /* kotlin */
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.7.22")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.22")
}
