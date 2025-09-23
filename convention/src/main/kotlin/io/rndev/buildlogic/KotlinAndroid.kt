package io.rndev.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = 36

        defaultConfig {
            minSdk = 24
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }

    // Use withType to workaround https://youtrack.jetbrains.com/issue/KT-55947
    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    dependencies {
        add("implementation", libs.findLibrary("androidx.core.ktx").get())
        add("implementation", libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
    }

    addUnitTestDependencies()
}

/**
 * Configure base Kotlin options for JVM (non-Android)
 */
internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension>("java") {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

        toolchain {
            languageVersion.set(JavaLanguageVersion.of(11))
        }
    }

    extensions.configure<KotlinJvmProjectExtension>("kotlin") {
        // Configura el toolchain para Kotlin para que coincida
        jvmToolchain {
            this.languageVersion.set(JavaLanguageVersion.of(11))
        }
        // Si el anterior no funciona por alguna razón (versión de KGP, etc.), puedes usar la opción más directa,
        // pero el toolchain es preferible:
        // jvmToolchain(11) // Para KGP 1.7.0+
        // O la forma más antigua si es necesario:
        // kotlinOptions.jvmTarget = "11"
    }

    dependencies {
        add("implementation", libs.findLibrary("kotlinx.coroutines.core").get())
    }

    addUnitTestDependencies()
}
