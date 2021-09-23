import java.net.URI
plugins {
  `maven-publish`
}

group = "org.apsarasmc.apsaras"
version = "1.2-SNAPSHOT"

subprojects {
  apply(plugin = "java")
  apply(plugin = "maven-publish")

  group = rootProject.group
  version = rootProject.version

  repositories {
    mavenCentral()
    mavenLocal()
  }

  publishing {
    publications {
      create<MavenPublication>("maven"){
        groupId = project.group.toString()
        artifactId = project.name
        version = project.version.toString()
        from(components["java"])
      }
    }
    repositories {
      mavenLocal()
      maven {
        url = URI("https://apsarasmc-maven.pkg.coding.net/repository/apsarasmc/apsaras/")
        credentials {
          username = properties["artifactsGradleUsername"] as String
          password = properties["artifactsGradlePassword"] as String
        }
      }
    }
  }
}