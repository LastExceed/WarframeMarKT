import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.3.71"
	application
	`maven-publish`
	signing
}

val author = "LastExceed"
val projectName = "WarframeMarKT"
val urlGitHubUser = "github.com/$author/"
val urlGitHubRepo = "$urlGitHubUser/$projectName"

group = "com.github.lastexceed"
version = "1.1.1"

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
val javadocJar = tasks.register<Jar>("javadocJar") {
	dependsOn("javadoc")
	archiveClassifier.set("javadoc")
	from(tasks.javadoc.get().destinationDir)
}

publishing {
	publications {
		create<MavenPublication>(projectName) {
			from(components["java"])
			artifact(sourcesJar.get())
			artifact(javadocJar.get())
			pom {
				name.set("WarframeMarKT")
				description.set("Kotlin wrapper for api.warframe.market")
				url.set("https://$urlGitHubRepo")

				organization {
					name.set(group as String)
					url.set(urlGitHubUser)
				}
				licenses {
					license {
						name.set("MIT license")
						url.set("https://$urlGitHubRepo/blob/master/LICENSE")
					}
				}
				developers {
					developer {
						name.set(author)
					}
				}
				scm {
					url.set("https://$urlGitHubRepo")
					connection.set("scm:git:git://$urlGitHubRepo.git")
					developerConnection.set("https://$urlGitHubRepo")
				}
			}
		}

		repositories {
			maven {
				credentials {
					val sonatypeUsername: String by project
					val sonatypePassword: String by project
					username = sonatypeUsername
					password = sonatypePassword
				}
				val releasesRepoUrl = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
				val snapshotsRepoUrl = uri("https://oss.sonatype.org/content/repositories/snapshots/")
				url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
			}
		}
	}
}

signing {
	useGpgCmd()
	sign(publishing.publications[projectName])
}

tasks["publish${projectName}PublicationToMavenRepository"].dependsOn(tasks["sign${projectName}Publication"])