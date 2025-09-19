plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "1.2.1"
}

group = "io.github.rndevelo.buildlogic"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
    website.set("https://github.com/rndevelo/android-build-logic")
    vcsUrl.set("https://github.com/rndevelo/android-build-logic")

    plugins {
        register("androidApplication") {
            id = "io.github.rndevelo.buildlogic.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
            displayName = "Android Application Convention Plugin by rndev"
            description = "Sets up a typical Android application module with common configurations."
            tags.set(listOf("android", "application", "convention"))
        }
        register("androidApplicationCompose") {
            id = "io.github.rndevelo.buildlogic.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
            displayName = "Android Application Compose Convention Plugin by rndev"
            description = "Sets up an Android application module with Jetpack Compose."
            tags.set(listOf("android", "compose", "application", "convention"))
        }
        register("androidLibrary") {
            id = "io.github.rndevelo.buildlogic.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
            displayName = "Android Library Convention Plugin by rndev"
            description = "Sets up an Android library module."
            tags.set(listOf("android", "library", "convention"))
        }
        register("androidLibraryCompose") {
            id = "io.github.rndevelo.buildlogic.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
            displayName = "Android Library Compose Convention Plugin by rndev"
            description = "Sets up an Android library module with Jetpack Compose."
            tags.set(listOf("android", "library", "compose", "convention"))
        }
        register("jvmLibrary") {
            id = "io.github.rndevelo.buildlogic.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
            displayName = "JVM Library Convention Plugin by rndev"
            description = "Configures a JVM (Kotlin/Java) library module."
            tags.set(listOf("kotlin", "jvm", "library", "convention"))
        }
    }
}

//tasks.test {
//    useJUnitPlatform()
//}
//
//// Validación antes de publicar
//tasks.register("validatePublication") {
//    doLast {
//        require(project.version.toString() != "unspecified") {
//            "Project version must be specified"
//        }
//        require(gradlePlugin.website.get().isNotBlank()) {
//            "Plugin website must be specified"
//        }
//        require(gradlePlugin.vcsUrl.get().isNotBlank()) {
//            "Plugin VCS URL must be specified"
//        }
//    }
//}
//
//tasks.publishPlugins {
//    dependsOn("validatePublication")
//}
