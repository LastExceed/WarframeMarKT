import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.3.71"
	application
	`maven-publish`
	signing
}

group = "com.github.lastexceed"
version = "0.0.1-SNAPSHOT"

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

application {
	mainClassName = "MainKt"
}

val sourcesJar = tasks.register<Jar>("sourcesJar") {
	archiveClassifier.set("sources")
	from(sourceSets.main.get().allSource)
}

publishing {
	publications {
		create<MavenPublication>("WarframeMarKT") {
			pom {
				name.set("$group:$name")
				description.set("Kotlin wrapper for api.warframe.market")
				url.set("https://github.com/LastExceed/WarframeMarKT")

//				organization {
//					name.set("com.github.lastexceed")
//					url.set("https://github.com/LastExceed")
//				}
//				licenses {
//					license {
//						name.set("Apache License 2.0")
//						url.set("https://github.com/doyaaaaaken/kotlin-csv/blob/master/LICENSE")
//					}
//				}
//				developers {
//					developer {
//						name.set("LastExceed")
//					}
//				}
				scm {
					val gitHubUrl = "github.com/LastExceed/WarframeMarKT"
					url.set("https://$gitHubUrl")
					connection.set("scm:git:git://$gitHubUrl.git")
					developerConnection.set("https://$gitHubUrl")
				}
			}
			//from(components["java"])
			//artifact(sourcesJar.get())
		}

		repositories {
			maven {
				credentials {
					val creds = File("nexus_credentials").readLines()
					username = creds[0]
					password = creds[1]
				}
				url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
			}
		}
	}
	//
}

signing {
	sign(publishing.publications)
//	setRequired {
//		(rootProject.extra["isReleaseVersion"] as Boolean) && gradle.taskGraph.hasTask("publish")
//	}
//	val signingKey: String? = System.getenv("GPG_SECRET")
//	val signingPassword: String? = System.getenv("GPG_PASSPHRASE")
//	useInMemoryPgpKeys(signingKey, signingPassword)
//	sign(publishing.publications["mavenJava"])
}