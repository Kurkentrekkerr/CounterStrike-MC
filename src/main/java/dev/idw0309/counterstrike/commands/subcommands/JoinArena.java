package dev.idw0309.counterstrike.commands.subcommands;

import dev.idw0309.counterstrike.Counterstrike;
import dev.idw0309.counterstrike.commands.SubCommandManager;
import dev.idw0309.counterstrike.data.DataManager;
import dev.idw0309.counterstrike.data.game.GameData;
import dev.idw0309.counterstrike.game.Arena;
import dev.idw0309.counterstrike.message.Message;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SubCommandManager.SubCommand(name = "join", description = "Command to join a arena", usage = "join")
@RequiredArgsConstructor
public class JoinArena implements SubCommandManager.SubCommandExecutor {

    private final Arena arena;
    private final DataManager dataManager;
    final Counterstrike plugin = Counterstrike.getInstance();

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        //Checks if the person filled in the full command
        if(args.length == 0) {
            Message.sendToPlayer(player, "/counterstrike join <Arena id>");
            return;
        }

        //Checkt ofdat de ingevulde id wel een cijfer is ipv een woord.
        try{
            Integer.parseInt(args[0]);
        }catch(NumberFormatException e){
            Message.sendToPlayer(player, "Vul een getal in, geen woord of naam!");
            return;
        }

        int gameId = Integer.parseInt(args[0]);

        //Checks if the Arena exist, otherwise it will return and send a message to the player
        if(!(dataManager.arenas.containsKey(gameId))) {
            Message.sendToPlayer(player, "Deze arena bestaat niet!");
            return;
        }

        arena.joinArena(player, gameId);

    }
}
