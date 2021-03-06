import java.net.URI

plugins {
  id("com.github.johnrengelman.shadow").version("7.0.0")
}

repositories {
  maven {
    name = "sonatype-oss-snapshots"
    url = URI("https://oss.sonatype.org/content/repositories/snapshots/")
  }
  maven {
    name = "spigotmc-repo"
    url = URI("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
  }
  maven {
    name = "extendedclip"
    url = URI("https://repo.extendedclip.com/content/repositories/placeholderapi/")
  }
}

dependencies {
  runtimeOnly(project(":spigot"))
  compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
}

tasks.shadowJar {
  archiveBaseName.set("Apsaras")
  archiveClassifier.set("spigot")

  dependencies {
   exclude(dependency("com.google.code.findbugs:jsr305:"))

   exclude(dependency("com.google.code.gson:gson:"))

   exclude(dependency("com.google.guava:guava:"))
   exclude(dependency("com.google.errorprone:error_prone_annotations:"))
   exclude(dependency("com.google.guava:failureaccess:"))
   exclude(dependency("com.google.guava:listenablefuture:"))
   exclude(dependency("com.google.j2objc:j2objc-annotations:"))

   exclude(dependency("javax.annotation:javax.annotation-api:"))
   exclude(dependency("org.slf4j:jcl-over-slf4j"))
  }

  relocate("org.sonatype.inject", "org.apsarasmc.libs.sonatype.inject")
  relocate("org.eclipse.sisu", "org.apsarasmc.libs.eclipse.sisu")
  relocate("org.eclipse.aether", "org.apsarasmc.libs.eclipse.aether")
  relocate("org.codehaus.plexus", "org.apsarasmc.libs.codehaus.plexus")
  relocate("org.apache.maven", "org.apsarasmc.libs.apache.maven")
  relocate("org.apache.http", "org.apsarasmc.libs.apache.http")
  relocate("org.apache.commons", "org.apsarasmc.libs.apache.commons")
  relocate("org.aopalliance", "org.apsarasmc.libs.aopalliance")
  relocate("com.google.inject", "org.apsarasmc.libs.guice")
  relocate("org.yaml.snakeyaml", "org.apsarasmc.libs.snakeyaml")
  relocate("org.objectweb.asm", "org.apsarasmc.libs.asm")

  relocate("com.zaxxer.hikari", "org.apsarasmc.libs.hikari")
  relocate("com.mchange", "org.apsarasmc.libs.mchange")
  relocate("org.quartz", "org.apsarasmc.libs.quartz")
  relocate("org.terracotta", "org.apsarasmc.libs.terracotta")

  exclude("about.html")
  exclude("plugin.xml")
  exclude("mchange-config-resource-paths.txt")
  exclude("checkstyle.xml")
  exclude("licenses/**")
  exclude("mozilla/**")
  exclude("META-INF/maven/**")
  exclude("META-INF/services/**")
  exclude("META-INF/sisu/**")
  exclude("META-INF/DEPENDENCIES")
  exclude("META-INF/LICENSE")
  exclude("META-INF/NOTICE")
  exclude("META-INF/LICENSE.txt")
  exclude("META-INF/NOTICE.txt")
}

tasks.assemble {
  dependsOn(tasks.shadowJar)
}