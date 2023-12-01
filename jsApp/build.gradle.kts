import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

//group = "com.devoxxfr2023.km"
version = "1.0-SNAPSHOT"

kotlin {
    js(IR) {
        binaries.executable()
        browser {}
    }

    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.web.core)
                implementation(compose.ui)
                @OptIn(ExperimentalComposeLibrary::class)
                implementation(compose.material3)
            }
        }
    }
}

compose.experimental {
    web.application {

    }
}