package xyz.haoteng.mcribbl.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.haoteng.mcribbl.commands.ColorCommand;
import xyz.haoteng.mcribbl.gui.ColorsGUI;

public class InventoryListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e){
        //Get the player that clicked something in the inventory
        Player player = (Player) e.getWhoClicked();
        //Get the item that has been clicked
        ItemStack item = e.getCurrentItem();

        if (item == null) return;

        //If the inventory belongs to ColorsGUI
        if (e.getClickedInventory().getHolder() instanceof ColorsGUI) {
            e.setCancelled(true);

            if (item.getType() == Material.GREEN_STAINED_GLASS_PANE) {
                if (e.getRawSlot() == 16) {
                    player.openInventory(new ColorsGUI(0).getInventory());
                    return;
                } else if (e.getRawSlot() == 17) {
                    player.openInventory(new ColorsGUI(1).getInventory());
                    return;
                }
            } else if (item.getType() == Material.RED_STAINED_GLASS_PANE){
                return;
            }

            ColorCommand.selectedColorBlock = item;
            player.sendMessage(ChatColor.GOLD + "Material Changed to " + ChatColor.AQUA + item.getType());
        }

    }
}
