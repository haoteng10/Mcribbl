package xyz.haoteng.mcribbl.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.haoteng.mcribbl.helpers.Message;
import xyz.haoteng.mcribbl.models.Rating;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RatingCommand implements CommandExecutor {

//    private static List<Rating> allRatings = new ArrayList<Rating>();

    public static HashMap<Player, ArrayList<Rating>> allPlayerRatings = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Command that looks like this --> /rating player [player name]
        if (args.length == 2 && args[0].equals("player")) {
            if (getLatestPlayerRating(args[1]) != null) {
                Rating playerRating = getLatestPlayerRating(args[1]);
                displayPlayerScore(playerRating, sender);
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

            if (getLatestPlayerRating(args[1]) != null && check < 10 && check >= 0) {
                Rating playerRating = getLatestPlayerRating(args[1]);
                boolean hasTargetInt = playerRating.scoreHasCertainDigit(check);
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
            if (getLatestPlayerRating(player) != null){
                displayPlayerScore(getLatestPlayerRating(player), player);
            } else {
                sendInvalidMessage(player);
                return false;
            }
        }

        return true;
    }

    private void displayPlayerScore(Rating playerRating, CommandSender sender){
        playerRating.printScore(sender);
    }

    public static void addPlayerScore(Rating rating){
//        allRatings.add(rating);
        Player player = rating.getHolder();
        ArrayList<Rating> currentPlayerRatings = allPlayerRatings.get(player);
        currentPlayerRatings.add(rating);
        allPlayerRatings.put(player, currentPlayerRatings);
    }

    //Get the latest player rating from the hashmap of player ratings in an arraylist
    public static Rating getLatestPlayerRating(Player player){
        ArrayList<Rating> ratings = allPlayerRatings.get(player);
        if (ratings.size() <= 0) return null;
        return ratings.get(ratings.size()-1);
    }

    //Overload the method to allow using player names instead of player object
    public static Rating getLatestPlayerRating(String playerName){
        for (Player i : allPlayerRatings.keySet()){
            if (i.getName().equals(playerName)) {
                return getLatestPlayerRating(i);
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
