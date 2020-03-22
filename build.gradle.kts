import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.3.70"
}

group = "lastexceed"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib-jdk8"))
	setOf(
		"cio",
		"json-jvm",
		"gson",
		"jackson",
		"serialization-jvm"
	).forEach {
		implementation("io.ktor", "ktor-client-$it", "1.3.1")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "13"
}