plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinSerialization)
    application
}

group = "com.worldline.quiz"
version = "1.0.0"

application {
    mainClass.set("com.worldline.quiz.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.config.yaml)
}

ktor {
    fatJar {
        archiveFileName.set("fat.jar")
    }
    docker {
        externalRegistry.set(
            io.ktor.plugin.features.DockerImageRegistry.dockerHub(
                appName = provider { "ktor-quiz" },
                username = providers.environmentVariable("KTOR_IMAGE_REGISTRY_USERNAME"),
                password = providers.environmentVariable("KTOR_IMAGE_REGISTRY_PASSWORD")
            )
        )
    }
}