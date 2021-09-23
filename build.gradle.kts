import java.net.URI

group = "org.apsarasmc.apsaras"
version = "1.2-SNAPSHOT"

subprojects {
  apply {
    plugin("java")
    plugin("java-library")
  }

  group = rootProject.group
  version = rootProject.version

  repositories {
    mavenCentral()
    mavenLocal()
  }

  repositories {
    mavenLocal()
    maven {
      url = URI("https://apsarasmc-maven.pkg.coding.net/repository/apsarasmc/apsaras/")
      credentials {
        username = properties.getOrDefault("artifactsGradleUsername","") as String
        password = properties.getOrDefault("artifactsGradlePassword","") as String
      }
    }
  }
}