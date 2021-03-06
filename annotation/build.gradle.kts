plugins {
  id("groovy")
  id("java-gradle-plugin")
}

gradlePlugin {
  plugins {
    create("apsarasannotation"){
      id = "org.apsarasmc.apsaras.annotation"
      implementationClass = "org.apsarasmc.annotation.ApplyPlugin"
    }
  }
}

dependencies {
  implementation(project(":core"))
  implementation("com.google.code.gson:gson:2.8.8")
}