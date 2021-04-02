package xyz.haoteng.mcribbl.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import xyz.haoteng.mcribbl.commands.ColorCommand;


public class PlayerMoveListener implements Listener {
    private static int[] prevBlockLoc = new int[3];
    private static Material prevBlockMaterial;

    private static boolean enabled = false;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Player player = e.getPlayer();
        Block targetedBlock = player.getTargetBlock(null, 20);

        //If cursor is enabled and the targeted block is not air
        if (enabled && targetedBlock.getType() != Material.AIR) {

            //If there is a previous targeted block, restore the material back to its original form
            if (prevBlockMaterial != null) {
                Block prevBlock = Bukkit.getWorld("world").getBlockAt(prevBlockLoc[0], prevBlockLoc[1], prevBlockLoc[2]);
                prevBlock.setType(prevBlockMaterial);
            }

            //Save the current targetedBlock as a previousBlock to prepare for restoration once the cursor moves
            prevBlockMaterial = targetedBlock.getType();
            prevBlockLoc[0] = targetedBlock.getX();
            prevBlockLoc[1] = targetedBlock.getY();
            prevBlockLoc[2] = targetedBlock.getZ();

            //Set the targetedBlock as the selectedBlock as a preview
            targetedBlock.setType(ColorCommand.selectedColorBlock.getType());
        }
    }

    public static void reset(){
        prevBlockMaterial = null;
        prevBlockLoc[0] = 0;
        prevBlockLoc[1] = 0;
        prevBlockLoc[2] = 0;
    }

    public static void toggleEnabled(){
        enabled = !enabled;
    }
}
