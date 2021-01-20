package xyz.haoteng.mcribbl.models;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Score {
    private Player holder;
    private int totalScore;
    private int[] scoreCasts;
    private List<Integer> scoreCastsList;

    public Score(Player holder){
        this.holder = holder;
        totalScore = 0;
        scoreCastsList = new ArrayList<Integer>();
    }

    public void addScore(int score){
        scoreCastsList.add(score);
        totalScore += score;
    }

    public void convertListToArray(){
        scoreCasts = new int[scoreCastsList.size()];
        for (int i = 0; i < scoreCastsList.size(); i++){
            scoreCasts[i] = scoreCastsList.get(i);
        }
    }

    public int getTotalScore() {
        return totalScore;
    }

    public double averageScore(){
        convertListToArray();
        return (double) totalScore / scoreCasts.length;
    }

    /**
     * Precondition:
     * scoreCasts instance variable has a length of greater than 0.
     * Postcondition:
     * returns a value in the array
     * @return the mode of the scores in scoreCasts array
     */
    public int modeScore(){
        convertListToArray();

        int currentMode = scoreCasts[0];
        int currentModeOccurrences = 1;

        for (int i = 0; i < scoreCasts.length; i++){
            int selectedOccurrence = 0;
            for (int j = i; j < scoreCasts.length; j++){
                if (scoreCasts[j] == scoreCasts[i]) selectedOccurrence++;
            }
            if (selectedOccurrence > currentModeOccurrences) {
                currentMode = scoreCasts[i];
                currentModeOccurrences = selectedOccurrence;
            }
        }

        return currentMode;
    }

    public int maxCastScore(){
        convertListToArray();

        if (scoreCasts.length == 0) return -1;

        int currentMax = scoreCasts[0];
        for (int number : scoreCasts){
            if (number > currentMax) currentMax = number;
        }
        return currentMax;
    }

    public int minCastScore(){
        convertListToArray();

        if (scoreCasts.length == 0) return -1;

        int currentMin = scoreCasts[0];
        for (int number : scoreCasts){
            if (number < currentMin) currentMin = number;
        }
        return currentMin;
    }

    public Player getHolder() {
        return holder;
    }

    public void printScore(CommandSender sender){
        sender.sendMessage(String.valueOf(this));
    }

    @Override
    public String toString() {
        String playerNameString = ChatColor.GREEN + "Player: " + holder.getName();
        String averageScoreString = ChatColor.GOLD + "Current average score is " + ChatColor.BOLD + this.averageScore();
        String currentModeString = ChatColor.GOLD + "Current mode score is " + ChatColor.BOLD + this.modeScore();
        String totalScoreString = ChatColor.GOLD + "Total score is " + ChatColor.BOLD + this.getTotalScore();
        String minMaxScoreString = ChatColor.GOLD + "Min: " + this.minCastScore() + "Max: " + this.maxCastScore();

        return  playerNameString + "\n" + averageScoreString + "\n" + currentModeString + "\n" + minMaxScoreString + "\n" + totalScoreString;
    }
}
