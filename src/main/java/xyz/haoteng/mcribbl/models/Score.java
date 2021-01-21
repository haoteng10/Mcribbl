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
        convertListToArray();
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
        return (double) totalScore / scoreCasts.length;
    }

    /**
     * Precondition: </br>
     * scoreCasts instance variable has a length of greater than 0. </br>
     * Postcondition: </br>
     * returns a value in the array
     * @return the mode of the scores in scoreCasts array
     */
    public int modeScore(){
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
        if (scoreCasts.length == 0) return -1;

        int currentMax = scoreCasts[0];
        for (int number : scoreCasts){
            if (number > currentMax) currentMax = number;
        }
        return currentMax;
    }

    public int minCastScore(){
        if (scoreCasts.length == 0) return -1;

        int currentMin = scoreCasts[0];
        for (int number : scoreCasts){
            if (number < currentMin) currentMin = number;
        }
        return currentMin;
    }

    public boolean anyEvenNumber(){
        if (scoreCasts.length == 0) return false;

        for (int number : scoreCasts){
            if (number % 2 == 0){
                return true;
            }
        }
        return false;
    }

    public boolean allEvenNumber(){
        if (scoreCasts.length == 0) return false;

        for (int number : scoreCasts){
            if (number % 2 != 0){
                return false;
            }
        }
        return true;
    }

    public int noOfEvenNumbers(){
        int count = 0;
        for (int number : scoreCasts){
            if (number % 2 == 0) count++;
        }
        return count;
    }

    public boolean scoreHasCertainDigit(int digit){
        int number = totalScore;
        while (number != 0){
            if (number % 10 == digit) return true;
            number /= 10;
        }
        return false;
    }

    public boolean hasSimilarCastScores(){
        for (int i = 0; i < scoreCasts.length; i++){
            for (int j = i+1; j < scoreCasts.length; j++){
                if (scoreCasts[i] == scoreCasts[j]) return true;
            }
        }
        return false;
    }

    public Player getHolder() {
        return holder;
    }

    public void printScore(CommandSender sender){
        try {
            sender.sendMessage(String.valueOf(this));
        } catch (Exception e){
            sender.sendMessage(ChatColor.GOLD + "No one casts a vote for you! :/");
        }
    }

    @Override
    public String toString() {
        String playerNameString = ChatColor.GREEN + "Player: " + holder.getName();
        String averageScoreString = ChatColor.GOLD + "Current average score is " + ChatColor.DARK_AQUA + this.averageScore();
        String currentModeString = ChatColor.GOLD + "Current mode score is " + ChatColor.DARK_AQUA + this.modeScore();
        String totalScoreString = ChatColor.GOLD + "Total score is " + ChatColor.DARK_AQUA + this.getTotalScore();
        String minMaxScoreString = ChatColor.GOLD + "Min: " + ChatColor.DARK_AQUA + this.minCastScore() + ChatColor.GOLD +" Max: " + ChatColor.DARK_AQUA + this.maxCastScore();
        String evenNumbers = ChatColor.GOLD + "No. of Even Numbers: " + ChatColor.DARK_AQUA + this.noOfEvenNumbers() + ChatColor.GOLD + " Any Even Numbers: " + ChatColor.DARK_AQUA + this.anyEvenNumber() + ChatColor.GOLD + "\nAll Even Numbers: " + ChatColor.DARK_AQUA + this.allEvenNumber();
        String similarCastScores = ChatColor.GOLD + "Has duplicating scores: " + ChatColor.DARK_AQUA + this.hasSimilarCastScores();

        return  playerNameString + "\n" +
                averageScoreString + "\n" +
                currentModeString + "\n" +
                minMaxScoreString + "\n" +
                totalScoreString + "\n" +
                evenNumbers + "\n" +
                similarCastScores;
    }
}
