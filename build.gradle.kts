buildscript {
    repositories {
        google()
        jcenter()
        maven(url = "https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath(Libs.kotlin_gradle_plugin)
        classpath(Libs.com_jfrog_bintray_gradle_plugin)
        classpath(Libs.com_android_tools_build_gradle)
        classpath(Libs.binary_compatibility_validator)
    }
}

plugins {
    buildSrcVersions
    jacoco
    id("org.jlleitschuh.gradle.ktlint").version(Versions.org_jlleitschuh_gradle_ktlint_gradle_plugin)
}

apply(plugin = "binary-compatibility-validator")

allprojects {
    repositories {
        google()
        jcenter()
    }
}
