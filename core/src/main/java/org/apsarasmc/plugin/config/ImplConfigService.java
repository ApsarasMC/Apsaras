package org.apsarasmc.plugin.config;

import org.apsarasmc.apsaras.config.ConfigService;
import org.apsarasmc.apsaras.plugin.PluginContainer;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import org.yaml.snakeyaml.resolver.Resolver;

import javax.inject.Singleton;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

public class ImplConfigService< T > implements ConfigService< T > {
  private final Path path;
  private final Class< T > configClass;
  private final Yaml yaml;

  public ImplConfigService(final Path path, final Class< T > configClass) {
    this.path = path;
    this.configClass = configClass;

    DumperOptions dumperOptions = new DumperOptions();
    dumperOptions.setProcessComments(true);
    dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.AUTO);

    yaml = new Yaml(
      new CustomClassLoaderConstructor(configClass, configClass.getClassLoader()),
      new ApsarasRepresenter(),
      dumperOptions,
      new LoaderOptions(),
      new Resolver()
    );
  }


  @Override
  public T load() throws Exception {
    if (!path.toFile().exists()) {
      save(configClass.getConstructor().newInstance());
    }
    return yaml.load(new FileReader(path.toFile()));
  }

  @Override
  public void save(final T object) throws Exception {
    try (FileWriter fileWriter = new FileWriter(path.toFile())) {
      yaml.dump(object, fileWriter);
    }
  }

  @Singleton
  public static class Factory implements ConfigService.Factory {
    @Override
    public < T > ConfigService< T > of(final PluginContainer pluginContainer, final String name, final Class< T > configClass) {
      return new ImplConfigService<>(pluginContainer.configPath().resolve(name + ".yml"), configClass);
    }
  }
}
