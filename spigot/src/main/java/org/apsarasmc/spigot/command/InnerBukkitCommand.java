package org.apsarasmc.spigot.command;

import net.kyori.adventure.text.Component;
import org.apsarasmc.spigot.entity.SpigotPlayer;
import org.apsarasmc.spigot.entity.SpigotSender;
import org.apsarasmc.spigot.util.TextComponentUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class InnerBukkitCommand extends Command {
  private final org.apsarasmc.apsaras.command.Command command;
  protected InnerBukkitCommand(org.apsarasmc.apsaras.command.Command command, @Nonnull String name) {
    super(name);
    this.command = command;
  }
  @Override
  public boolean execute(@Nonnull CommandSender sender, @Nonnull String commandLabel, @Nonnull String[] args) {
    if(sender instanceof Player) {
      return command.process(new SpigotPlayer((Player) sender), String.join(" ",args)).isSuccess();
    }else {
      return command.process(new SpigotSender(sender), String.join(" ",args)).isSuccess();
    }
  }

  @Nonnull
  @Override
  public List< String > tabComplete(@Nonnull CommandSender sender, @Nonnull String alias, @Nonnull String[] args) throws IllegalArgumentException {
    if(sender instanceof Player) {
      return command.complete(new SpigotPlayer((Player) sender), String.join(" ",args));
    }else {
      return command.complete(new SpigotSender(sender), String.join(" ",args));
    }
  }

  @Override
  public boolean testPermissionSilent(@Nonnull CommandSender target) {
    if(target instanceof Player) {
      return command.canExecute(new SpigotPlayer((Player) target));
    }else {
      return command.canExecute(new SpigotSender(target));
    }
  }

  @Nonnull
  @Override
  public String getDescription() {
    Optional< Component > optionalShortDescription = command.shortDescription();
    Optional< Component > optionalExtendedDescription = command.extendedDescription();
    return TextComponentUtil.toText(
      optionalShortDescription.orElse(Component.text(""))
        .append(Component.newline())
        .append(optionalExtendedDescription.orElse(Component.text("")))
    );
  }

  @Nonnull
  @Override
  public String getUsage() {
    Optional< Component > optionalComponent = command.usage();
    return TextComponentUtil.toText(optionalComponent.orElse(Component.text("")));
  }
}
