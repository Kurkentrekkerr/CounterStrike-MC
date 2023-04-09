package dev.idw0309.counterstrike.events;

import de.tr7zw.nbtapi.NBTItem;
import dev.idw0309.counterstrike.Counterstrike;
import dev.idw0309.counterstrike.game.Arena;
import dev.idw0309.counterstrike.message.Message;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;

@RequiredArgsConstructor
public class InventoryClickListener implements Listener {

    final Counterstrike plugin = Counterstrike.getInstance();

    private final Arena arena;

    File f = new File(plugin.getDataFolder().getAbsolutePath(), "Inventorys/Inv.yml");
    FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);

    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent e){

        if(e.getCurrentItem() == null) return;

        if(e.getCurrentItem().getItemMeta() == null) return;

        if(e.getCurrentItem().getItemMeta().getDisplayName() == null) return;

        int slot = e.getRawSlot();

        CTShopInventory(e, slot);
        TeamInventory(e, slot);

    }

    private void TeamInventory(InventoryClickEvent e, int slot) {

        if(!(e.getView().getTitle().equals(cfg.getString("Inventorys.Game-Lobby.Name")))) return;

        if(!(cfg.getBoolean("Inventorys.Game-Lobby.Inventory-ids." + slot + ".Functions.hasFunction"))) return;

        String function = cfg.getString("Inventorys.Game-Lobby.Inventory-ids." + slot + ".Functions.function");

        arena.runTeamMenuFunction((Player) e.getWhoClicked(), function);

        e.setCancelled(true);

    }

    private void CTShopInventory(InventoryClickEvent e, int slot) {

        if(!(e.getView().getTitle().equals(cfg.getString("Inventorys.Counter-Terrorist-Store.Name")))) return;

        if(!(cfg.getBoolean("Inventorys.Counter-Terrorist-Store.Inventory-ids." + slot + ".Functions.hasFunction"))) return;

        NBTItem nbti = new NBTItem(e.getCurrentItem());

        //TODO VERDER DEVELOPEN DAT ER OOK GELD AFGAAT EN PERSOON HET WAPEN KRIJGT
        Message.sendToConsole("" + nbti.getString("mtcustom"));
        Message.sendToConsole("" + e.getWhoClicked());
        Message.sendToConsole("" + cfg.getString("Inventorys.Counter-Terrorist-Store.Inventory-ids." + slot + ".Functions.function"));

        e.setCancelled(true);
    }
}
