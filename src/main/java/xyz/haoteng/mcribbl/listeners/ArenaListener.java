package xyz.haoteng.mcribbl.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import xyz.haoteng.mcribbl.Game;
import xyz.haoteng.mcribbl.Mcribbl;
import xyz.haoteng.mcribbl.commands.RatingCommand;
import xyz.haoteng.mcribbl.models.Rating;

public class ArenaListener implements Listener {
    Player player;

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        if (e.getEntity() instanceof Ghast && e.getEntity().getKiller() instanceof Player){
            player = e.getEntity().getKiller();

            player.sendMessage(ChatColor.GOLD + "You killed the " + ChatColor.BOLD + "ghast" + ChatColor.RESET + ChatColor.GOLD + "!");
            player.sendMessage(ChatColor.DARK_GREEN + "2x " + ChatColor.GREEN + "points from last round has been added to the scoreboard.");

            //Double the previous rating
            Rating latestRating = RatingCommand.getLatestPlayerRating(player);
            latestRating.doubleScore();

            //Refresh players' sidebar
            Game.refreshSidebar();

            //Play EXP Sound
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 10);

            //Neutralize mobs
            Game.playerBonus.neutralizeMobs();

            resumePlayer();
        }

        if (e.getEntity() instanceof Player){
            player = (Player) e.getEntity();

            player.sendMessage(ChatColor.GOLD + "You got killed by " + ChatColor.BOLD + "ghast" + ChatColor.RESET + ChatColor.GOLD + "!");
            player.sendMessage(ChatColor.GREEN + "No multiplier has been applied to the voting result.");

            //Refresh players' sidebar
            Game.refreshSidebar();

            //Play sad sound
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 1, 10);

            resumePlayer();
        }
    }

    private void resumePlayer(){
        //Teleport the player back & change game mode
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Mcribbl.plugin, new Runnable() {
            @Override
            public void run() {
                player.teleport(Game.getPlayerLocation(player));
                player.setGameMode(GameMode.CREATIVE);
                player.setFlying(true);
                player.stopSound(Sound.ENTITY_PLAYER_LEVELUP);
                player.stopSound(Sound.ENTITY_ENDER_DRAGON_DEATH);
            }
        }, 150);
    }

}
