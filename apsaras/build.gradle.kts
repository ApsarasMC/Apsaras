plugins {
  id("java-library")
}

dependencies {
  compileOnly("com.google.code.findbugs:jsr305:3.0.2")

  api("javax.inject:javax.inject:1")
  api("org.slf4j:slf4j-api:1.7.32")
  api("net.kyori:adventure-api:4.9.0")
  api("net.kyori:adventure-text-serializer-plain:4.9.0")
  api("net.kyori:adventure-text-serializer-legacy:4.9.0")
}