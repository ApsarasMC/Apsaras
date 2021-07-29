package org.apsarasmc.plugin.setting;

import org.apsarasmc.apsaras.Server;
import org.apsarasmc.apsaras.config.ConfigService;
import org.apsarasmc.plugin.ApsarasPluginContainer;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;


@Singleton
public class ImplSettingProvider implements Provider<ApsarasSetting> {
    private final ConfigService<ApsarasSetting> configService;
    private ApsarasSetting setting;

    @Inject
    public ImplSettingProvider(final ApsarasPluginContainer pluginContainer, final ConfigService.Factory configServiceFactory, final Server server) throws Exception {
        configService = configServiceFactory.of(pluginContainer, "config", ApsarasSetting.class);
        this.setting = configService.load();
    }

    @Override
    public ApsarasSetting get() {
        return this.setting;
    }
}