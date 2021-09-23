import java.net.URI

repositories {
  maven {
    name = "sponge-repo"
    url = URI("https://repo.spongepowered.org/maven")
  }
}

dependencies {
  implementation(project(":core"))
  implementation(project(":apsaras"))

  compileOnly("org.spongepowered:spongeapi:8.0.0-SNAPSHOT")
}