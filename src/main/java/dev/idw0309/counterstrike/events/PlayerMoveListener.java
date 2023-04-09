package dev.idw0309.counterstrike.events;

import dev.idw0309.counterstrike.GameState;
import dev.idw0309.counterstrike.data.DataManager;
import dev.idw0309.counterstrike.data.game.GamePlayerData;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

@RequiredArgsConstructor
public class PlayerMoveListener implements Listener {

    private final DataManager dataManager;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        //if (e.getPlayer() instanceof Player) return;

        GamePlayerData gamePlayerData = dataManager.getGamePlayerData(e.getPlayer());

        if (!(gamePlayerData.getState() == GameState.SHOP)) return;

        if (e.getFrom().getX() == e.getTo().getX() &&
                e.getFrom().getY() == e.getTo().getY() &&
                e.getFrom().getZ() == e.getTo().getZ()) return;

        e.setTo(e.getFrom());
    }

}
