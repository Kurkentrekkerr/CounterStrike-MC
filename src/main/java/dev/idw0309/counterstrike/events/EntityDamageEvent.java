package dev.idw0309.counterstrike.events;

import dev.idw0309.counterstrike.GameState;
import dev.idw0309.counterstrike.data.DataManager;
import dev.idw0309.counterstrike.data.game.GamePlayerData;
import dev.idw0309.counterstrike.message.Message;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

@RequiredArgsConstructor
public class EntityDamageEvent implements Listener {

    final DataManager dataManager;

    @EventHandler
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {

        //TODO MET DE HAND SLAAN UITZETTEN (persoon moet een voorwerp vasthebben)

        if (event.getEntity() instanceof Player) return;
        Player player = (Player) event.getEntity();

        GamePlayerData gamePlayerData = dataManager.getGamePlayerData(player);
        if(gamePlayerData.getState() == GameState.LOBBY) {
            event.setCancelled(true);
            return;
        }
    }
}
