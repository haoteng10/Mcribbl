package xyz.haoteng.mcribbl;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.haoteng.mcribbl.commands.ColorCommand;
import xyz.haoteng.mcribbl.listeners.InventoryListener;
import xyz.haoteng.mcribbl.listeners.PlayerInteractListener;
import xyz.haoteng.mcribbl.listeners.PlayerMoveListener;

public final class Mcribbl extends JavaPlugin {

    @Override
    public void onEnable() {

        this.getCommand("color").setExecutor(new ColorCommand());
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);

        // Plugin startup logic
        System.out.println("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Plugin disabled!");
    }
}
