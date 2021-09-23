import java.net.URI

repositories {
  maven {
    name = "sonatype-oss-snapshots"
    url = URI("https://oss.sonatype.org/content/repositories/snapshots/")
  }
  maven {
    name = "spigotmc-repo"
    url = URI("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
  }
}

dependencies {
  implementation(project(":core"))
  implementation(project(":apsaras"))
  implementation("net.kyori:adventure-platform-bukkit:4.0.0")
  implementation("net.kyori:adventure-platform-bungeecord:4.0.0")
  implementation("org.slf4j:slf4j-jcl:1.7.32")

  compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT") {
    exclude(
      mapOf(
        Pair("group", "com.google.guava"),
        Pair("module", "guava"),
      )
    )
    exclude(
      mapOf(
        Pair("group", "org.checkerframework"),
        Pair("module", "checker-qual"),
      )
    )
    exclude(
      mapOf(
        Pair("group", "net.md-5:bungeecord-chat"),
        Pair("module", "1.16-R0.4"),
      )
    )
    exclude(
      mapOf(
        Pair("group", "org.yaml"),
        Pair("module", "snakeyaml"),
      )
    )
  }
}