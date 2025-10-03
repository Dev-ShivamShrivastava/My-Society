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

    implementation(libs.retrofit)

    implementation(libs.javax.inject)

    implementation("com.google.code.gson:gson:2.10.1")

}
