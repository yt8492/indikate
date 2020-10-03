import java.text.SimpleDateFormat
import java.util.Date

plugins {
    kotlin("js") version "1.4.10"
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.5"
}

val libVersion = "0.0.1"

group = "com.yt8492"
version = libVersion

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-js"))
    implementation("org.jetbrains.kotlinx:kotlinx-nodejs:0.0.7")
}

kotlin {
    js(IR) {
        nodejs()
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(kotlin.sourceSets["main"].kotlin.srcDirs)
}

publishing {
    publications.invoke {
        register("maven", MavenPublication::class) {
            from(components["kotlin"])
            artifact(sourcesJar.get())
        }
    }
}

val githubUrl = "https://github.com/yt8492/indikate"
val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ")

bintray {
    user = project.property("bintrayUser")?.toString()
    key = project.property("bintrayKey")?.toString()
    publish = true
    setPublications("maven")
    pkg.apply {
        repo = "maven"
        name = "indikate"
        setLicenses("MIT")
        websiteUrl = githubUrl
        vcsUrl = "$githubUrl.git"
        issueTrackerUrl = "$githubUrl/issues"
        publicDownloadNumbers = true
        version.name = libVersion
        version.released = dateFormat.format(Date())
        setLabels("kotlin", "kotlinjs", "nodejs")
        description = "Simple Server-side Framework for Kotlin/JS"
        desc = description
    }
}
