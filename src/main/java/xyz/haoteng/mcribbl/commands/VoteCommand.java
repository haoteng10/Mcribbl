package xyz.haoteng.mcribbl.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.haoteng.mcribbl.models.Score;

public class VoteCommand implements CommandExecutor {
    private static boolean enabled = false;
    public static Score currentPlayerScore;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "Invalid Usage. /vote [1-5]");
        }

        currentPlayerScore.addScore(Integer.parseInt(args[0]));
        player.sendMessage(currentPlayerScore.modeScore() + " <-- Mode");

        return true;
    }

    public static void initVoting(Player player){
        Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);
        for (Player iPlayer : players){
            iPlayer.sendMessage("Voting starts now! \n Please rank from 1 (worst) to 5 (best) \n /vote [1 to 5]");
        }
        currentPlayerScore = new Score(player);
    }

    public static void enable(){
        enabled = true;
    }
}
