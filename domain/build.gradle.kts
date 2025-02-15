plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    // paging-common
    implementation(libs.androidx.paging3.common)

    // javax-inject
    implementation(libs.javax.inject)
}