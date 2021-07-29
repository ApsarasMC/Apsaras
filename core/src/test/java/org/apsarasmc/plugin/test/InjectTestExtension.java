package org.apsarasmc.plugin.test;

import org.apache.log4j.BasicConfigurator;
import org.apsarasmc.apsaras.Apsaras;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstanceFactory;
import org.junit.jupiter.api.extension.TestInstanceFactoryContext;
import org.junit.jupiter.api.extension.TestInstantiationException;

public class InjectTestExtension implements TestInstanceFactory {
    static {
        BasicConfigurator.configure();
        new TestCore().init();
    }

    @Override
    public Object createTestInstance(final TestInstanceFactoryContext factoryContext, final ExtensionContext extensionContext) throws TestInstantiationException {
        return Apsaras.injector().getInstance(factoryContext.getTestClass());
    }
}
