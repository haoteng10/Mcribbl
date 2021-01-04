package xyz.haoteng.mcribbl.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import xyz.haoteng.mcribbl.commands.ColorCommand;
import xyz.haoteng.mcribbl.gui.ColorGUI;

public class InventoryListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if (item == null || item.getType() == null) return;

        if (e.getClickedInventory().getHolder() instanceof ColorGUI) {
            e.setCancelled(true);
            ColorCommand.selectedColorBlock = item;
            player.sendMessage(ChatColor.GOLD + "Awesome!");
        }

    }
}
