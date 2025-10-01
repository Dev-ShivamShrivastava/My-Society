plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    api(project(":domain"))
    implementation(libs.kotlinx.coroutines.android)

    implementation(platform("com.squareup.okhttp3:okhttp-bom:5.1.0"))
    implementation("com.squareup.okhttp3:logging-interceptor")
}
