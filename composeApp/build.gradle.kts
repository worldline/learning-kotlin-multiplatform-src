@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.gradle.kotlin.dsl.implementation
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqldelight)
}

kotlin {

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    jvm("desktop")
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }



    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }





    sourceSets {
        val desktopMain by getting


        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.kotlin.navigation)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kstore)

            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines.extensions)

        }
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.kstore.file)
            //implementation(libs.kotlinx.coroutines.android)

            implementation(libs.sqldelight.android.driver)
            //debugImplementation(compose.uiTooling)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(compose.desktop.linux_arm64)
            implementation(libs.ktor.client.apache)
            implementation(libs.kstore.file)
            implementation(libs.kotlinx.coroutines.swing)
            //implementation(libs.logback)
            implementation(libs.sqldelight.jvm.driver)
            implementation(libs.logback)
            implementation(libs.slf4jApi)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin) //for iOS
            implementation(libs.kstore.file)
            implementation(libs.sqldelight.ios.driver)
        }
        wasmJsMain.dependencies {
            //implementation(libs.ktor.client.js)
            implementation(libs.kstore.storage)
            implementation(libs.sqldelight.webworker.driver)
            implementation(npm("sql.js", libs.versions.sqlJs.get()))
            implementation(devNpm("copy-webpack-plugin", libs.versions.webPackPlugin.get()))

        }
    }
}

android {
    namespace = "com.worldline.quiz"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.worldline.quiz"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        //JAVA Version for Android -> https://developer.android.com/build/jdks?hl=fr
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}


compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.worldline.quiz"
            packageVersion = "1.0.0"
        }
        buildTypes.release.proguard {
            isEnabled.set(false)  // Désactiver complètement
        }
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName = "com.myapplication.common.cache"
            generateAsync = true
            verifyMigrations = false
        }
    }
    linkSqlite = true
}

tasks.register<Exec>("deployToPi") {
    group = "deployment"
    description = "Deploy application to Raspberry Pi"

    dependsOn("packageReleaseUberJarForCurrentOS")

    // Spécifier le chemin complet du script
    commandLine("bash", "${project.rootDir}/deploy.sh")

    // Afficher la sortie du script en temps réel
    standardOutput = System.out
    errorOutput = System.err

    doFirst {
        val deployScript = File(project.rootDir, "deploy.sh")
        if (!deployScript.exists()) {
            throw GradleException("deploy.sh script not found at: ${deployScript.absolutePath}")
        }
        println("🚀 Starting deployment script...")
    }
}

