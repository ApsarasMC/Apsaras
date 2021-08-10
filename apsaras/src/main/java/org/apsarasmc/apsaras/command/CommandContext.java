package org.apsarasmc.apsaras.command;

import net.kyori.adventure.audience.Audience;
import org.apsarasmc.apsaras.entity.PermissionCheckable;

public interface CommandContext extends Audience, PermissionCheckable {
  CommandSender sender();
}
