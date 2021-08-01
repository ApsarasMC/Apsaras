package org.apsarasmc.testplugin;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.apsarasmc.apsaras.aop.ApsarasPlugin;
import org.apsarasmc.apsaras.plugin.PluginDepend;

@ApsarasPlugin (
  name = "test-plugin",
  dependency = {
    @ApsarasPlugin.Dependency (
      type = PluginDepend.Type.SOFT,
      name = "a-plugin-never-exist"
    ),
    @ApsarasPlugin.Dependency (
      type = PluginDepend.Type.LIBRARY,
      name = "com.google.code.gson:gson:2.8.7"
    ),
  }
)
public class TestModule implements Module {
  @Override
  public void configure(Binder binder) {
    //
  }
}
