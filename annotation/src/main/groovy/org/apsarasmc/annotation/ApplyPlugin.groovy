package org.apsarasmc.annotation

import org.gradle.api.Plugin
import org.gradle.api.Project

class ApplyPlugin implements Plugin<Project> {
  @Override
  void apply(final Project target) {
    target.dependencies {
      annotationProcessor "org.apsarasmc.apsaras:annotation:1.1-SNAPSHOT"
      compileOnly "org.apsarasmc.apsaras:apsaras:1.1-SNAPSHOT"
    }
  }
}
