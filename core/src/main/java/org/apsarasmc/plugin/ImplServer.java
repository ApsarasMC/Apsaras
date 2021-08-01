package org.apsarasmc.plugin;

import org.apsarasmc.apsaras.Server;
import org.apsarasmc.plugin.util.relocate.RelocatingRemapper;

public interface ImplServer extends Server {
  RelocatingRemapper remapper();
}
