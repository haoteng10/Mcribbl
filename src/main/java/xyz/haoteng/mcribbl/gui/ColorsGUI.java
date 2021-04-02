package xyz.haoteng.mcribbl.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ColorsGUI implements InventoryHolder {
    private final Inventory inv;
    private static int page;

    private final ItemStack[] allWools = {
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
    };
    private final ItemStack[] allTerracottas = {
            new ItemStack(Material.WHITE_TERRACOTTA),
            new ItemStack(Material.ORANGE_TERRACOTTA),
            new ItemStack(Material.MAGENTA_TERRACOTTA),
            new ItemStack(Material.LIGHT_BLUE_TERRACOTTA),
            new ItemStack(Material.YELLOW_TERRACOTTA),
            new ItemStack(Material.LIME_TERRACOTTA),
            new ItemStack(Material.PINK_TERRACOTTA),
            new ItemStack(Material.GRAY_TERRACOTTA),
            new ItemStack(Material.LIGHT_GRAY_TERRACOTTA),
            new ItemStack(Material.CYAN_TERRACOTTA),
            new ItemStack(Material.PURPLE_TERRACOTTA),
            new ItemStack(Material.BLUE_TERRACOTTA),
            new ItemStack(Material.BROWN_TERRACOTTA),
            new ItemStack(Material.GREEN_TERRACOTTA),
            new ItemStack(Material.RED_TERRACOTTA),
            new ItemStack(Material.BLACK_TERRACOTTA)
    };

    public ColorsGUI(int page) {
        // Construct the Inventory
        inv = Bukkit.createInventory(this, 18, "Colors");

        this.page = page;

        //Menu Switches
        ItemStack left = null;
        ItemStack right = null;

        if (page <= 0) {
            left = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            right = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);

            ItemMeta leftMeta = left.getItemMeta();
            leftMeta.setDisplayName(ChatColor.RED + "PREVIOUS PAGE");
            left.setItemMeta(leftMeta);

            ItemMeta rightMeta = right.getItemMeta();
            rightMeta.setDisplayName(ChatColor.GREEN + "NEXT PAGE");
            right.setItemMeta(rightMeta);

            // Take all of the items and put it inside the inventory
            for (int i = 0; i < allWools.length; i++){
                inv.setItem(i, allWools[i]);
            }
        }
        if (page >= 1) {
            left = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            right = new ItemStack(Material.RED_STAINED_GLASS_PANE);

            ItemMeta leftMeta = left.getItemMeta();
            leftMeta.setDisplayName(ChatColor.GREEN + "PREVIOUS PAGE");
            left.setItemMeta(leftMeta);

            ItemMeta rightMeta = right.getItemMeta();
            rightMeta.setDisplayName(ChatColor.RED + "NEXT PAGE");
            right.setItemMeta(rightMeta);

            for (int i = 0; i < allTerracottas.length; i++){
                inv.setItem(i, allTerracottas[i]);
            }
        }

        inv.setItem(16, left);
        inv.setItem(17, right);
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
