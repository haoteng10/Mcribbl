package xyz.haoteng.mcribbl.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class ColorsGUI implements InventoryHolder {
    private final Inventory inv;

    public ColorsGUI() {
        // Construct the Inventory
        inv = Bukkit.createInventory(this, 9, "Colors");
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
                new ItemStack(Material.LIGHT_GRAY_WOOL)
        };

        // Take all of the items and put it inside the Inventory
        for (int i = 0; i < allWools.length; i++){
            inv.setItem(i, allWools[i]);
        }
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
