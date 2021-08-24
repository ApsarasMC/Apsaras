package org.apsarasmc.spigot.command;

import net.kyori.adventure.text.Component;
import org.apsarasmc.apsaras.command.Command;
import org.apsarasmc.spigot.command.util.CommandSenderUtil;
import org.apsarasmc.spigot.util.TextComponentUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class SpigotRawCommand extends org.bukkit.command.Command {
  private final Command.Raw handle;
  public SpigotRawCommand(String name, Command.Raw handle) {
    super(name);
    this.handle = handle;
  }

  @Override
  public boolean execute(@Nonnull CommandSender sender, @Nonnull String commandLabel, @Nonnull String[] args) {
    return handle.process(CommandSenderUtil.transfer(sender), String.join(" ",args)).isSuccess();
  }

  @NotNull
  @Override
  public List< String > tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
    return handle.complete(CommandSenderUtil.transfer(sender), String.join(" ",args));
  }

  @NotNull
  @Override
  public String getDescription() {
    Component description = handle.shortDescription(null).orElseGet(
        ()->handle.extendedDescription(null).orElse(null));
    if(description == null){
      return this.getName();
    }else{
      return TextComponentUtil.toText(description);
    }
  }

  @NotNull
  @Override
  public String getUsage() {
    Component usage = handle.usage(null).orElse(null);
    if(usage == null){
      return this.getName();
    }else{
      return TextComponentUtil.toText(usage);
    }
  }
}
