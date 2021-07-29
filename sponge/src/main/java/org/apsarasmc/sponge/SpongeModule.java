package org.apsarasmc.sponge;

import com.google.inject.Binder;
import com.google.inject.Module;
import org.apsarasmc.sponge.util.EventUtil;

public class SpongeModule implements Module {
    private final Module applyModule;

    public SpongeModule(Module module) {
        this.applyModule = module;
    }

    @Override
    public void configure(Binder binder) {
        applyModule.configure(binder);
        binder.requestStaticInjection(EventUtil.class);
    }
}
