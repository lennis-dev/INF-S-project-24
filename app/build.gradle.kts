/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.7/userguide/building_java_projects.html in the Gradle documentation.
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
  // Apply the application plugin to add support for building a CLI application in Java.
  application
}

repositories {
  // Use Maven Central for resolving dependencies.
  mavenCentral()
  maven(url = "https://jitpack.io")
}

dependencies { implementation("org.xerial:sqlite-jdbc:3.46.0.0") }

// Apply a specific Java toolchain to ease working on different environments.
java { toolchain { languageVersion = JavaLanguageVersion.of(21) } }

application {
  // Define the main class for the application.
  mainClass.set("dev.lennis.school.notes.Main")
}
