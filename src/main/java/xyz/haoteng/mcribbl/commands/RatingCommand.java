package xyz.haoteng.mcribbl.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.haoteng.mcribbl.helpers.Message;
import xyz.haoteng.mcribbl.models.Score;

import java.util.ArrayList;
import java.util.List;

public class RatingCommand implements CommandExecutor {

    private static List<Score> allScores = new ArrayList<Score>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // /rating player [player name]
        if (args.length == 2 && args[0].equals("player")){
            if (getPlayerScoreFromList(args[1]) != null) {
                displayPlayerScore(args[1], sender);
            } else {
                sendInvalidMessage(sender);
                return false;
            }
            // /rating
        } else if (args.length == 0){

            if (!(sender instanceof Player)) return false;

            Player player = (Player) sender;
            if (getPlayerScoreFromList(player.getName()) != null){
                displayPlayerScore(player.getName(), player);
            } else {
                sendInvalidMessage(player);
                return false;
            }
        }

        return true;
    }

    private void displayPlayerScore(String playerName, CommandSender sender){
        Score playerScore = getPlayerScoreFromList(playerName);
        playerScore.printScore(sender);
    }

    public static void addPlayerScore(Score score){
        allScores.add(score);
    }

    public static Score getPlayerScoreFromList(String playerName){
        for (Score score : allScores){
            if (score.getHolder().getName().equals(playerName)){
                return score;
            }
        }
        return null;
    }

    private void sendInvalidMessage(CommandSender sender){
        Message invUsg = new Message();
        invUsg.setCommandTemplate("/rating player [player name]");
        invUsg.displayError(sender, 1);
    }
}
