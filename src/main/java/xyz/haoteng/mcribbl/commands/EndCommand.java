package xyz.haoteng.mcribbl.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.haoteng.mcribbl.Game;

public class EndCommand implements CommandExecutor {

    @Deprecated
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Game.end((Player) sender);

        return true;
    }
}
