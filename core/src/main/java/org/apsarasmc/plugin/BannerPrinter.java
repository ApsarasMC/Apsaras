package org.apsarasmc.plugin;

import org.apsarasmc.apsaras.Server;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.PrintStream;

@Singleton
public class BannerPrinter {
  public static final String APSARAS_BANNER =
    "    /\\                                  \n" +
      "   /  \\   _ __  ___  __ _ _ __ __ _ ___ \n" +
      "  / /\\ \\ | '_ \\/ __|/ _` | '__/ _` / __|\n" +
      " / ____ \\| |_) \\__ \\ (_| | | | (_| \\__ \\\n" +
      "/_/    \\_\\ .__/|___/\\__,_|_|  \\__,_|___/\n" +
      "         | |                            \n" +
      "         |_|                            \n";

  @Inject
  private Server server;

  public void print(final PrintStream out) {
    out.print(APSARAS_BANNER);
  }
}
