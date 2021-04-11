package xyz.haoteng.mcribbl;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.haoteng.mcribbl.commands.*;
import xyz.haoteng.mcribbl.listeners.*;

public final class Mcribbl extends JavaPlugin {
    public static Mcribbl plugin;

    @Override
    public void onEnable() {
        plugin = this;

        // Initialize Commands
        this.getCommand("color").setExecutor(new ColorCommand());
        this.getCommand("start").setExecutor(new StartCommand());
        this.getCommand("vote").setExecutor(new VoteCommand());
        this.getCommand("end").setExecutor(new EndCommand());
        this.getCommand("rating").setExecutor(new RatingCommand());

        // Initialize Listeners
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new ArenaListener(), this);

        Bukkit.getPluginManager().registerEvents(new SidebarListener(), this);

        // Plugin startup logic
        System.out.println("Plugin enabled!");
    }

    @Override
    public void onDisable() {

        //Disable the voting command
        VoteCommand.enabled = false;

        // Plugin shutdown logic
        System.out.println("Plugin disabled!");
    }
}
