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

  implementation("net.kyori:adventure-platform-spongeapi:4.0.0")
  compileOnly("org.spongepowered:spongeapi:7.3.0")
}