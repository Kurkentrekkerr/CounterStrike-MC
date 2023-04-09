package dev.idw0309.counterstrike.game;

import dev.idw0309.counterstrike.Counterstrike;
import dev.idw0309.counterstrike.data.DataManager;
import dev.idw0309.counterstrike.message.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@RequiredArgsConstructor
public class AddArena {

    final Counterstrike plugin = Counterstrike.getInstance();

    private final DataManager dataManager;

    public void loadArena(String map){
        //File f = new File(plugin.getDataFolder().getAbsolutePath(), "Maps/Maps.yml");
        //FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);

        FileConfiguration cfg = dataManager.getMapData();

        int gameid = cfg.getInt("Arenas." + map + ".GameId");

        dataManager.arenas.put(gameid, map);
        dataManager.loadGameData(cfg.getInt("Arenas." + map + ".PlayerCount.Minimum"), cfg.getInt("Arenas." + map + ".PlayerCount.Maximum"), map, gameid);
        Message.sendToConsole("Map is ingeladen.");

    }

    public void autoLoadMap() {

        FileConfiguration cfg = dataManager.getMapData();

        ConfigurationSection map = cfg.getConfigurationSection("Arenas");

        for (String mapLoad : map.getValues(false).keySet()) {
            loadArena(mapLoad);
            Message.sendToConsole("Loaded: " + mapLoad);
        }
    }

    /**
     * public void loadWeapons() {
     *         ConfigurationSection weapons = config.getConfigurationSection("weapons");
     *
     *         for (String weaponId : weapons.getValues(false).keySet()) {
     *             ConfigurationSection weapon = weapons.getConfigurationSection(weaponId);
     *             WeaponType weaponType = WeaponType.valueOf(weapon.getString("weapon-type"));
     *             String name = weapon.getString("name");
     *             String displayName = weapon.isSet("display-name") ? weapon.getString("display-name") : name;
     *             displayName = ChatColor.translateAlternateColorCodes('&', displayName);
     *             TeamEnum team = TeamEnum.valueOf(weapon.getString("team"));
     *             int cost = weapon.getInt("cost");
     *             Material material = Material.valueOf(weapon.getString("material"));
     *             Weapon weaponObj;
     *
     *             int damage = weapon.getInt("damage");
     *             int magazines = weapon.getInt("magazines");
     *             int magazineCapacity = weapon.getInt("magazine-capacity");
     *             double reloadSpeed = weapon.getDouble("reload-speed");
     *
     *             weaponObj = new WeaponQA(weaponId, name, displayName, material, cost, magazineCapacity, magazines, damage, reloadSpeed, team, weaponType);
     *             Weapon.addWeapon(weaponObj);
     *         }
     *     }
     */


}
