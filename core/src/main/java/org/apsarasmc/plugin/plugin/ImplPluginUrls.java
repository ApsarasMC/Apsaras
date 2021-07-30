package org.apsarasmc.plugin.plugin;

import org.apsarasmc.apsaras.plugin.PluginUrls;

import javax.annotation.Nullable;

public class ImplPluginUrls implements PluginUrls {
  private final @Nullable
  String home;
  private final @Nullable
  String issue;

  private ImplPluginUrls(final @Nullable String home,
                         final @Nullable String issue) {
    this.home = home;
    this.issue = issue;
  }

  public static ImplPluginUrls clone(PluginUrls urls) {
    if (urls instanceof ImplPluginUrls) {
      return (ImplPluginUrls) urls;
    }
    return new ImplPluginUrls(urls.home(), urls.issue());
  }

  @Override
  public @Nullable
  String home() {
    return this.home;
  }

  @Override
  public @Nullable
  String issue() {
    return this.issue;
  }

  public static final class Builder implements PluginUrls.Builder {
    private String home;
    private String issue;

    @Override
    public PluginUrls.Builder home(String home) {
      this.home = home;
      return this;
    }

    @Override
    public PluginUrls.Builder issue(String issue) {
      this.issue = issue;
      return this;
    }

    @Override
    public PluginUrls build() {
      return new ImplPluginUrls(home, issue);
    }
  }
}
