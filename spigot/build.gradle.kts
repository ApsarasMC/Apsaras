import java.net.URI

repositories {
  maven {
    name = "sonatype-oss-snapshots"
    url = URI("https://oss.sonatype.org/content/repositories/snapshots/")
  }
  maven {
    name = "extendedclip"
    url = URI("https://repo.extendedclip.com/content/repositories/placeholderapi/")
  }
  maven {
    name = "papermc-repo"
    url = URI("https://papermc.io/repo/repository/maven-public/")
  }
}

dependencies {
  implementation(project(":core"))
  implementation(project(":apsaras"))
  implementation("net.kyori:adventure-platform-bukkit:4.0.0")
  implementation("net.kyori:adventure-platform-bungeecord:4.0.0")
  implementation("org.slf4j:slf4j-jcl:1.7.32")

  compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT"){
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

  compileOnly("me.clip:placeholderapi:2.10.9") {
    exclude(
      mapOf(
        Pair("group", "org.bstats"),
        Pair("module", "bstats-bukkit"),
      )
    )
  }
}