package dev.idw0309.counterstrike.message;

import dev.idw0309.counterstrike.Counterstrike;
import dev.idw0309.counterstrike.data.DataManager;
import dev.idw0309.counterstrike.data.game.GamePlayerData;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class Message {
	
	final static String prefix2;
	final DataManager dataManager;
	final Counterstrike plugin = Counterstrike.getInstance();

	static {
		prefix2 = ChatColor.RED + ChatColor.BOLD.toString() + "CounterStrike " + ChatColor.WHITE;
	}
	
	public static void sendToConsole(String message) {
		Bukkit.getConsoleSender().sendMessage(prefix2 + message);
	}
	
	public static void sendToPlayer(CommandSender sender, String message) {
		sender.sendMessage(prefix2 + message);
	}

	public static void sendErrorToPlayer(Player pl, String message) {
		pl.sendMessage(prefix2 + message);
	}
	
	public static void sendHelpTextToPlayer(CommandSender sender, String message, int i, int j) {
		sender.sendMessage("§e------------- §c§lDDG-BOOTCAMP §e-------------");
		sender.sendMessage(ChatColor.WHITE + message);
		sender.sendMessage("§7Page " + i + "/" + j);
		sender.sendMessage("§e------------- §c§lDDG-BOOTCAMP §e-------------");
	}

	public void sendToIngamePlayers(String message, int gameId) {
		//delay
		new BukkitRunnable() {
			public void run() {
				for(Player player : Bukkit.getOnlinePlayers()){
					GamePlayerData gamePlayerData = dataManager.getGamePlayerData(player);

					if (gamePlayerData.getGame() == gameId) {
						player.sendMessage(prefix2 + message);
					}
				}
			}
		}.runTaskLater(plugin, 1);
	}

	public void sendActionbarTextToIngamePlayers(int gameId, String message) {
		for(Player player : Bukkit.getOnlinePlayers()){
			GamePlayerData gamePlayerData = dataManager.getGamePlayerData(player);

			if (gamePlayerData.getGame() == gameId) {
				player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
			}
		}
	}

	public void sendActionbarTextToPlayer(Player player, String message) {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
	}
}
