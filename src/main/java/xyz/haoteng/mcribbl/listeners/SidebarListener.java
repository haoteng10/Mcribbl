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

public class SidebarListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        final Player player = e.getPlayer();

        RatingCommand.allPlayerRatings.put(player, new ArrayList<Rating>());

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("vote count", "player rating", ChatColor.GOLD + "Mcribbl Battle");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        ArrayList<Player> topPlayers = RatingCommand.getTopPlayers();

        //Line 7 (1st place)
        Team place1 = board.registerNewTeam("place1");
        place1.addEntry(ChatColor.GREEN.toString());
        place1.setPrefix(ChatColor.YELLOW + "1. " + ChatColor.GOLD);
        try {
            place1.setSuffix(topPlayers.get(0).getName());
        } catch (Exception error){
            place1.setSuffix("");
        }
        obj.getScore(ChatColor.GREEN.toString()).setScore(7);

        //Line 6 (2nd place)
        Team place2 = board.registerNewTeam("place2");
        place2.addEntry(ChatColor.GRAY.toString());
        place2.setPrefix(ChatColor.YELLOW + "2. " + ChatColor.GOLD);
        try {
            place2.setSuffix(topPlayers.get(1).getName());
        } catch (Exception error) {
            place2.setSuffix("");
        }
        obj.getScore(ChatColor.GRAY.toString()).setScore(6);

        //Line 5 (3rd place)
        Team place3 = board.registerNewTeam("place3");
        place3.addEntry(ChatColor.WHITE.toString());
        place3.setPrefix(ChatColor.YELLOW + "3. " + ChatColor.GOLD);
        try {
            place3.setSuffix(topPlayers.get(2).getName());
        } catch (Exception error) {
            place3.setSuffix("");
        }
        obj.getScore(ChatColor.WHITE.toString()).setScore(5);

        //Line 4
        Score lineBreak = obj.getScore("============");
        lineBreak.setScore(4);

        //Line 3
        Team currentRating = board.registerNewTeam("rating");
        currentRating.addEntry(ChatColor.BLACK.toString());
        currentRating.setPrefix(ChatColor.YELLOW + "Latest: ");

        Rating playerRating = RatingCommand.getLatestPlayerRating(player);
        String latestScore = "";

        if (playerRating != null) {
            latestScore += playerRating.getTotalScore();
        } else {
            latestScore += "0";
        }

        currentRating.setSuffix(latestScore);

        obj.getScore(ChatColor.BLACK.toString()).setScore(3);

        //Line 2
        Team allRatings = board.registerNewTeam("all rating");
        allRatings.addEntry(ChatColor.DARK_AQUA.toString());
        allRatings.setPrefix(ChatColor.YELLOW + "Total: ");
        allRatings.setSuffix(RatingCommand.getTotalScore(player) + "");
        obj.getScore(ChatColor.DARK_AQUA.toString()).setScore(2);

        //Line 1
        Score playerName = obj.getScore(ChatColor.LIGHT_PURPLE + "Player: " + ChatColor.AQUA + player.getName());
        playerName.setScore(1);

        player.setScoreboard(board);

    }

}
