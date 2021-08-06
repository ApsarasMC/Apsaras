package org.apsarasmc.annotation

import org.gradle.api.Plugin
import org.gradle.api.Project

class ApplyPlugin implements Plugin<Project> {
  @Override
  void apply(final Project target) {
    target.dependencies.add('annotationProcessor', 'org.apsarasmc.apsaras:annotation:1.1-SNAPSHOT')
    target.dependencies.add('compileOnly', 'org.apsarasmc.apsaras:apsaras:1.1-SNAPSHOT')
    target.repositories {
      maven {
        url 'https://apsarasmc.coding.net/public-artifacts/apsarasmc/apsaras/packages'
      }
    }
  }
}
