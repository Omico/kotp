@file:Suppress("UnstableApiUsage")

import me.omico.gradm.configs
import me.omico.gradm.gradm

rootProject.name = "kotp"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots")
    }
    val versions = object {
        val agePlugin = "1.0.0-SNAPSHOT"
        val gradleEnterprisePlugin = "3.8.1"
        val gradleVersionsPlugin = "0.42.0"
        val gradmPlugin = "2.0.0"
        val kotlinPlugin = "1.6.21"
        val spotlessPlugin = "6.6.1"
    }
    plugins {
        id("com.diffplug.spotless") version versions.spotlessPlugin
        id("com.github.ben-manes.versions") version versions.gradleVersionsPlugin
        id("com.gradle.enterprise") version versions.gradleEnterprisePlugin
        id("me.omico.age.project") version versions.agePlugin
        id("me.omico.age.settings") version versions.agePlugin
        id("me.omico.age.spotless") version versions.agePlugin
        id("me.omico.gradm") version versions.gradmPlugin
        kotlin("multiplatform") version versions.kotlinPlugin
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("com.gradle.enterprise")
    id("me.omico.gradm")
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlwaysIf(!gradle.startParameter.isOffline)
    }
}

gradm {
    configs {
        format = true
    }
}
