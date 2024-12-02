plugins {
  alias(libs.plugins.kotlinJvm)
  alias(libs.plugins.spotless)
}

group = "earth.adi"

version = "1.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies {
  testImplementation(platform(libs.junitBom))
  testImplementation(libs.bundles.test)
}

tasks.test { useJUnitPlatform() }

kotlin { jvmToolchain(17) }

tasks.withType<JavaCompile> { dependsOn(tasks.spotlessApply) }

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
  kotlin {
    ktfmt()
    trimTrailingWhitespace()
    endWithNewline()
    licenseHeader("")
    toggleOffOn()
  }
  kotlinGradle {
    target("*.gradle.kts")
    ktfmt()
    trimTrailingWhitespace()
    endWithNewline()
  }
}
