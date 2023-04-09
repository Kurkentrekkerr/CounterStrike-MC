package dev.idw0309.counterstrike.runnables;

import dev.idw0309.counterstrike.Counterstrike;
import dev.idw0309.counterstrike.data.DataManager;
import dev.idw0309.counterstrike.data.game.GameData;
import dev.idw0309.counterstrike.data.game.GamePlayerData;
import dev.idw0309.counterstrike.game.Arena;
import dev.idw0309.counterstrike.game.TeamEnum;
import dev.idw0309.counterstrike.message.Message;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class GameTimer extends BukkitRunnable {

    private int gameId;
    private DataManager dataManager;
    private Message message;
    private Arena arena;

    int timeToEnd = 5; //TODO DIT TERUG AANPASSEN NAAR 115

    final Counterstrike plugin = Counterstrike.getInstance();

    public GameTimer(int gameId, Arena arena, Message message, DataManager dataManager){
        this.gameId = gameId;
        this.dataManager = dataManager;
        this.message = message;
        this.arena = arena;

    }

    public void run() {
        GameData gameData = dataManager.getGameData(gameId);
        if(gameData.getPlayerCount() <= 0) {
            arena.resetArena(gameId);
            this.cancel();
            return;
        }

        if(gameData.getAmountCounterTerrorist() == 0 || timeToEnd <= 0) {

            arena.roundEnded(gameId, TeamEnum.COUNTER_TERRORISTS);

            //message.sendActionbarTextToIngamePlayers(gameId, "Counterterrorists hebben gewonnen!");
            Message.sendToConsole("Counter-Terrorists hebben gewonnen!");
            this.cancel();
            return;
        }

        if(gameData.getAmountTerrorist() == 0) {

            arena.roundEnded(gameId, TeamEnum.TERRORISTS);

            //message.sendActionbarTextToIngamePlayers(gameId, "Terrorists hebben gewonnen!");
            Message.sendToConsole("Terrorists hebben gewonnen!");
            this.cancel();
            return;
        }


        message.sendActionbarTextToIngamePlayers(gameId, "This round will end in " + timeToEnd + " second(s)");
        timeToEnd--;
    }
}
