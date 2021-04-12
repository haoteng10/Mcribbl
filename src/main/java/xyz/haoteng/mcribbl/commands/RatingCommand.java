package xyz.haoteng.mcribbl.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.haoteng.mcribbl.helpers.Message;
import xyz.haoteng.mcribbl.models.Rating;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class RatingCommand implements CommandExecutor {

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

    //Get the total score of ALL of the ratings of a player
    public static int getTotalScore(Player player){
        ArrayList<Rating> ratings = allPlayerRatings.get(player);
        int score = 0;
        for (Rating rating : ratings){
            score += rating.getTotalScore();
        }
        return score;
    }

    //Get the top 3 players using the total scores of all rounds
    public static ArrayList<Player> getTopPlayers(){
        ArrayList<Player> allPlayers = new ArrayList<>();
        ArrayList<Player> allPlayers2 = new ArrayList<>();

        for (Player i : allPlayerRatings.keySet()){
            allPlayers.add(i);
            allPlayers2.add(i);
        }

//        for (int i = 0; i < allPlayers.size(); i++){
//            int lessThanNo = 0;
//            for (int j = i + 1; j < allPlayers.size(); j++){
//                if (RatingCommand.getTotalScore(allPlayers.get(i)) < RatingCommand.getTotalScore(allPlayers.get(j))){
//                    lessThanNo++;
//                }
//            }
//
//            if (lessThanNo <= 2) {
//                topPlayers.add(allPlayers.get(i));
//            }
//        }

        int selectionExecutionCount = 0;
        int insertionExecutionCount = 0;

        //Selection Sort
        for (int i = 0; i < allPlayers.size(); i++){
            int currentMax = i;

            for (int j = i+1; j < allPlayers.size(); j++){
                if (RatingCommand.getTotalScore(allPlayers.get(currentMax)) < RatingCommand.getTotalScore(allPlayers.get(j))){
                    currentMax = j;
                }
                selectionExecutionCount++;
            }

            Player temp = allPlayers.get(i);
            allPlayers.set(i, allPlayers.get(currentMax));
            allPlayers.set(currentMax, temp);
        }

        //Insertion Sort
        for (int i = 1; i < allPlayers2.size(); i++){
            int j = i-1;
            Player p = allPlayers2.get(i);

            while(j >= 0 && RatingCommand.getTotalScore(allPlayers2.get(j)) > RatingCommand.getTotalScore(p)){
                allPlayers2.set(j+1, allPlayers2.get(j));
                j--;
                insertionExecutionCount++;
            }

            allPlayers2.set(j+1, p);
        }

        System.out.println("Selection Sort Execution Counts: " + selectionExecutionCount);
        System.out.println(allPlayers);

        System.out.println("Insertion Sort Execution Counts: " + insertionExecutionCount);
        System.out.println(allPlayers2);

        ArrayList<Player> topPlayers = new ArrayList<Player>();

        try {
            topPlayers.add(allPlayers.get(0));
        } catch (Exception e){
            System.out.println("Catch: " + e);
        }

        try {
            topPlayers.add(allPlayers.get(1));
        } catch (Exception e){
            System.out.println("Catch: " + e);
        }

        try {
            topPlayers.add(allPlayers.get(2));
        } catch (Exception e){
            System.out.println("Catch: " + e);
        }

        return topPlayers;
    }

    private void sendInvalidMessage(CommandSender sender){
        Message invUsg = new Message();
        invUsg.setCommandTemplate("/rating player [player name]");
        invUsg.displayError(sender, 1);
    }
}
