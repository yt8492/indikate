plugins {
    kotlin("js")
}

version = "0.0.1"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-js"))
    implementation(project(":library"))
    implementation(npm("webpack-node-externals", "2.5.1"))
}

kotlin {
    js(IR) {
        browser {
            webpackTask {
                outputFileName = "main.js"
            }
        }
        nodejs()
        binaries.executable()
    }
}
