package dev.idw0309.counterstrike.runnables;

import dev.idw0309.counterstrike.Counterstrike;
import dev.idw0309.counterstrike.GameState;
import dev.idw0309.counterstrike.data.DataManager;
import dev.idw0309.counterstrike.data.game.GameData;
import dev.idw0309.counterstrike.message.Message;
import lombok.RequiredArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class GamePlayerCounter extends BukkitRunnable {

    private final DataManager dataManager;

    private final Message message;

    private int gameId;


    final Counterstrike plugin = Counterstrike.getInstance();

    public GamePlayerCounter(int gameId, DataManager dataManager, Message message) {
        this.dataManager = dataManager;
        this.message = message;
        this.gameId = gameId;

    }

    @Override
    public void run() {
        GameData gameData = dataManager.getGameData(gameId);

        if (!(gameData.getGameState() == GameState.LOBBY)) return;

        int minimumplayers = gameData.getMinPlayerCount();
        int ingameplayers = gameData.getPlayerCount();

        if(ingameplayers == 0) {
            this.cancel();
            return;
        }

        if (!(ingameplayers >= minimumplayers)) {
            message.sendActionbarTextToIngamePlayers(gameId, "The game needs " + (minimumplayers - ingameplayers) + " more player(s) to start the game.");
            return;
        }
        new GameStarter(gameId, dataManager, message).runTaskTimer(plugin, 40L, 20L);
        this.cancel();

    }
}
