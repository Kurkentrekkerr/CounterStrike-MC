package dev.idw0309.counterstrike.inventory;

import de.tr7zw.nbtapi.NBTItem;
import dev.idw0309.counterstrike.Counterstrike;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.Objects;

public class PlayerInventorys {

    final Counterstrike plugin = Counterstrike.getInstance();

    File f = new File(plugin.getDataFolder().getAbsolutePath(), "Inventorys/Inv.yml");
    FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);

    public void openInv(Player player, String inv) {

        Inventory inventory = Bukkit.createInventory(null, cfg.getInt("Inventorys." + inv + ".Rows")*9, Objects.requireNonNull(cfg.getString("Inventorys." + inv + ".Name")));

        ConfigurationSection invItem = cfg.getConfigurationSection("Inventorys." + inv + ".Inventory-ids");

        for (String invItems : invItem.getValues(false).keySet()) {
            //loadArena(mapLoad);

            ItemStack material = new ItemStack(getMaterial(inv, Integer.parseInt(invItems)));

            if(isNbtItem(inv, Integer.parseInt(invItems))) {
                NBTItem nbt = new NBTItem(material);
                nbt.setString("counterstrike", getNbtData(inv, Integer.parseInt(invItems)));
                material = nbt.getItem();
            }

            ItemMeta meta = material.getItemMeta();
            assert meta != null;
            meta.setDisplayName(getMaterialName(inv, Integer.parseInt(invItems)));
            material.setItemMeta(meta);

            inventory.setItem(Integer.parseInt(invItems), material);
        }

        //ItemMeta meta = glass.getItemMeta();
        //meta.setDisplayName("§l§l§l§l§l§l");
        ////meta.addEnchant(Enchantment.DURABILITY, 1, true);
        //glass.setItemMeta(meta);


        //inventory.setItem(1, glass);

        player.openInventory(inventory);

    }

    //TODO checken ofdat er een wapen is geadd, als er een is geadd de juiste texure+naam

    private String getMaterialName(String inv, int id) {

        return cfg.getString("Inventorys." + inv + ".Inventory-ids." + id + ".Name");
    }

    private Material getMaterial(String inv, int id) {
        return Material.valueOf(cfg.getString("Inventorys." + inv + ".Inventory-ids." + id + ".Material"));
    }

    private Boolean isNbtItem(String inv, int id) {

        return cfg.getBoolean("Inventorys." + inv + ".Inventory-ids." + id + ".NbtItem.isNbtItem");

    }

    private String getNbtData(String inv, int id) {
        return cfg.getString("Inventorys." + inv + ".Inventory-ids." + id + ".NbtItem.NBT-Data");
    }
}
