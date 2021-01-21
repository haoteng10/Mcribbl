package xyz.haoteng.mcribbl.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import xyz.haoteng.mcribbl.listeners.PlayerMoveListener;

public class EndCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        VoteCommand.endVoting();
        PlayerMoveListener.toggleEnabled();

        if (sender instanceof Player){
            Player player = (Player) sender;
            Location playerLocation = player.getLocation();
            for (int i = 0; i < 10; i++){
                Bukkit.getWorld("world").spawnEntity(playerLocation.add(new Vector(Math.random()-0.5, 0, Math.random()-0.5).multiply(2)), EntityType.FIREWORK);
            }
        }

        return true;
    }
}
