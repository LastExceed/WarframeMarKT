import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.3.71"
}

group = "com.github.lastexceed"
version = "1.0.0"

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