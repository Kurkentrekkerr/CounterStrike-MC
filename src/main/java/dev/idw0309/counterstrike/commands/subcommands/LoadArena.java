package dev.idw0309.counterstrike.commands.subcommands;

import dev.idw0309.counterstrike.commands.SubCommandManager;
import dev.idw0309.counterstrike.data.DataManager;
import dev.idw0309.counterstrike.game.AddArena;
import dev.idw0309.counterstrike.message.Message;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@SubCommandManager.SubCommand(name = "load", description = "Command to join a arena", usage = "load")
@RequiredArgsConstructor
public class LoadArena implements SubCommandManager.SubCommandExecutor {

    private final AddArena addArena;

    private final DataManager dataManager;


    //TODO DEZE CLASS WEGHALEN, DEZE IS NIET MEER NODIG, MAPS WORDEN AUTOMATISCH GELADEN

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        //Checks if the person filled in the full command
        if(args.length == 0) {
            Message.sendToPlayer(player, "/counterstrike load <map name>");
            return;
        }

        FileConfiguration cfg = dataManager.getMapData();

        if (cfg.getString("Arenas." + args[0] + ".MapName") == null) {
            Message.sendToPlayer(player, "Deze map is niet bekend in het systeem.");
            return;
        }

        if (dataManager.arenas.containsKey(args[0])) {
            Message.sendToPlayer(player, "Deze map is al ingeladen in het systeem.");
            return;
        }

        addArena.loadArena(args[0]);

        Message.sendToPlayer(player, "Map ingeladen");
    }
}
