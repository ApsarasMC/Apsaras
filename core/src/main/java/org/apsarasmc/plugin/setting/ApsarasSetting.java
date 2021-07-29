package org.apsarasmc.plugin.setting;

import org.apsarasmc.apsaras.config.Comment;

public class ApsarasSetting {
    @Comment("Should print apsaras banner when apsaras start up?")
    public boolean banner = true;

    @Comment("Maven Repo")
    @Comment("In China, you should use \"https://maven.aliyun.com/repository/central\".")
    public String[] mavenRepo = new String[]{"https://repo1.maven.org/maven2/"};
}
