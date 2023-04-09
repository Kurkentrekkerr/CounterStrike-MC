package dev.idw0309.counterstrike.inventory;

import de.tr7zw.nbtapi.NBTItem;
import dev.idw0309.counterstrike.Counterstrike;
import dev.idw0309.counterstrike.GameState;
import dev.idw0309.counterstrike.data.DataManager;
import dev.idw0309.counterstrike.data.game.GamePlayerData;
import dev.idw0309.counterstrike.message.Message;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;

@RequiredArgsConstructor
public class SetPlayerInventory {

    private final DataManager dataManager;


    public void getItems(Player player, String inv) {

        FileConfiguration cfg = dataManager.getGameInvData();

        ConfigurationSection invItem = cfg.getConfigurationSection("Inventorys." + inv + ".Inventory-slots");

        for (String invItems : invItem.getValues(false).keySet()) {


            if(inv.equals("CT-Standard-Inv") || inv.equals("T-Standard-Inv")) {
                String cat = getCategorie(inv, Integer.parseInt(invItems));

                setItem(player, Integer.parseInt(invItems), cat);
            }

            if(inv.equals("Bomb")) {
                String cat = getCategorie(inv, Integer.parseInt(invItems));

                setBomb(player, Integer.parseInt(invItems), cat);
            }

        }

    }

    private void setBomb(Player player, int id, String cat) {

        if (!(cat.equals("Bomb"))) return;

        ItemStack material = new ItemStack(getMaterial("bomb"));

        if(isNbtItem("bomb")) {
            NBTItem nbt = new NBTItem(material);
            nbt.setString("counterstrike", getNbtData("bomb"));
            material = nbt.getItem();
        }

        ItemMeta meta = material.getItemMeta();
        assert meta != null;
        meta.setDisplayName(getMaterialName("bomb"));
        material.setItemMeta(meta);

        player.getInventory().setItem(id,material);

    }

    private void setItem(Player player, int id, String cat) {
        GamePlayerData gamePlayerData = dataManager.getGamePlayerData(player);

        String weapon = null;

        //TODO VERDER CODE, ALLEEN HANDGUN NOG MAAR GEDAAN.

        if (cat.equals("Handgun")) {
            weapon = gamePlayerData.getHandgun();
        } else if (cat.equals("Automatic_Rifle")) {
            weapon = gamePlayerData.getAutomaticgun();
        } else if (cat.equals("Teser")) {
            weapon = "teser";
        } else if (cat.equals("Melee")) {
            weapon = gamePlayerData.getMelee();
        } else if (cat.equals("Throable1")) {
            weapon = gamePlayerData.getThroable1();
        } else if (cat.equals("Throable2")) {
            weapon = gamePlayerData.getThroable2();
        }

        if(weapon == null) return;

        ItemStack material = new ItemStack(getMaterial(weapon));

        if(isNbtItem(weapon)) {
            NBTItem nbt = new NBTItem(material);
            nbt.setString("counterstrike", getNbtData(weapon));
            material = nbt.getItem();
        }

        ItemMeta meta = material.getItemMeta();
        assert meta != null;
        meta.setDisplayName(getMaterialName(weapon));
        material.setItemMeta(meta);

        player.getInventory().setItem(id,material);
    }


    private String getCategorie(String inv, int id) {
        FileConfiguration cfg = dataManager.getGameInvData();
        return cfg.getString("Inventorys." + inv + ".Inventory-slots." + id + ".Categorie");
    }

    private String getMaterialName(String weapon) {
        FileConfiguration cfg2 = dataManager.getWeaponData();
        return cfg2.getString("Weapons." + weapon + ".Name");
    }

    private Material getMaterial(String weapon) {
        FileConfiguration cfg2 = dataManager.getWeaponData();
        return Material.valueOf(cfg2.getString("Weapons." + weapon + ".Material"));
    }

    private Boolean isNbtItem(String weapon) {
        FileConfiguration cfg2 = dataManager.getWeaponData();
        return cfg2.getBoolean("Weapons." + weapon + ".NbtItem.isNbtItem");

    }

    private String getNbtData(String weapon) {
        FileConfiguration cfg2 = dataManager.getWeaponData();
        return cfg2.getString("Weapons." + weapon + ".NbtItem.NBT-Data");
    }


}
