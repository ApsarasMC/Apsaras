package org.apsarasmc.apsaras.command;

import net.kyori.adventure.audience.Audience;
import org.apsarasmc.apsaras.builder.AbstractBuilder;
import org.apsarasmc.apsaras.entity.PermissionCheckable;

public interface CommandContext extends Audience, PermissionCheckable {
  CommandSender sender();

  < T > T get(CommandArgument< T > argument);

  < T > void put(CommandArgument< T > argument, T value);

  interface Builder extends AbstractBuilder< CommandContext > {
    Builder sender(CommandSender sender);

    < T > Builder argument(CommandArgument< T > argument, T value);
  }
}