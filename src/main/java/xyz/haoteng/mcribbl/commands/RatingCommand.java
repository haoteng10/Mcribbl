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

        // Command that looks like this --> /rating player [player name]
        if (args.length == 2 && args[0].equals("player")) {
            if (getPlayerScoreFromList(args[1]) != null) {
                Score playerScore = getPlayerScoreFromList(args[1]);
                displayPlayerScore(playerScore, sender);
            } else {
                sendInvalidMessage(sender);
                return false;
            }
        }

        // Command that looks like this --> /rating player [player name] has [integer]
        if (args.length == 4 && args[0].equals("player") && args[2].equals("has")){
            int check;
            try {
                check = Integer.parseInt(args[3]);
            } catch (Exception e){
                Message invUsg = new Message();
                invUsg.setCommandTemplate("/rating player [player name] has [1-digit integer]");
                invUsg.displayError(sender, 1);
                return false;
            }

            if (getPlayerScoreFromList(args[1]) != null && check < 10 && check >= 0) {
                Score playerScore = getPlayerScoreFromList(args[1]);
                boolean hasTargetInt = playerScore.scoreHasCertainDigit(check);
                sender.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + check + ChatColor.RESET + ChatColor.GOLD + " is one of the digit(s) in the final score: " + ChatColor.DARK_AQUA + hasTargetInt);
            } else {
                Message invUsg = new Message();
                invUsg.setCommandTemplate("/rating player [player name] has [1-digit integer]");
                invUsg.displayError(sender, 1);
                return false;
            }
        }

        // Command that looks like this --> /rating
        if (args.length == 0){

            if (!(sender instanceof Player)) return false;

            Player player = (Player) sender;
            if (getPlayerScoreFromList(player.getName()) != null){
                displayPlayerScore(getPlayerScoreFromList(player.getName()), player);
            } else {
                sendInvalidMessage(player);
                return false;
            }
        }

        return true;
    }

    private void displayPlayerScore(Score playerScore, CommandSender sender){
        playerScore.printScore(sender);
    }

    public static void addPlayerScore(Score score){
        allScores.add(score);
    }

    public static Score getPlayerScoreFromList(String playerName){
        Score latestScore = null;
        for (Score score : allScores){
            if (score.getHolder().getName().equals(playerName)){
                latestScore = score;
            }
        }
        return latestScore;
    }

    private void sendInvalidMessage(CommandSender sender){
        Message invUsg = new Message();
        invUsg.setCommandTemplate("/rating player [player name]");
        invUsg.displayError(sender, 1);
    }
}
