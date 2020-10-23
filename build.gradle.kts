plugins {
    kotlin("js") version "1.4.10" apply false
}

repositories {
    mavenCentral()
    jcenter()
}

tasks.register<GenerateReadme>("generateReadme")
