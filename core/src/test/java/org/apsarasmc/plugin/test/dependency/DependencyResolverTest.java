package org.apsarasmc.plugin.test.dependency;

import org.apache.commons.codec.digest.DigestUtils;
import org.apsarasmc.plugin.dependency.DependencyResolver;
import org.apsarasmc.plugin.test.InjectTest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@InjectTest
class DependencyResolverTest {
  @Inject
  private DependencyResolver dependencyResolver;

  @Test
  void downloadGson() throws ArtifactResolutionException, IOException {
    File jarFile = dependencyResolver.getDependencyFile("com.google.code.gson:gson:2.8.7");
    InputStream jarInputStream = new FileInputStream(jarFile);
    String sha1Hex = DigestUtils.sha1Hex(jarInputStream);
    jarInputStream.close();
    Assertions.assertEquals("69d9503ea0a40ee16f0bcdac7e3eaf83d0fa914a", sha1Hex);
  }
}
