import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.openjfx.javafxplugin") version "0.0.10"
    application
    java
}

application {
    mainClass.set("org.nice.soft.vocabulary.gui.VocabularyGuiLauncherKt")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

javafx {
    modules("javafx.controls", "javafx.fxml")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.jfoenix:jfoenix:9.0.10")
    implementation("io.datafx:flow:8.0.7")
    implementation("io.datafx:datafx:8.0.7")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation(project(":vocabulary-core"))

    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.7.22")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.22")
}

tasks.jar {
    archiveBaseName.set("vocabulary")
    manifest {
        attributes["Main-Class"] = "org.nice.soft.vocabulary.gui.VocabularyGuiLauncherKt"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

tasks.withType<org.gradle.jvm.tasks.Jar> { duplicatesStrategy = DuplicatesStrategy.INCLUDE}