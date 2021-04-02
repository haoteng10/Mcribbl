package xyz.haoteng.mcribbl.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class ColorsGUI implements InventoryHolder {
    private final Inventory inv;

    public ColorsGUI() {
        // Construct the Inventory
        inv = Bukkit.createInventory(this, 18, "Colors");
        // Put the items into the inventory
        ItemStack[] allWools = {
                new ItemStack(Material.WHITE_WOOL),
                new ItemStack(Material.BLACK_WOOL),
                new ItemStack(Material.BLUE_WOOL),
                new ItemStack(Material.BROWN_WOOL),
                new ItemStack(Material.CYAN_WOOL),
                new ItemStack(Material.GRAY_WOOL),
                new ItemStack(Material.GREEN_WOOL),
                new ItemStack(Material.LIGHT_BLUE_WOOL),
                new ItemStack(Material.LIGHT_GRAY_WOOL),
                new ItemStack(Material.LIME_WOOL),
                new ItemStack(Material.ORANGE_WOOL),
                new ItemStack(Material.PURPLE_WOOL),
                new ItemStack(Material.YELLOW_WOOL),
                new ItemStack(Material.PINK_WOOL),
                new ItemStack(Material.MAGENTA_WOOL),
                new ItemStack(Material.RED_WOOL),
                //Menu Switching
                new ItemStack(Material.RED_STAINED_GLASS),
                new ItemStack(Material.GREEN_STAINED_GLASS)
        };

        // Take all of the items and put it inside the inventory
        for (int i = 0; i < allWools.length; i++){
            inv.setItem(i, allWools[i]);
        }
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
