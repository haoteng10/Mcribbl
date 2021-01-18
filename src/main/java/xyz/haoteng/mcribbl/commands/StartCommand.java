package xyz.haoteng.mcribbl.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.haoteng.mcribbl.listeners.PlayerMoveListener;
import xyz.haoteng.mcribbl.models.Rectangle;

public class StartCommand implements CommandExecutor {
    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            return false;
        }

        Player player = (Player) sender;

        Block[] cornerBlocks = getTwoCornerBlocks(player.getLocation(), 5, 10);
        Rectangle boardArea = new Rectangle(cornerBlocks[0].getLocation(), cornerBlocks[1].getLocation());
        boardArea.changeAllMaterial(Material.QUARTZ_BLOCK, false);

        PlayerMoveListener.toggleEnabled();

        return true;
    }

    private Block[] getTwoCornerBlocks(Location playerLocation, int zOffset, int size){
        Block bottomLeftCorner = playerLocation.getWorld().getBlockAt(playerLocation.getBlockX() - size, playerLocation.getBlockY() - size, playerLocation.getBlockZ() + zOffset);
        Block topRightCorner = playerLocation.getWorld().getBlockAt(playerLocation.getBlockX() + size, playerLocation.getBlockY() + size, playerLocation.getBlockZ() + zOffset);
        return new Block[]{bottomLeftCorner, topRightCorner};
    }
}
