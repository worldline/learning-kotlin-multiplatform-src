import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType
//import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqldelight)
    
}

kotlin {
    js(IR){
        moduleName = "Quiz"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {

                    static = (static ?: mutableListOf()).apply {
                        add(project.rootDir.path)
                        add(project.rootDir.path + "/composeApp/")
                    }
                }
            }
        }
        binaries.executable()
    }
    
 

     
    
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
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
    
    sourceSets {
        val desktopMain by getting
        
        val jsMain by getting
       
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)

            implementation(libs.ktor.client.core) // core source of ktor
            implementation(libs.ktor.client.content.negotiation) // Simplify handling of content type based deserialization
            implementation(libs.ktor.serialization.kotlinx.json) // make your dataclasses serializable
            
            implementation(libs.sqldelight.coroutines.extensions)
            implementation(libs.kotlin.navigation)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
        }
        
        
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            
            implementation(libs.ktor.client.okhttp)
            implementation(libs.sqldelight.android.driver)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.ktor.client.apache)
            implementation(libs.sqldelight.jdbc.driver)
        }
        
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin) //for iOS
            implementation(libs.sqldelight.native.driver)
        }
        
        jsMain.dependencies {
            implementation("io.ktor:ktor-client-js:3.0.0-beta-2")
            implementation("app.cash.sqldelight:web-worker-driver:2.0.2")
            implementation(npm("@cashapp/sqldelight-sqljs-worker", "2.0.2"))
            implementation(npm("sql.js", "1.8.0"))  
            //implementation ("app.cash.sqldelight:primitive-adapters:2.0.2")
            implementation("org.jetbrains.kotlin:kotlin-stdlib-js:2.0.20-Beta2")
            implementation(devNpm("copy-webpack-plugin", "11.0.0"))
            
             implementation(kotlin("stdlib"))
            
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.worldline.quiz"
            packageVersion = "1.0.0"
        }
    }
}

sqldelight {
    //linkSqlite = true
    databases {
        create("Database") {
            packageName = "com.worldline.quiz.cache"
            listOf("sqldelight")
            generateAsync.set(true)
        }

    }
}

