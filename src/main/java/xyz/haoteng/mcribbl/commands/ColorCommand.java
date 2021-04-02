package xyz.haoteng.mcribbl.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.haoteng.mcribbl.gui.ColorsGUI;

public class ColorCommand implements CommandExecutor {
    public static ItemStack selectedColorBlock = new ItemStack(Material.BLACK_WOOL);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        ColorsGUI colorsGUI = new ColorsGUI(0);
        player.openInventory(colorsGUI.getInventory());

        return false;
    }
}
