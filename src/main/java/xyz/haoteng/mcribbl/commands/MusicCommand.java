package xyz.haoteng.mcribbl.commands;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.swing.*;

public class MusicCommand implements CommandExecutor {

    private boolean toggle = false;
    private Location musicLoc;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (!toggle) {
                Player player = (Player) sender;
                musicLoc = player.getLocation();
                player.playEffect(musicLoc, Effect.RECORD_PLAY, Material.MUSIC_DISC_PIGSTEP);
            } else {
                musicLoc.getWorld().playEffect(musicLoc, Effect.RECORD_PLAY, 0);
            }
            toggle = !toggle;
            return true;
        }

        return false;
    }
}
