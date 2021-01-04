package xyz.haoteng.mcribbl.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Player player = e.getPlayer();
        Block targetedBlock = player.getTargetBlock(null, 5);
        if (targetedBlock.getType() != Material.AIR) {
            targetedBlock.setType(Material.DIAMOND_BLOCK);
        }
    }
}
