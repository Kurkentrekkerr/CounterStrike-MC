package dev.idw0309.counterstrike.commands;

import dev.idw0309.counterstrike.message.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {
    
    /**
     * Main command of the plugin
     * @return true if the plugin detects something what isnt right, it will return true and stop the process.
     */
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Message.sendToConsole("Your not a player....");
			return true;
		}
		Player player = (Player) sender;
		if (args.length == 0) {
			Message.sendToPlayer(player, "Vul een subcommand in!");
		}
		if (args.length > 0) {
			  SubCommandManager.SubCommandState executionState = SubCommandManager.execute(sender, args);
			  switch (executionState) {
			    case NOT_FOUND:
			    	Message.sendToPlayer(player, "Dit subcommand bestaat niet!");
			    	break;
			    case NO_PERMISSION: 
			    	Message.sendToPlayer(player, "Je hebt niet de juiste permissies");
			      	break;
			    case INVALID_ARG_LENGTH: 
			    	Message.sendToPlayer(player, "Invalid arg length!");
			    	break;
			    default:
			  }
			}
	     
	     return true;
    }



}
