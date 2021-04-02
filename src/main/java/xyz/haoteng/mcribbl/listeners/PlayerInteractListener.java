package xyz.haoteng.mcribbl.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import xyz.haoteng.mcribbl.commands.ColorCommand;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();

        //If the interact event is initialized with a stick
        if (e.getMaterial() == Material.STICK){
            e.setCancelled(true);

            //1. Get the target block
            //2. If the action is RIGHT_CLICK, set the target block to the desired block
            //3. Reset the cursor
            Block targetedBlock = player.getTargetBlock(null, 20);
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
                targetedBlock.setType(ColorCommand.selectedColorBlock.getType());
                PlayerMoveListener.reset();
            }
        }
    }
}
