package xyz.haoteng.mcribbl.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;
import xyz.haoteng.mcribbl.commands.RatingCommand;
import xyz.haoteng.mcribbl.models.Rating;

import java.util.ArrayList;
import java.util.HashMap;

public class SidebarListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        final Player player = e.getPlayer();

        RatingCommand.allPlayerRatings.put(player, new ArrayList<Rating>());

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("vote count", "player rating", ChatColor.GOLD + "Mcribbl Battle");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        //Line 6 (1st place)
        Team place1 = board.registerNewTeam("place1");
        place1.addEntry(ChatColor.GREEN.toString());
        place1.setPrefix(ChatColor.YELLOW + "1. " + ChatColor.GOLD);
        place1.setSuffix(" " + ChatColor.DARK_AQUA + "0");
        obj.getScore(ChatColor.GREEN.toString()).setScore(6);

        //Line 5 (2nd place)
        Team place2 = board.registerNewTeam("place2");
        place2.addEntry(ChatColor.GRAY.toString());
        place2.setPrefix(ChatColor.YELLOW + "2. " + ChatColor.GOLD);
        place2.setSuffix(" " + ChatColor.DARK_AQUA + "0");
        obj.getScore(ChatColor.GRAY.toString()).setScore(5);

        //Line 4 (3rd place)
        Team place3 = board.registerNewTeam("place3");
        place3.addEntry(ChatColor.WHITE.toString());
        place3.setPrefix(ChatColor.YELLOW + "3. " + ChatColor.GOLD);
        place3.setSuffix(" " + ChatColor.DARK_AQUA + "0");
        obj.getScore(ChatColor.WHITE.toString()).setScore(4);

        //Line 3
        Score lineBreak = obj.getScore("=========");
        lineBreak.setScore(3);

        //Line 2
        Team currentRating = board.registerNewTeam("rating");
        currentRating.addEntry(ChatColor.BLACK.toString());
        currentRating.setPrefix(ChatColor.YELLOW + "Latest: ");

        Rating playerRating = RatingCommand.getLatestPlayerRating(player);
        if (playerRating != null) {
            currentRating.setSuffix(ChatColor.DARK_AQUA + "" + playerRating.getTotalScore());
        } else {
            currentRating.setSuffix(ChatColor.DARK_AQUA + "" + 0);
        }

        obj.getScore(ChatColor.BLACK.toString()).setScore(2);

        //Line 1
        Score playerName = obj.getScore(ChatColor.LIGHT_PURPLE + "Player: " + ChatColor.AQUA + player.getName());
        playerName.setScore(1);

        player.setScoreboard(board);

    }

}
