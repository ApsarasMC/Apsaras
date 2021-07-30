package org.apsarasmc.plugin.dependency;

import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.apsarasmc.apsaras.Apsaras;
import org.apsarasmc.plugin.ApsarasPluginContainer;
import org.apsarasmc.plugin.setting.ApsarasSetting;
import org.eclipse.aether.AbstractRepositoryListener;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositoryEvent;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transfer.AbstractTransferListener;
import org.eclipse.aether.transfer.TransferEvent;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Singleton
public class DependencyResolver {
  private static final Logger logger = Apsaras.server().logger();

  private final RepositorySystem repositorySystem;
  private final DefaultRepositorySystemSession repositorySystemSession;
  private final List< RemoteRepository > remoteRepositories = new LinkedList<>();

  @Inject
  public DependencyResolver(ApsarasPluginContainer pluginContainer, ApsarasSetting setting) throws IOException {
    Path dependencyPath = pluginContainer.dataPath().resolve("dependency");
    Files.createDirectories(dependencyPath);

    DefaultServiceLocator locator = MavenRepositorySystemUtils.newServiceLocator();
    locator.addService(RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class);
    locator.addService(TransporterFactory.class, HttpTransporterFactory.class);

    locator.setErrorHandler(new DefaultServiceLocator.ErrorHandler() {
      @Override
      public void serviceCreationFailed(Class< ? > type, Class< ? > impl, Throwable exception) {
        logger.warn(String.format("Service creation failed for %s with implementation %s", type, impl), new Exception(exception));
      }
    });

    repositorySystem = locator.getService(RepositorySystem.class);

    repositorySystemSession = MavenRepositorySystemUtils.newSession();
    repositorySystemSession.setTransferListener(new ConsoleTransferListener());
    repositorySystemSession.setRepositoryListener(new ConsoleRepositoryListener());

    LocalRepository localRepo = new LocalRepository(dependencyPath.toFile());
    repositorySystemSession.setLocalRepositoryManager(repositorySystem.newLocalRepositoryManager(repositorySystemSession, localRepo));
    repositorySystemSession.setSystemProperties(System.getProperties());
    repositorySystemSession.setConfigProperties(System.getProperties());
    repositorySystemSession.setSystemProperty("os.detected.name", System.getProperty("os.name", "unknown").toLowerCase());
    repositorySystemSession.setSystemProperty("os.detected.arch", System.getProperty("os.arch", "unknown").replace("amd64", "x86_64"));
    for (String repo : setting.mavenRepo) {
      remoteRepositories.add(new RemoteRepository.Builder(null, "default", repo).build());
    }
  }

  public File getDependencyFile(String packageName) throws ArtifactResolutionException {
    String[] splits = packageName.split(":");

    String group = splits[ 0 ];
    String name = splits[ 1 ];
    String version = splits[ 2 ];

    Artifact artifact = new DefaultArtifact(group, name, "jar", version);
    ArtifactRequest artifactRequest = new ArtifactRequest();
    artifactRequest.setArtifact(artifact);
    artifactRequest.setRepositories(remoteRepositories);

    ArtifactResult artifactResult;
    artifactResult = repositorySystem.resolveArtifact(repositorySystemSession, artifactRequest);

    if (artifactResult.isResolved()) {
      return artifactResult.getArtifact().getFile();
    } else {
      throw new ArtifactResolutionException(Collections.singletonList(artifactResult));
    }
  }

  public static class ConsoleRepositoryListener extends AbstractRepositoryListener {
    @Override
    public void artifactDeployed(RepositoryEvent event) {
      //
    }

    @Override
    public void artifactDeploying(RepositoryEvent event) {
      //
    }

    @Override
    public void artifactDescriptorInvalid(RepositoryEvent event) {
      logger.warn("Invalid artifact descriptor for {}: {}.", event.getArtifact(), event.getException().getMessage());
    }

    @Override
    public void artifactDescriptorMissing(RepositoryEvent event) {
      logger.warn("Missing artifact descriptor for {}.", event.getArtifact());
    }

    @Override
    public void artifactInstalled(RepositoryEvent event) {
      //
    }

    @Override
    public void artifactInstalling(RepositoryEvent event) {
      //
    }

    @Override
    public void artifactResolved(RepositoryEvent event) {
      //
    }

    @Override
    public void artifactDownloading(RepositoryEvent event) {
      //
    }

    @Override
    public void artifactDownloaded(RepositoryEvent event) {
      //
    }

    @Override
    public void artifactResolving(RepositoryEvent event) {
      //
    }

    @Override
    public void metadataDeployed(RepositoryEvent event) {
      //
    }

    @Override
    public void metadataDeploying(RepositoryEvent event) {
      //
    }

    @Override
    public void metadataInstalled(RepositoryEvent event) {
      //
    }

    @Override
    public void metadataInstalling(RepositoryEvent event) {
      //
    }

    @Override
    public void metadataInvalid(RepositoryEvent event) {
      logger.warn("Invalid metadata: {}.", event.getMetadata());
    }

    @Override
    public void metadataResolved(RepositoryEvent event) {
      //
    }

    @Override
    public void metadataResolving(RepositoryEvent event) {
      //
    }
  }

  public static class ConsoleTransferListener extends AbstractTransferListener {
    private long time = System.currentTimeMillis();

    @Override
    public void transferInitiated(TransferEvent event) {
      //
    }

    @Override
    public void transferProgressed(TransferEvent event) {
      long now = System.currentTimeMillis();
      if (time + 1000 < now) {
        time = now;
        logger.info("Downloading: {} {} ({}%).",
          event.getResource().getRepositoryUrl(),
          event.getResource().getResourceName(),
          (int) (event.getTransferredBytes() * 100 / event.getResource().getContentLength())
        );
      }
    }

    @Override
    public void transferSucceeded(TransferEvent event) {
      //
    }

    @Override
    public void transferFailed(TransferEvent event) {
      //
    }
  }
}