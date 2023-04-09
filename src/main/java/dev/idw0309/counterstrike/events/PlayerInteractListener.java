package dev.idw0309.counterstrike.events;

import dev.idw0309.counterstrike.Counterstrike;
import dev.idw0309.counterstrike.GameState;
import dev.idw0309.counterstrike.data.DataManager;
import dev.idw0309.counterstrike.data.game.GameData;
import dev.idw0309.counterstrike.data.game.GamePlayerData;
import dev.idw0309.counterstrike.game.Arena;
import dev.idw0309.counterstrike.game.TeamEnum;
import dev.idw0309.counterstrike.inventory.PlayerInventorys;
import dev.idw0309.counterstrike.message.Message;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

@RequiredArgsConstructor
public class PlayerInteractListener implements Listener {

    private final DataManager dataManager;

    private final Arena arena;

    private final PlayerInventorys playerInventorys;

    final Counterstrike plugin = Counterstrike.getInstance();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.getItemInHand() == null) return;

        if (player.getItemInHand().getType() == Material.AIR) return;

        IngameLobbyEvent(event);

        IngameShopEvent(event);


        /**if (event.getClickedBlock() == null) return;
        if (!(event.getClickedBlock().getState() instanceof Sign)) return;

        Sign sign = (Sign) event.getClickedBlock().getState();

        String arenaName = sign.getLine(0);

        File f = new File(plugin.getDataFolder().getAbsolutePath(), "Maps/" + arenaName + "/Data.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        int gameId = cfg.getInt("Data.GameId");


        //Checks if the Arena exist
        if(!(dataManager.arenas.containsKey(gameId))) return;

        arena.joinArena(event.getPlayer(), gameId);**/
    }

    private void IngameShopEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(!(player.getItemInHand().getItemMeta().getDisplayName().equals("§a§lShop §7(Right Click)"))) return;

        GamePlayerData gamePlayerData = dataManager.getGamePlayerData(player);
        if(!(gamePlayerData.getState() == GameState.SHOP)) return;

        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        if(gamePlayerData.getTeam() == TeamEnum.COUNTER_TERRORISTS) {
            playerInventorys.openInv(player, "Counter-Terrorist-Store");
        } else if(gamePlayerData.getTeam() == TeamEnum.TERRORISTS) {
            playerInventorys.openInv(player, "Terrorist-Store");
        }
    }

    private void IngameLobbyEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(!(player.getItemInHand().getItemMeta().getDisplayName().equals("§a§lTeam selector §7(Right Click)") || player.getItemInHand().getItemMeta().getDisplayName().equals("§c§lLeave Game §7(Right Click)"))) return;

        GamePlayerData gamePlayerData = dataManager.getGamePlayerData(player);
        if(!(gamePlayerData.getState() == GameState.INGAMELOBBY)) return;

        if (!(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        if (player.getItemInHand().getItemMeta().getDisplayName().equals("§a§lTeam selector §7(Right Click)")) {
            //TODO Team selector menu openen en maken
            playerInventorys.openInv(player, "Game-Lobby");
            return;
        }

        if (player.getItemInHand().getItemMeta().getDisplayName().equals("§c§lLeave Game §7(Right Click)")) {

            arena.leaveGame(player, false);
        }
    }
}
