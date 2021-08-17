package org.apsarasmc.plugin;

import org.apsarasmc.apsaras.Server;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.PrintStream;

@Singleton
public class BannerPrinter {
  public static final String[] APSARAS_BANNER = new String[]{
    "    /\\                                  ",
    "   /  \\   _ __  ___  __ _ _ __ __ _ ___ ",
    "  / /\\ \\ | '_ \\/ __|/ _` | '__/ _` / __|",
    " / ____ \\| |_) \\__ \\ (_| | | | (_| \\__ \\",
    "/_/    \\_\\ .__/|___/\\__,_|_|  \\__,_|___/",
    "         | |                            ",
    "         |_|                            "};

  @Inject
  private Server server;

  public void print(final PrintStream out) {
    for (String s : APSARAS_BANNER) {
      out.println(s);
    }
  }
}
