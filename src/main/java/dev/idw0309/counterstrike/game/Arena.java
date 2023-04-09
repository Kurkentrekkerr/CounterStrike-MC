package dev.idw0309.counterstrike.game;

import dev.idw0309.counterstrike.Counterstrike;
import dev.idw0309.counterstrike.GameState;
import dev.idw0309.counterstrike.data.DataManager;
import dev.idw0309.counterstrike.data.game.GameData;
import dev.idw0309.counterstrike.data.game.GamePlayerData;
import dev.idw0309.counterstrike.data.player.PlayerData;
import dev.idw0309.counterstrike.inventory.SetPlayerInventory;
import dev.idw0309.counterstrike.message.Message;
import dev.idw0309.counterstrike.runnables.GamePlayerCounter;
import dev.idw0309.counterstrike.runnables.ShopTimer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.*;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;

@RequiredArgsConstructor
public class Arena {

    //TODO JOIN NPCS MAKEN, MAKEN DAT HET GEREGISTREERD WORD ALS IEMAND IEMAND ANDERS KILLED, ALLES OPTIMIZE EN AANPASSEN(DENK AAN CHATMESSAGES ENZV)
    //TODO WAPEN FUNCTIES MAKEN

    @Getter
    private final Collection<Sign> signs = new HashSet<>();

    private final Counterstrike plugin = Counterstrike.getInstance();

    private final DataManager dataManager;

    private final Message message;

    private final SetPlayerInventory setPlayerInventory;


    public void joinArena(Player player, int gameId) {

        GameData gameData = dataManager.getGameData(gameId);

        //Teleports player to the lobby
        TeleportToGameLobby(player, gameId, gameData.getMap());
    }

    public void TeleportToGameLobby(Player player, int gameId, String map) {
        //Loading all game data
        GamePlayerData gamePlayerData = dataManager.getGamePlayerData(player);
        GameData gameData = dataManager.getGameData(gameId);

        if (gameData.getGameState() == GameState.INGAME) {
            Message.sendToPlayer(player, "De game die je probeert te joinen is al gestart.");
            return;
        }

        if (gameData.getPlayerCount() >= gameData.getMaxPlayerCount()) {
            Message.sendToPlayer(player, "De game die je probeert te joinen is vol.");
            return;
        }

        if (gamePlayerData.getGame() == gameId) {
            Message.sendToPlayer(player, "Je zit al in de game die je probeert te joinen.");
            return;
        }

        FileConfiguration cfg = dataManager.getMapData();

        //Checks what the world is of the filled in arena
        String world = cfg.getString("Arenas." + map + ".World");

        //Checks the lobby point of the world
        String stringSpawn = cfg.getString("Arenas." + map + ".Spawnpoints.Lobby");
        assert stringSpawn != null;
        String[] spawnData = stringSpawn.split("--");
        assert world != null;
        Location spawn = new Location(Bukkit.getWorld(world), Integer.parseInt(spawnData[0]), Integer.parseInt(spawnData[1]), Integer.parseInt(spawnData[2]));

        //Teleports the player to the lobby point and adds the player to the Map with the arena name
        player.teleport(spawn);

        player.getInventory().clear();

        ItemStack leather = new ItemStack(Material.LEATHER);
        ItemMeta leathermeta = leather.getItemMeta();
        assert leathermeta != null;
        leathermeta.setDisplayName("§a§lTeam selector §7(Right Click)");
        leather.setItemMeta(leathermeta);

        ItemStack redbed = new ItemStack(Material.RED_BED);
        ItemMeta redbedmeta = redbed.getItemMeta();
        assert redbedmeta != null;
        redbedmeta.setDisplayName("§c§lLeave Game §7(Right Click)");
        redbed.setItemMeta(redbedmeta);

        player.getInventory().setItem(0, leather);
        player.getInventory().setItem(8, redbed);

        setRandomTeamOnJoin(gameData, gamePlayerData);

        gamePlayerData.setGame(gameId);
        gamePlayerData.setState(GameState.INGAMELOBBY);

        //Checks if there no players in the arena, becaus if there is a player in the arena, this will stop it from creating 2 timers for the same arena.
        if(gameData.getPlayerCount() == 0) {
            GamePlayerCounter gamePlayerCounter;
            gamePlayerCounter = new GamePlayerCounter(gameId, dataManager, message);

            gamePlayerCounter.runTaskTimer(plugin, 20L, 200L);
        }

        gameData.addPlayerCount();

        message.sendToIngamePlayers("De speler " + player.getName() + " is het spel gejoined.", gameId);
        Message.sendToPlayer(player, "Je bent de game "+ map + " gejoined!");

    }

    public void leaveGame(Player player, boolean gameEnded) {
        GamePlayerData gamePlayerData = dataManager.getGamePlayerData(player);

        int gameId = gamePlayerData.getGame();

        GameData gameData = dataManager.getGameData(gameId);
        Enum team = gamePlayerData.getTeam();

        //Resetting all GamePlayerData
        dataManager.resetGamePlayerData(player);


        FileConfiguration cfg = dataManager.getConfigData();

        //Checks what world is the lobby
        String world = cfg.getString("Spawnpoint.World");

        //Checks the lobby point of the server
        String stringLobby = cfg.getString("Spawnpoint.Lobby");
        assert stringLobby != null;
        String[] lobbyData = stringLobby.split("--");
        assert world != null;
        Location lobby = new Location(Bukkit.getWorld(world), Integer.parseInt(lobbyData[0]), Integer.parseInt(lobbyData[1]), Integer.parseInt(lobbyData[2]));


        //Checks if the game is finished, when it is, it doesn't need all of these things.
        if (!(gameEnded)) {

            gameData.removePlayerCount();

            //Removes items from player his inv. player.getInventory().remove() not used becaus of a random crash thing with this method
            Objects.requireNonNull(player.getInventory().getItem(8)).setAmount(0);
            Objects.requireNonNull(player.getInventory().getItem(0)).setAmount(0);

            if (team == TeamEnum.TERRORISTS) {

                gameData.removeAmountTerrorist();

            }

            if (team == TeamEnum.COUNTER_TERRORISTS) {

                gameData.removeAmountCounterTerrorist();
            }

            message.sendToIngamePlayers("De speler " + player.getName() + " is het spel verlaten.", gameId);

        }
        player.teleport(lobby);


    }

    public void startGameShop(int gameId) {
        //TODO ADD OOK EEN SCOREBOARD

        FileConfiguration cfg = dataManager.getGameInvData();

        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compassmeta = compass.getItemMeta();
        assert compassmeta != null;
        compassmeta.setDisplayName("§a§lShop §7(Right Click)");
        compass.setItemMeta(compassmeta);

        GameData gameData = dataManager.getGameData(gameId);
        gameData.setGameState(GameState.INGAME);

        //Get all players with the same gameid in there playergamedata
        for(Player player : Bukkit.getOnlinePlayers()){
            GamePlayerData gamePlayerData = dataManager.getGamePlayerData(player);

            if (!(gamePlayerData.getGame() == gameId)) continue;

            gamePlayerData.setState(GameState.SHOP);

            String team = null;

            if (gamePlayerData.getTeam() == TeamEnum.TERRORISTS) {
                team = "Terrorist";
            } else if (gamePlayerData.getTeam() == TeamEnum.COUNTER_TERRORISTS){
                team = "Counter-Terrorist";
            }

            respawnPlayer(player, gameId, team);

            ConfigurationSection invItem = cfg.getConfigurationSection("Inventorys.Bomb.Inventory-slots");

            for (String invItems : invItem.getValues(false).keySet()) {
                if(cfg.getString("Inventorys.Bomb.Inventory-slots." + invItems + ".Categorie").equals("Bomb")) {
                    player.getInventory().setItem(Integer.parseInt(invItems), compass);
                }
            }

        }

        gameData.addGameRound(); //voegt de eerste ronde toe aan de data van de map

        new ShopTimer(gameId, this, message, dataManager).runTaskTimer(plugin, 0L, 20L);

    }

    public void endShoptimer(int gameId) {

        FileConfiguration cfg = dataManager.getGameInvData();

        for(Player player : Bukkit.getOnlinePlayers()) {
            GamePlayerData gamePlayerData = dataManager.getGamePlayerData(player);

            if (!(gamePlayerData.getGame() == gameId)) continue;

            gamePlayerData.setState(GameState.INGAME);


            ConfigurationSection invItem = cfg.getConfigurationSection("Inventorys.Bomb.Inventory-slots");

            for (String invItems : invItem.getValues(false).keySet()) {

                if (cfg.getString("Inventorys.Bomb.Inventory-slots." + invItems + ".Categorie").equals("Bomb")) {
                    player.getInventory().clear(Integer.parseInt(invItems));
                }
            }
        }
    }

    public void startGame(int gameId) {
        //TODO GAME MAKEN
        //TODO BOM TOEVOEGEN
    }

    public void roundEnded(int gameId, Enum teamWon) {

        GameData gameData = dataManager.getGameData(gameId);

        if (teamWon == TeamEnum.COUNTER_TERRORISTS) {
            gameData.addWinsDefenders();
        } else if (teamWon == TeamEnum.TERRORISTS) {
            gameData.addWinsTerrorist();
        }

        int getMaxRounds = dataManager.getMapData().getInt("Arenas." + gameData.getMap() + ".Max-Rounds");
        int ctWonRounds = gameData.getWinsDefenders(); //Counterterrorist won rounds
        int tWonRounds = gameData.getWinsTerrorist(); //Terrorist won rounds

        if ((ctWonRounds - tWonRounds) > getMaxRounds/2) {
            endGamePlayer(gameId, TeamEnum.COUNTER_TERRORISTS);
            Message.sendToConsole("CT WON BY ROUNDS");
            return;
        }

        if ((tWonRounds - ctWonRounds) > getMaxRounds/2) {
            endGamePlayer(gameId, TeamEnum.TERRORISTS);
            Message.sendToConsole("T WON BY ROUNDS");
            return;
        }

        if ((tWonRounds + ctWonRounds) == getMaxRounds) {

            endGamePlayer(gameId, TeamEnum.DRAW);
            Message.sendToConsole("DRAW (GELIJK SPEL TERRORIST EN COUNTERTERORIST");
            return;
        }

        for(Player player : Bukkit.getOnlinePlayers()) {
            GamePlayerData gamePlayerData = dataManager.getGamePlayerData(player);

            if (!(gamePlayerData.getGame() == gameId)) continue;

            String team = null;
            Enum teamm = gamePlayerData.getTeam();

            if (teamm == TeamEnum.TERRORISTS) {
                team = "Terrorist";
            } else if (teamm == TeamEnum.COUNTER_TERRORISTS) {
                team = "Counter-Terrorist";
            }

            respawnPlayer(player, gameId, team);

        }

        startGameShop(gameId);

    }

    private void endGamePlayer(int gameId, TeamEnum winningTeam) {

        GameData gameData = dataManager.getGameData(gameId);
        gameData.setGameState(GameState.ENDED);

        for(Player player : Bukkit.getOnlinePlayers()) {
            GamePlayerData gamePlayerData = dataManager.getGamePlayerData(player);

            if (!(gamePlayerData.getGame() == gameId)) continue;

            PlayerData playerData = dataManager.getPlayerData(player);


            player.getInventory().clear();

            //Adds the kills/deaths to the player
            playerData.addTotalDeaths(gamePlayerData.getIngameDeaths());
            playerData.addTotalKills(gamePlayerData.getIngameKills());

            leaveGame(player, true);

            //Checks if the player is in the winningteam
            if (gamePlayerData.getTeam() == winningTeam) {
                playerData.addWins();
                continue;
            }

            playerData.addLoses();

        }

        //Resets the game arena
        resetArena(gameId);
    }

    public void resetArena(int gameId) {
        GameData gameData = dataManager.getGameData(gameId);

        gameData.setGameState(GameState.LOBBY);
        gameData.setPlayercount(0);
        gameData.setGameRound(0);
        gameData.setWinsDefenders(0);
        gameData.setWinsTerrorist(0);
        gameData.setAmountCounterTerrorist(0);
        gameData.setAmountTerrorist(0);

        //TODO MAKEN DAT DE MAP OOK VOLLEDIG WORD GERESET (DROPS/MISS DAT DE BOM ER NOG STAAT ENZV)

    }

    public void respawnPlayer(Player player, int gameId, String team) {

        //TODO MEERERDE RESPAWN LOCATIES TOEVOEGEN

        FileConfiguration cfg = dataManager.getMapData();

        GameData gameData = dataManager.getGameData(gameId);
        String map = gameData.getMap();

        //Checks what the world is of the filled in arena
        String world = cfg.getString("Arenas." + map + ".World");

        //Checks the spawnpoint of the counterterrorist
        String stringSpawn = cfg.getString("Arenas." + map + ".Spawnpoints." + team);
        assert stringSpawn != null;
        String[] spawnData = stringSpawn.split("--");
        assert world != null;
        Location spawn = new Location(Bukkit.getWorld(world), Integer.parseInt(spawnData[0]), Integer.parseInt(spawnData[1]), Integer.parseInt(spawnData[2]));

        player.getInventory().clear();

        GamePlayerData gamePlayerData = dataManager.getGamePlayerData(player);
        Enum teamm = gamePlayerData.getTeam();
        String inv = null;

        if (teamm == TeamEnum.TERRORISTS) {
            inv = "CT-Standard-Inv";
        } else if (teamm == TeamEnum.COUNTER_TERRORISTS) {
            inv = "T-Standard-Inv";
        }

        setPlayerInventory.getItems(player, inv);

        player.setGameMode(GameMode.ADVENTURE);

        player.teleport(spawn);

    }

    public void runTeamMenuFunction(Player player, String function) {

        //TODO MAKEN DAT NAMEN GEHIDE WORDNE, DUS DAT ANDERE GEEN NAMEN VAN ANDERE ZIEN

        GamePlayerData gamePlayerData = dataManager.getGamePlayerData(player);
        GameData gameData = dataManager.getGameData(gamePlayerData.getGame());

        Enum team = gamePlayerData.getTeam();

        if(function.equals("Join-Team-CT")) {

            if(team == TeamEnum.COUNTER_TERRORISTS) {
                Message.sendToPlayer(player, "Je zit al in dit team.");
                return;
            }

            if(!(gameData.getAmountTerrorist() >= gameData.getAmountCounterTerrorist())) {
                Message.sendToPlayer(player, "Het team dat je probeert te joinen team is op dit moment te vol (Anders krijg je ongebalanceerde shit)");
                return;
            }


            if(team == TeamEnum.TERRORISTS) {
                gameData.removeAmountTerrorist();
            }

            gameData.addAmountCounterTerrorist();
            gamePlayerData.setTeam(TeamEnum.COUNTER_TERRORISTS);

            Message.sendToPlayer(player, "Je bent het Counter-Terrorist team gejoined.");

            return;
        }

        if(function.equals("Join-Team-T")) {


            if(team == TeamEnum.TERRORISTS) {
                Message.sendToPlayer(player, "Je zit al in dit team.");
                return;
            }

            if(!(gameData.getAmountCounterTerrorist() >= gameData.getAmountTerrorist())) {
                Message.sendToPlayer(player, "Het team dat je probeert te joinen team is op dit moment te vol (Anders krijg je ongebalanceerde shit)");
                return;
            }

            gameData.addAmountTerrorist();
            if(team == TeamEnum.COUNTER_TERRORISTS) {
                gameData.removeAmountCounterTerrorist();
            }
            gamePlayerData.setTeam(TeamEnum.TERRORISTS);
            Message.sendToPlayer(player, "Je bent het Terrorist team gejoined.");
        }
    }

    private void setRandomTeamOnJoin(GameData gameData, GamePlayerData gamePlayerData) {
        Enum teamEnum = TeamEnum.NONE;
        String team = null;

        int amountcounterterror = gameData.getAmountCounterTerrorist();
        int amountterrorist = gameData.getAmountTerrorist();

        if(amountterrorist > amountcounterterror) {
            teamEnum = TeamEnum.COUNTER_TERRORISTS;
            team = "CTerrorist";
        } else if (amountcounterterror > amountterrorist) {
            teamEnum = TeamEnum.TERRORISTS;
            team = "Terrorist";
        } else if (amountcounterterror == amountterrorist) {

            //Creates a 50/50 change to get into the CT Team or T team
            int random = getRandomInt(2); //will return a number between 0-1
            if (random == 0) {
                teamEnum = TeamEnum.TERRORISTS;
                team = "Terrorist";
            }else if (random == 1) {
                teamEnum = TeamEnum.COUNTER_TERRORISTS;
                team = "CTerrorist";
            }
        }

        if (team == null) return;

        if(team.equals("Terrorist")) {
            gameData.addAmountTerrorist();
        } else {
            gameData.addAmountCounterTerrorist();
        }

        gamePlayerData.setTeam(teamEnum);

    }


    public static Integer getRandomInt(Integer max) {
        Random ran = new Random();
        return ran.nextInt(max);
    }
}
