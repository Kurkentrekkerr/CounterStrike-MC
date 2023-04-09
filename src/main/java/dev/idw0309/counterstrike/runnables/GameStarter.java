package dev.idw0309.counterstrike.runnables;

import dev.idw0309.counterstrike.Counterstrike;
import dev.idw0309.counterstrike.data.DataManager;
import dev.idw0309.counterstrike.data.game.GameData;
import dev.idw0309.counterstrike.game.Arena;
import dev.idw0309.counterstrike.inventory.SetPlayerInventory;
import dev.idw0309.counterstrike.message.Message;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStarter extends BukkitRunnable {

    private DataManager dataManager;
    private Message message;
    private int gameId;
    private GamePlayerCounter gamePlayerCounter;
    private Arena arena;

    public int timeToStart;

    private final SetPlayerInventory setPlayerInventory;

    final Counterstrike plugin = Counterstrike.getInstance();

    public GameStarter(int gameId, DataManager dataManager, Message message) {
        this.gameId = gameId;
        this.dataManager = dataManager;
        this.message = message;


        setPlayerInventory = new SetPlayerInventory(dataManager);

        arena = new Arena(dataManager, message, setPlayerInventory);
        gamePlayerCounter = new GamePlayerCounter(gameId, dataManager, message);

        timeToStart = 45;
    }

    public void run() {
        if(timeToStart <= 0) {
            arena.startGameShop(gameId);
            message.sendActionbarTextToIngamePlayers(gameId, "This game will start in " + timeToStart + " second(s)!");
            this.cancel();
            return;
        }

        GameData gameData = dataManager.getGameData(gameId);

        int minimumplayers = gameData.getMinPlayerCount();
        int ingameplayers = gameData.getPlayerCount();

        if (!(ingameplayers >= minimumplayers)) {
            message.sendActionbarTextToIngamePlayers(gameId, "The game needs " + (minimumplayers - ingameplayers) + " more player(s) to start the game.");
            gamePlayerCounter.runTaskTimer(plugin, 20L, 200L);
            this.cancel();
            return;
        }

        message.sendActionbarTextToIngamePlayers(gameId, "This game will start in " + timeToStart + " second(s)!");
        timeToStart--;
    }
}
