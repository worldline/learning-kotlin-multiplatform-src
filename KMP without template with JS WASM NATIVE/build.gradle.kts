

plugins {

    val kotlinVersion = "1.8.10"
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("8.0.2").apply(false)
    id("com.android.library").version("8.0.2").apply(false)
    kotlin("android").version(kotlinVersion).apply(false)
    kotlin("multiplatform").version(kotlinVersion).apply(false)
    id("org.jetbrains.compose").version("1.3.1").apply(false)
    id("org.jetbrains.kotlin.jvm") version kotlinVersion apply false

}

tasks.register("cleanAll", Delete::class) {
    delete(rootProject.buildDir)
}
