plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "1.2.1"
    `maven-publish`
}

group = "io.github.rndevelo.buildlogic"
version = providers.gradleProperty("version").orElse("1.0.0-SNAPSHOT").get()

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
    withSourcesJar()
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
    website.set("https://github.com/rndevelo/build-logic")
    vcsUrl.set("https://github.com/rndevelo/build-logic")

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
        register("androidFeature") {
            id = "io.github.rndevelo.buildlogic.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
            displayName = "Android Feature Convention Plugin by rndev"
            description = "Sets up a feature module following Android best practices."
            tags.set(listOf("android", "feature", "convention", "module"))
        }
        register("diLibrary") {
            id = "io.github.rndevelo.buildlogic.di.library"
            implementationClass = "DiLibraryConventionPlugin"
            displayName = "DI Library Convention Plugin by rndev"
            description = "Configures a library module with Dependency Injection setup."
            tags.set(listOf("di", "library", "kotlin", "convention"))
        }
        register("diLibraryCompose") {
            id = "io.github.rndevelo.buildlogic.di.library.compose"
            implementationClass = "DiLibraryComposeConventionPlugin"
            displayName = "DI Library Compose Convention Plugin by rndev"
            description = "Configures a library module with DI setup and Jetpack Compose support."
            tags.set(listOf("di", "library", "compose", "kotlin", "convention"))
        }
        register("jvmLibrary") {
            id = "io.github.rndevelo.buildlogic.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
            displayName = "JVM Library Convention Plugin by rndev"
            description = "Configures a JVM (Kotlin/Java) library module."
            tags.set(listOf("kotlin", "jvm", "library", "convention"))
        }
        register("jvmRetrofit") {
            id = "io.github.rndevelo.buildlogic.jvm.retrofit"
            implementationClass = "JvmRetrofitConventionPlugin"
            displayName = "JVM Retrofit Convention Plugin by rndev"
            description = "Configures a JVM (Kotlin) library module with Retrofit client setup."
            tags.set(listOf("kotlin", "jvm", "library", "retrofit", "convention"))
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = project.group.toString()
            artifactId = "build-logic"
            version = project.version.toString()
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/rndevelo/build-logic")
            credentials {
                username = System.getenv("GPR_USER")
                password = System.getenv("GPR_KEY")
            }
        }
    }
}
