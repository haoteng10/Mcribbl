package xyz.haoteng.mcribbl;

import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import xyz.haoteng.mcribbl.commands.RatingCommand;
import xyz.haoteng.mcribbl.commands.VoteCommand;
import xyz.haoteng.mcribbl.listeners.PlayerMoveListener;
import xyz.haoteng.mcribbl.models.Rating;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    private static BukkitTask task;

    private static HashMap<Player, Location> playerLocations = new HashMap<>();

    public static BonusMatch playerBonus;

    public static void end(Player player){
        //End the voting session
        VoteCommand.endVoting();

        //End the player cursor
        PlayerMoveListener.toggleEnabled();

        //Spawn some fireworks!!!
        task = new BukkitRunnable(){
            int seconds = 5;
            @Override
            public void run() {
                seconds--;

                Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
                FireworkMeta fwmeta = firework.getFireworkMeta();
                fwmeta.addEffects(FireworkEffect.builder().withColor(Color.TEAL).withColor(Color.GREEN).with(FireworkEffect.Type.STAR).withTrail().build());
                fwmeta.setPower(1);
                firework.setFireworkMeta(fwmeta);

                if (seconds == 0){
                    task.cancel();

                    player.sendTitle(ChatColor.GOLD + "Bonus Round" , ChatColor.BLUE + "Defeat the mob!", 4, 40, 4);
                }
            }
        }.runTaskTimer(Mcribbl.plugin, 0,20);

        //Bonus Match
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Mcribbl.plugin, new Runnable() {
            @Override
            public void run() {
                playerBonus = new BonusMatch(player);
                playerBonus.start();
            }
        }, 104);

    }

    public static void refreshSidebar(){
        //Refresh players' sidebar
        for (Player p : Bukkit.getOnlinePlayers()) {

            Scoreboard playerBoard = p.getScoreboard();

            Rating playerRating = RatingCommand.getLatestPlayerRating(p);

            playerBoard.getTeam("rating").setSuffix(playerRating.getTotalScore() + "");
            playerBoard.getTeam("all rating").setSuffix(RatingCommand.getTotalScore(p) + "");

            ArrayList<Player> topPlayers = RatingCommand.getTopPlayers();
            Team place1 = playerBoard.getTeam("place1");
            try {
                place1.setSuffix(topPlayers.get(0).getName());
            } catch (Exception error){
                place1.setSuffix("");
            }

            Team place2 = playerBoard.getTeam("place2");
            try {
                place2.setSuffix(topPlayers.get(1).getName());
            } catch (Exception error) {
                place2.setSuffix("");
            }

            Team place3 = playerBoard.getTeam("place3");
            try {
                place3.setSuffix(topPlayers.get(2).getName());
            } catch (Exception error) {
                place3.setSuffix("");
            }
        }
    }

    public static void addPlayerLocation(Player player, Location loc){
        playerLocations.put(player, loc);
    }

    public static Location getPlayerLocation(Player player){
        return playerLocations.get(player);
    }
}
