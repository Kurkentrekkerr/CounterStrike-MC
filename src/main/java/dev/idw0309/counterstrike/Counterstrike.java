package dev.idw0309.counterstrike;

import dev.idw0309.counterstrike.commands.CommandManager;
import dev.idw0309.counterstrike.commands.SubCommandManager;
import dev.idw0309.counterstrike.commands.subcommands.*;
import dev.idw0309.counterstrike.data.DataManager;
import dev.idw0309.counterstrike.events.*;
import dev.idw0309.counterstrike.game.AddArena;
import dev.idw0309.counterstrike.game.Arena;
import dev.idw0309.counterstrike.inventory.PlayerInventorys;
import dev.idw0309.counterstrike.inventory.SetPlayerInventory;
import dev.idw0309.counterstrike.message.Message;
import dev.idw0309.counterstrike.runnables.GamePlayerCounter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public final class Counterstrike extends JavaPlugin {

    private static @Getter @Setter Counterstrike instance;

    private DataManager dataManager;
    private Arena arena;
    private AddArena addArena;
    private Message message;
    private PlayerInventorys playerInventorys;
    private SetPlayerInventory setPlayerInventory;

    @Override
    public void onEnable() {
        instance = this;

        dataManager = new DataManager();
        message = new Message(dataManager);
        setPlayerInventory = new SetPlayerInventory(dataManager);
        arena = new Arena(dataManager, message, setPlayerInventory);
        addArena = new AddArena(dataManager);
        playerInventorys = new PlayerInventorys();

        onStartup();

    }

    @Override
    public void onDisable(){
        //Kickt iedereen als persoon /rl doet. DEZE PLUGIN KAN NIET TEGEN /RL
        for (Player target : Bukkit.getServer().getOnlinePlayers()) {
            target.kickPlayer("Server reloading");
        }
    }

    private void Commands() {
        getCommand("counterstrike").setExecutor(new CommandManager());
        SubCommandManager.addSubCommand(new Help(playerInventorys));
        SubCommandManager.addSubCommand(new JoinArena(arena, dataManager));
        SubCommandManager.addSubCommand(new SetSpawnPoint());
        SubCommandManager.addSubCommand(new SetLobbySpawn());
        SubCommandManager.addSubCommand(new LoadArena(addArena, dataManager));
    }

    private void Events() {
        getServer().getPluginManager().registerEvents(new PlayerJoinQuitListener(dataManager, message), this);
        getServer().getPluginManager().registerEvents(new EntityDamageEvent(dataManager), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(arena), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(dataManager, arena, playerInventorys), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(dataManager), this);
    }
    private void onStartup() {

        //Hier alles in doen wat op startup moet opgestart worden

        saveResource("Config.yml", false);
        saveResource("Maps/Maps.yml", false);
        saveResource("Inventorys/Inv.yml", false);
        saveResource("Inventorys/GameInv.yml", false);
        saveResource("PlayerData/PlayerData.yml", false);
        saveResource("WeaponData/WeaponData.yml", false);

        addArena.autoLoadMap();

        Commands();
        Events();
    }
}
