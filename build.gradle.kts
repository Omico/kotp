import me.omico.age.dsl.withKotlinMavenPublication
import me.omico.age.spotless.configureSpotless
import me.omico.age.spotless.intelliJIDEARunConfiguration

plugins {
    id("com.diffplug.spotless")
    id("com.github.ben-manes.versions")
    id("me.omico.age.project")
    id("me.omico.age.spotless")
    kotlin("multiplatform")
}

group = "me.omico.kotp"
version = "0.1.0-SNAPSHOT"

withKotlinMavenPublication()

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        commonMain {
            dependencies {
                implementation(kotlinx.datetime)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        named("jvmMain") {
            dependencies {
                implementation(bouncyCastle.provider.jdk18on)
            }
        }
    }
}

apply(plugin = "com.github.ben-manes.versions")

configureSpotless {
    intelliJIDEARunConfiguration()
    kotlin {
        target("src/**/*.kt")
        ktlint(versions.ktlint)
        indentWithSpaces()
        trimTrailingWhitespace()
        endWithNewline()
        licenseHeaderFile(rootProject.file("spotless/copyright.kt")).updateYearWithLatest(true).yearSeparator("-")
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        targetExclude(".gradm/**/*.gradle.kts")
        ktlint(versions.ktlint)
        indentWithSpaces()
        trimTrailingWhitespace()
        endWithNewline()
    }
}

val wrapper: Wrapper by tasks.named<Wrapper>("wrapper") {
    gradleVersion = versions.gradle
    distributionType = Wrapper.DistributionType.BIN
}
