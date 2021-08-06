package org.apsarasmc.plugin.setting;

import org.apsarasmc.apsaras.config.Comment;

public class ApsarasSetting {
  @Comment ("Should print apsaras banner when apsaras start up?")
  public boolean banner = true;

  @Comment ("Enable handle ChatEvent's ChatFormatter by Apsaras (Spigot only)")
  public boolean enableHandleChatFormatter = true;

  @Comment ("Use legacy message format")
  public boolean messageLegacy = false;

  @Comment ("Maven Repo")
  @Comment ("In China, you should use \"https://maven.aliyun.com/repository/central/\".")
  public String[] mavenRepo = new String[] {
    "https://repo1.maven.org/maven2/",
    "https://www.jitpack.io/"
  };
}
