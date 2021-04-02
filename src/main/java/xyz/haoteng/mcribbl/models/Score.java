package xyz.haoteng.mcribbl.models;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Score {
    private Player holder;
    private int totalScore;
    private List<Integer> scoreCasts;

    public Score(Player holder){
        this.holder = holder;
        totalScore = 0;
        scoreCasts = new ArrayList<Integer>();
    }

    public void addScore(int score){
        scoreCasts.add(score);
        totalScore += score;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public double averageScore(){
        return (double) totalScore / scoreCasts.size();
    }

    /**
     * <p>
     * Precondition: </br>
     * scoreCasts instance variable has a length of greater than 0.
     * </p>
     * <p>
     * Postcondition: </br>
     * returns an integer value
     * </p>
     * @return the mode of the scores in scoreCasts array
     */
    public int modeScore(){
        int currentMode = scoreCasts.get(0);
        int currentModeOccurrences = 1;

        for (int i = 0; i < scoreCasts.size(); i++){
            int selectedOccurrence = 0;
            for (int j = i; j < scoreCasts.size(); j++){
                if (scoreCasts.get(j) == scoreCasts.get(i)) selectedOccurrence++;
            }
            if (selectedOccurrence > currentModeOccurrences) {
                currentMode = scoreCasts.get(i);
                currentModeOccurrences = selectedOccurrence;
            }
        }

        return currentMode;
    }

    public int maxCastScore(){
        if (scoreCasts.size() == 0) return -1;

        int currentMax = scoreCasts.get(0);
        for (int number : scoreCasts){
            if (number > currentMax) currentMax = number;
        }
        return currentMax;
    }

    public int minCastScore(){
        if (scoreCasts.size() == 0) return -1;

        int currentMin = scoreCasts.get(0);
        for (int number : scoreCasts){
            if (number < currentMin) currentMin = number;
        }
        return currentMin;
    }

    public boolean anyEvenNumber(){
        if (scoreCasts.size() == 0) return false;

        for (int number : scoreCasts){
            if (number % 2 == 0){
                return true;
            }
        }
        return false;
    }

    public boolean allEvenNumber(){
        if (scoreCasts.size() == 0) return false;

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
        for (int i = 0; i < scoreCasts.size(); i++){
            for (int j = i+1; j < scoreCasts.size(); j++){
                if (scoreCasts.get(i) == scoreCasts.get(j)) return true;
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
