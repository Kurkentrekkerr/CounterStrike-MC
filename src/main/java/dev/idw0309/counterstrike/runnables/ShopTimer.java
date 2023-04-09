package dev.idw0309.counterstrike.runnables;

import dev.idw0309.counterstrike.Counterstrike;
import dev.idw0309.counterstrike.GameState;
import dev.idw0309.counterstrike.data.DataManager;
import dev.idw0309.counterstrike.data.game.GameData;
import dev.idw0309.counterstrike.data.game.GamePlayerData;
import dev.idw0309.counterstrike.game.Arena;
import dev.idw0309.counterstrike.message.Message;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class ShopTimer extends BukkitRunnable {

    private int gameId;
    private DataManager dataManager;
    private Message message;
    private Arena arena;

    int timeToEnd = 10;

    final Counterstrike plugin = Counterstrike.getInstance();

    public ShopTimer(int gameId, Arena arena, Message message, DataManager dataManager){
        this.gameId = gameId;
        this.dataManager = dataManager;
        this.message = message;
        this.arena = arena;

    }

    public void run() {
        if (timeToEnd <= 0) {
            message.sendActionbarTextToIngamePlayers(gameId, "GOGOGO");

            arena.endShoptimer(gameId);
            arena.startGame(gameId);

            new GameTimer(gameId, arena, message, dataManager).runTaskTimer(plugin, 20L, 20L);
            this.cancel();
            return;
        }

        message.sendActionbarTextToIngamePlayers(gameId, "The game will start in " + timeToEnd + " second(s)");
        timeToEnd--;
    }
}
