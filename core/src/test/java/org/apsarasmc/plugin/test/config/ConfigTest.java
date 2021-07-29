package org.apsarasmc.plugin.test.config;

import org.apsarasmc.apsaras.config.ConfigService;
import org.apsarasmc.plugin.ApsarasPluginContainer;
import org.apsarasmc.plugin.test.InjectTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@InjectTest
public class ConfigTest {
    @Inject
    private ApsarasPluginContainer pluginContainer;

    @Test
    public void config() throws Exception {
        ConfigService<ConfigBean> configService = ConfigService.factory().of(pluginContainer, "test", ConfigBean.class);

        ConfigBean bean = new ConfigBean();
        configService.save(bean);

        bean.test.string = "testA";
        configService.save(bean);
        bean = configService.load();
        Assertions.assertEquals(bean.test.string, "testA");

        bean.test.string = "testB";
        configService.save(bean);
        bean = configService.load();
        Assertions.assertEquals(bean.test.string, "testB");
    }
}
