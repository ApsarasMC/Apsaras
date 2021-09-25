plugins {
  id("java-library")
  kotlin("jvm") version "1.5.10"
}

dependencies {
  api(project(":apsaras"))

  api("com.google.inject:guice:5.0.1") {
    exclude(mapOf(
      Pair("group", "com.google.guava"),
      Pair("module", "guava"),
    ))
  }
  api("com.google.guava:guava:30.1.1-jre") {
    exclude(mapOf(
      Pair("group", "org.checkerframework"),
      Pair("module", "checker-qual"),
    ))
  }
  api("com.google.code.gson:gson:2.8.8")
  api("org.yaml:snakeyaml:1.29")
  api("org.openjdk.nashorn:nashorn-core:15.3")

  compileOnly("com.google.code.findbugs:jsr305:3.0.2")

  implementation("org.apache.maven.resolver:maven-resolver-impl:1.7.2")
  implementation("org.apache.maven.resolver:maven-resolver-connector-basic:1.7.2")
  implementation("org.apache.maven.resolver:maven-resolver-transport-http:1.7.2")
  implementation("org.apache.maven:maven-resolver-provider:3.8.1")
  implementation("org.quartz-scheduler:quartz:2.3.2")

  implementation("org.ow2.asm:asm:9.2")
  implementation("org.ow2.asm:asm-commons:9.2")

  testImplementation("org.apache.logging.log4j:log4j-api:2.14.1")
  testImplementation("org.apache.logging.log4j:log4j-core:2.14.1")
  testImplementation("org.slf4j:slf4j-log4j12:1.7.32")
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.0")
  testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.0")

}
tasks.withType<Test>{
  useJUnitPlatform()
}