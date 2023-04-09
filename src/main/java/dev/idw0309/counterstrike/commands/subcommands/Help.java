package dev.idw0309.counterstrike.commands.subcommands;

import dev.idw0309.counterstrike.commands.SubCommandManager;
import lombok.RequiredArgsConstructor;

import org.bukkit.command.CommandSender;
import dev.idw0309.counterstrike.inventory.PlayerInventorys;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;


@SubCommandManager.SubCommand(name = "help", description = "See all the commands of this plugin", usage = "help", permission = "bootcamp.ddg.help")
@RequiredArgsConstructor
public class Help implements SubCommandManager.SubCommandExecutor {
    /**
     * This is a help command that will send all the commands to the player that are in this plugin
     * @param sender is the player that runned the command
     */

    private final PlayerInventorys playerInventorys;

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        playerInventorys.openInv(player, "Counter-Terrorist-Store");

    }
}
