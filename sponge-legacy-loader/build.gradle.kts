import java.net.URI
import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.PluginDependency

plugins {
  id("org.spongepowered.gradle.plugin").version("1.1.1")
  id("com.github.johnrengelman.shadow").version("7.0.0")
}

sponge {
  apiVersion("7.3.0")
  plugin("apsaras") {
    loader(PluginLoaders.JAVA_PLAIN)
    displayName("Apsaras")
    mainClass("org.apsarasmc.loader.sponge.SpongeLaunchWrapper")
    description("Provide apsaras api for sponge")
    links {
      homepage("https://github.com/ApsarasMC")
      source("https://github.com/ApsarasMC/ApsarasPlugin")
      issues("https://github.com/ApsarasMC/Apsaras/issues")
    }
    dependency("spongeapi") {
      loadOrder(PluginDependency.LoadOrder.AFTER)
      optional(false)
    }
  }
}

dependencies {
  runtimeOnly(project(":sponge-legacy"))
}

tasks.shadowJar {
  archiveBaseName.set("Apsaras")
  archiveClassifier.set("sponge-leacy")

  dependencies {
    exclude(dependency("com.google.code.findbugs:jsr305:"))
    exclude(dependency("com.google.inject:guice:"))
    exclude(dependency("javax.inject:javax.inject:"))
    exclude(dependency("aopalliance:aopalliance:"))
    exclude(dependency("com.google.code.gson:gson:"))

    exclude(dependency("com.google.guava:guava:"))
    exclude(dependency("com.google.errorprone:error_prone_annotations:"))
    exclude(dependency("com.google.guava:failureaccess:"))
    exclude(dependency("com.google.guava:listenablefuture:"))
    exclude(dependency("com.google.j2objc:j2objc-annotations:"))

    exclude(dependency("org.slf4j:slf4j-api:"))
    exclude(dependency("javax.annotation:javax.annotation-api:"))
  }

  relocate("org.sonatype.inject", "org.apsarasmc.libs.sonatype.inject")
  relocate("org.eclipse.sisu", "org.apsarasmc.libs.eclipse.sisu")
  relocate("org.eclipse.aether", "org.apsarasmc.libs.eclipse.aether")
  relocate("org.codehaus.plexus", "org.apsarasmc.libs.codehaus.plexus")
  relocate("org.apache.maven", "org.apsarasmc.libs.apache.maven")
  relocate("org.apache.http", "org.apsarasmc.libs.apache.http")
  relocate("org.apache.commons", "org.apsarasmc.libs.apache.commons")
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