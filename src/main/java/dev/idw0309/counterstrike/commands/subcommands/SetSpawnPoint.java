package dev.idw0309.counterstrike.commands.subcommands;

import dev.idw0309.counterstrike.Counterstrike;
import dev.idw0309.counterstrike.commands.SubCommandManager;
import dev.idw0309.counterstrike.data.DataManager;
import dev.idw0309.counterstrike.game.AddArena;
import dev.idw0309.counterstrike.message.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

@SubCommandManager.SubCommand(name = "setspawnpoint", description = "Command to join a arena", usage = "setspawnpoint")
@RequiredArgsConstructor
public class SetSpawnPoint implements SubCommandManager.SubCommandExecutor {


    final Counterstrike plugin = Counterstrike.getInstance();

    @SneakyThrows
    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        //Checks if the person filled in the full command
        if(args.length <= 1) {
            Message.sendToPlayer(player, "/counterstrike setspawnpoint <lobby/CT/T/spectator> <map name>");
            return;
        }

        File f = new File(plugin.getDataFolder().getAbsolutePath(), "Maps/Maps.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);


        if (cfg.getString(args[1] + ".MapName") == null) {
            Message.sendToPlayer(player, "Deze map is niet bekend in het systeem.");
            return;
        }

        Location location = player.getLocation();

        cfg.set(args[1] + ".World", Objects.requireNonNull(location.getWorld()).getName());

        if (args[0].equals("lobby")){
            cfg.set(args[1] + ".Spawnpoints.Lobby", location.getBlockX() + "--" + location.getBlockY() + "--" + location.getBlockZ());
            cfg.save(f);

            Message.sendToPlayer(player, "Lobby spawnpoint gezet");
            return;
        }

        if (args[0].equals("CT")){
            cfg.set(args[1] + ".Spawnpoints.Counter-Terrorist", location.getBlockX() + "--" + location.getBlockY() + "--" + location.getBlockZ());
            cfg.save(f);

            Message.sendToPlayer(player, "Counter-Terrorist spawnpoint gezet");
            return;
        }

        if (args[0].equals("T")){
            cfg.set(args[1] + ".Spawnpoints.Terrorist", location.getBlockX() + "--" + location.getBlockY() + "--" + location.getBlockZ());
            cfg.save(f);

            Message.sendToPlayer(player, "Terrorist spawnpoint gezet");
            return;
        }

        if (args[0].equals("spectator")){
            cfg.set(args[1] + ".Spawnpoints.Spectator", location.getBlockX() + "--" + location.getBlockY() + "--" + location.getBlockZ());
            cfg.save(f);

            Message.sendToPlayer(player, "Spectator spawnpoint gezet");
            return;
        }

        Message.sendToPlayer(player, "/counterstrike setspawnpoint <lobby/CT/T/spectator> <map name>");

    }
}
