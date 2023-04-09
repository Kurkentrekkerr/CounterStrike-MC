package dev.idw0309.counterstrike.commands.subcommands;

import dev.idw0309.counterstrike.Counterstrike;
import dev.idw0309.counterstrike.commands.SubCommandManager;
import dev.idw0309.counterstrike.data.DataManager;
import dev.idw0309.counterstrike.game.AddArena;
import dev.idw0309.counterstrike.message.Message;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Objects;

@SubCommandManager.SubCommand(name = "setlobbyspawn", description = "Command to join a arena", usage = "setlobbyspawn")
@RequiredArgsConstructor
public class SetLobbySpawn implements SubCommandManager.SubCommandExecutor {

    final Counterstrike plugin = Counterstrike.getInstance();

    @SneakyThrows
    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        Location location = player.getLocation();

        File f = new File(plugin.getDataFolder().getAbsolutePath(), "Config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);

        cfg.set("Spawnpoint.World", Objects.requireNonNull(location.getWorld()).getName());
        cfg.set("Spawnpoint.Lobby", location.getBlockX() + "--" + location.getBlockY() + "--" + location.getBlockZ());
        cfg.save(f);

        Message.sendToPlayer(player, "Je hebt de main lobby spawn opgeslagen.");
    }
}
