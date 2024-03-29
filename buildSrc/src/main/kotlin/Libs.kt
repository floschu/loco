import kotlin.String

/**
 * Generated by https://github.com/jmfayard/buildSrcVersions
 *
 * Update this file with
 *   `$ ./gradlew buildSrcVersions`
 */
object Libs {
    /**
     * https://github.com/Kotlin/kotlinx.coroutines
     */
    const val kotlinx_coroutines_debug: String = "org.jetbrains.kotlinx:kotlinx-coroutines-debug:" +
            Versions.org_jetbrains_kotlinx_kotlinx_coroutines

    /**
     * https://developer.android.com/jetpack/androidx
     */
    const val lifecycle_runtime_ktx: String = "androidx.lifecycle:lifecycle-runtime-ktx:" +
            Versions.androidx_lifecycle

    const val lifecycle_runtime_testing: String = "androidx.lifecycle:lifecycle-runtime-testing:" +
            Versions.androidx_lifecycle

    /**
     * https://developer.android.com/testing
     */
    const val core_ktx: String = "androidx.test:core-ktx:" + Versions.androidx_test

    /**
     * https://developer.android.com/testing
     */
    const val androidx_test_rules: String = "androidx.test:rules:" + Versions.androidx_test

    /**
     * https://developer.android.com/testing
     */
    const val androidx_test_runner: String = "androidx.test:runner:" + Versions.androidx_test

    /**
     * https://developer.android.com/studio
     */
    const val com_android_tools_build_gradle: String = "com.android.tools.build:gradle:" +
            Versions.com_android_tools_build_gradle

    const val org_jlleitschuh_gradle_ktlint_gradle_plugin: String =
            "org.jlleitschuh.gradle.ktlint:org.jlleitschuh.gradle.ktlint.gradle.plugin:" +
            Versions.org_jlleitschuh_gradle_ktlint_gradle_plugin

    const val de_fayard_buildsrcversions_gradle_plugin: String =
            "de.fayard.buildSrcVersions:de.fayard.buildSrcVersions.gradle.plugin:" +
            Versions.de_fayard_buildsrcversions_gradle_plugin

    const val com_jfrog_bintray_gradle_plugin: String =
            "com.jfrog.bintray:com.jfrog.bintray.gradle.plugin:" +
            Versions.com_jfrog_bintray_gradle_plugin

    /**
     * https://github.com/Kotlin/binary-compatibility-validator
     */
    const val binary_compatibility_validator: String =
            "org.jetbrains.kotlinx:binary-compatibility-validator:" +
            Versions.binary_compatibility_validator

    /**
     * https://github.com/floschu/coroutines-test-extensions
     */
    const val coroutines_test_extensions: String =
            "at.florianschuster.test:coroutines-test-extensions:" +
            Versions.coroutines_test_extensions

    /**
     * https://kotlinlang.org/
     */
    const val kotlin_gradle_plugin: String = "org.jetbrains.kotlin:kotlin-gradle-plugin:" +
            Versions.kotlin_gradle_plugin

    /**
     * https://developer.android.com/jetpack/androidx
     */
    const val fragment_testing: String = "androidx.fragment:fragment-testing:" +
            Versions.fragment_testing

    /**
     * https://developer.android.com/testing
     */
    const val espresso_core: String = "androidx.test.espresso:espresso-core:" +
            Versions.espresso_core

    /**
     * https://developer.android.com/studio
     */
    const val lint_gradle: String = "com.android.tools.lint:lint-gradle:" + Versions.lint_gradle

    /**
     * https://developer.android.com/jetpack/androidx
     */
    const val appcompat: String = "androidx.appcompat:appcompat:" + Versions.appcompat

    /**
     * https://developer.android.com/testing
     */
    const val junit_ktx: String = "androidx.test.ext:junit-ktx:" + Versions.junit_ktx

    /**
     * https://github.com/pinterest/ktlint
     */
    const val ktlint: String = "com.pinterest:ktlint:" + Versions.ktlint

    /**
     * https://developer.android.com/studio
     */
    const val aapt2: String = "com.android.tools.build:aapt2:" + Versions.aapt2
}
