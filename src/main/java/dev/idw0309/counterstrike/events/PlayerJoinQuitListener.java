package dev.idw0309.counterstrike.events;

import dev.idw0309.counterstrike.data.DataManager;
import dev.idw0309.counterstrike.data.game.GameData;
import dev.idw0309.counterstrike.data.game.GamePlayerData;
import dev.idw0309.counterstrike.data.player.PlayerData;
import dev.idw0309.counterstrike.game.Arena;
import dev.idw0309.counterstrike.game.TeamEnum;
import dev.idw0309.counterstrike.message.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class PlayerJoinQuitListener implements Listener {

    //final Counterstrike plugin = Counterstrike.getInstance();

    private final DataManager dataManager;
    private final Message message;


    @SneakyThrows
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        // When the player joins, load their data
        dataManager.loadPlayerData(e.getPlayer());
        dataManager.loadGamePlayerData(e.getPlayer());


    }


    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

        e.getPlayer().getInventory().clear();

        PlayerData playerData = dataManager.getPlayerData(e.getPlayer());

        // Save the player data
        dataManager.savePlayerData(e.getPlayer());

        // Unload the player
        dataManager.unloadPlayer(e.getPlayer());

        GamePlayerData gamePlayerData = dataManager.getGamePlayerData(e.getPlayer());

        //Checks if player was in a game
        if (gamePlayerData.getGame() == 0) return;

        dataManager.unloadPlayerGameData(e.getPlayer());

        int gameId = gamePlayerData.getGame();

        GameData gameData = dataManager.getGameData(gameId);

        if(gamePlayerData.getTeam() == TeamEnum.TERRORISTS) {
            gameData.removeAmountTerrorist();
        } else if (gamePlayerData.getTeam() == TeamEnum.COUNTER_TERRORISTS) {
            gameData.removeAmountCounterTerrorist();
        }

        gameData.removePlayerCount();

        message.sendToIngamePlayers("De speler " + e.getPlayer().getName() + " is het spel verlaten.", gameId);
    }
}
