package dev.idw0309.counterstrike.data;

import dev.idw0309.counterstrike.Counterstrike;
import dev.idw0309.counterstrike.GameState;
import dev.idw0309.counterstrike.data.game.GameData;
import dev.idw0309.counterstrike.data.game.GamePlayerData;
import dev.idw0309.counterstrike.data.player.PlayerData;
import dev.idw0309.counterstrike.data.weapon.WeaponData;
import dev.idw0309.counterstrike.game.TeamEnum;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class DataManager {

    final Counterstrike plugin = Counterstrike.getInstance();

    //All active areana's will be added here
    public final HashMap<Integer, String> arenas = new HashMap<>();

    //Weapon data Map
    private final HashMap<String, WeaponData> weaponData = new HashMap<>();

    //PlayerData Map
    private final Map<Player, PlayerData> playerData = new ConcurrentHashMap<>();

    //GamePlayerData Map
    private final Map<Player, GamePlayerData> gamePlayerData = new ConcurrentHashMap<>();

    //GameData Map
    private final Map<Integer, GameData> gameData = new ConcurrentHashMap<>(); //(this will be a random created number on first join to allow multiple games)


    public void loadPlayerData(Player player) {
        FileConfiguration cfg = getPlayerData();

        //Checking if the player exists
        if (cfg.getString("Playerdata." + player.getUniqueId() + ".Username") == null) {
            PlayerData data = new PlayerData(0,0,0,0,0,"None", 0);
            playerData.put(player, data);
            return;
        }

        int kills = cfg.getInt("Playerdata." + player.getUniqueId() + ".TotalKills");
        int deaths = cfg.getInt("Playerdata." + player.getUniqueId() + ".TotalDeaths");
        int money = cfg.getInt("Playerdata." + player.getUniqueId() + ".TotalMoney");
        int wins = cfg.getInt("Playerdata." + player.getUniqueId() + ".Wins");
        int loses = cfg.getInt("Playerdata." + player.getUniqueId() + ".Loses");
        String rank = cfg.getString("Playerdata." + player.getUniqueId() + ".Rank");
        int level = cfg.getInt("Playerdata." + player.getUniqueId() + ".Level");


        PlayerData data = new PlayerData(kills, deaths, money, wins, loses, rank, level);
        playerData.put(player, data);
    }

    public void savePlayerData(Player player) {
        File f = new File(plugin.getDataFolder().getAbsolutePath(), "PlayerData/PlayerData.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);

        PlayerData playerData = getPlayerData(player);

        try{

            cfg.set("Playerdata." + player.getUniqueId() + ".Username", player.getName());
            cfg.set("Playerdata." + player.getUniqueId() + ".TotalKills", playerData.getTotalKills());
            cfg.set("Playerdata." + player.getUniqueId() + ".TotalDeaths", playerData.getTotalDeaths());
            cfg.set("Playerdata." + player.getUniqueId() + ".TotalMoney", playerData.getTotalMoney());
            cfg.set("Playerdata." + player.getUniqueId() + ".Wins", playerData.getWins());
            cfg.set("Playerdata." + player.getUniqueId() + ".Loses", playerData.getLoses());
            cfg.set("Playerdata." + player.getUniqueId() + ".Rank", playerData.getRank());
            cfg.set("Playerdata." + player.getUniqueId() + ".Level", playerData.getLevel());

            cfg.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetGamePlayerData(Player player) {
        GamePlayerData gamePlayerDataa = getGamePlayerData(player);

        gamePlayerDataa.setState(GameState.LOBBY);
        gamePlayerDataa.setGame(0);
        gamePlayerDataa.setIngameDeaths(0);
        gamePlayerDataa.setIngameKills(0);
        gamePlayerDataa.setIngameMoney(0);
        gamePlayerDataa.setTeam(TeamEnum.NONE);
    }


    public void loadGamePlayerData(Player player) {
        //Checking if the player exists

        //TODO Maken dat die de weapon data uit een config pakt (uit playerdata folder)
        GamePlayerData data = new GamePlayerData(0, 0, 0, 0, TeamEnum.NONE, GameState.MAINLOBBY, "glock19", "ak47", 0, "grenade", "grenade", "zakmes");
        gamePlayerData.put(player, data);
    }

    public void loadGameData(int minplayercount, int maxplayercount, String map, int gameid) {
        //Checking if the player exists

        GameData data = new GameData(0, minplayercount, maxplayercount, map, GameState.LOBBY, 0, 0, 0, 0, 0);
        gameData.put(gameid, data);
    }



    public void unloadPlayer(Player player) {
        playerData.remove(player);
    }

    public void unloadPlayerGameData(Player player) {


        gamePlayerData.remove(player);

    }

    public PlayerData getPlayerData(Player player) {
        return playerData.get(player);
    }

    public GamePlayerData getGamePlayerData(Player player) {
        return gamePlayerData.get(player);
    }

    public GameData getGameData(int game) {
        return gameData.get(game);
    }



    //TODO HIER BEZIG HIERMNEE VERDER GAAN, ALLE CLASSES AFGAAN EN NAAR DIT VERRANDEREN
    public FileConfiguration getPlayerData(){
        File f = new File(plugin.getDataFolder().getAbsolutePath(), "PlayerData/PlayerData.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);

        return cfg;
    }

    public FileConfiguration getMapData() {
        File f = new File(plugin.getDataFolder().getAbsolutePath(), "Maps/Maps.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);

        return cfg;
    }

    public FileConfiguration getWeaponData() {
        File f = new File(plugin.getDataFolder().getAbsolutePath(), "WeaponData/WeaponData.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);


        return cfg;
    }

    public FileConfiguration getConfigData(){
        File f = new File(plugin.getDataFolder().getAbsolutePath(), "Config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);

        return cfg;
    }

    public FileConfiguration getGameInvData(){
        File f = new File(plugin.getDataFolder().getAbsolutePath(), "Inventorys/GameInv.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);

        return cfg;
    }

}
