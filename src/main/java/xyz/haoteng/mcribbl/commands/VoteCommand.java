package xyz.haoteng.mcribbl.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.haoteng.mcribbl.helpers.Message;
import xyz.haoteng.mcribbl.models.Score;

public class VoteCommand implements CommandExecutor {
    private static boolean enabled = false;
    public static Score currentPlayerScore;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        //Voting needs to be enabled to start
        if (!enabled){
            sendInvalidMessage(player);
            return false;
        }

        //Must follow command parameters
        if (args.length != 1) {
            sendInvalidMessage(player);
            return false;
        }

        int score;

        //Catching invalid inputs
        try {
            score = Integer.parseInt(args[0]);
            if (score < 1 || score > 5){
                sendInvalidMessage(player);
                return  false;
            }
        } catch (Exception e){
            sendInvalidMessage(player);
            return false;
        }

        //Add individual casts to the player score
        currentPlayerScore.addScore(score);

        //Send message
        player.sendMessage(ChatColor.GOLD + "You voted: "+ ChatColor.BOLD + score);

        return true;
    }

    public static void initVoting(Player player){
        enabled = true;

        //Notify all players that drawing & voting is starting
        Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);
        for (Player iPlayer : players){
            iPlayer.sendMessage(ChatColor.GOLD + "Voting starts now!" + ChatColor.AQUA + "\nPlease rank from 1 (worst) to 5 (best)" + ChatColor.DARK_AQUA + "\n/vote [1 to 5]");
        }

        currentPlayerScore = new Score(player);
    }

    public static void endVoting(){
        enabled = false;

        //Notify all players that drawing & voting is over
        Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);
        for (Player iPlayer : players){
            iPlayer.sendMessage(ChatColor.GOLD + "Voting ends!");
        }

        //Add the score to the global ratings
        RatingCommand.addPlayerScore(currentPlayerScore);

        //Reset the current player scoreboard
        currentPlayerScore = null;
    }

    private void sendInvalidMessage(Player player){
        Message invUsg = new Message();
        invUsg.setCommandTemplate("/vote [1-5]");
        invUsg.displayError(player, 1);
    }
}
