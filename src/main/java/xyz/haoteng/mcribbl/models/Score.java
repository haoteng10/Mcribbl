package xyz.haoteng.mcribbl.models;

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
     * @custom.precondition
     * scoreCasts instance variable has a length of greater than or equal to 0.
     * @custom.postcondition
     * 0 <= return value <= scoreCasts.length.
     * @return the mode of the scores in scoreCasts array
     */
    public int modeScore(){
        convertListToArray();

        if (scoreCasts.length == 0) return 0;

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
}
