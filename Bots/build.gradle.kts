import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
    kotlin("plugin.serialization") version "1.7.20"
}

group = "aboba"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("me.jakejmattson:DiscordKt:0.23.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation(files("twitch-client-0.0.1.jar"))
    implementation("com.beust:klaxon:5.5")
    implementation("io.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.0.7")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}


tasks.withType<Jar>{
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    configurations["runtimeClasspath"].forEach { file->
        from(zipTree(file.absoluteFile))
    }
    manifest{
        archiveBaseName.set("Hryaks")
        archiveVersion.set("")
        attributes(mapOf(
            "Main-Class" to "aboba.Main",
        ))
    }
    destinationDir = file("D:\\out")
}