package xyz.haoteng.mcribbl.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;
import xyz.haoteng.mcribbl.listeners.PlayerMoveListener;
import xyz.haoteng.mcribbl.models.Rating;

import java.util.ArrayList;

public class EndCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //End the voting session
        VoteCommand.endVoting();

        //End the player cursor
        PlayerMoveListener.toggleEnabled();

        //If the commander is a player
        if (sender instanceof Player){
            Player player = (Player) sender;
            //Spawn some fireworks!!!
            for (int i = 0; i < 3; i++){
                Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
                FireworkMeta fwmeta = firework.getFireworkMeta();
                fwmeta.addEffects(FireworkEffect.builder().withColor(Color.TEAL).withColor(Color.GREEN).with(FireworkEffect.Type.STAR).withTrail().build());
                fwmeta.setPower(1);
                firework.setFireworkMeta(fwmeta);
            }

            //Refresh players' sidebar
            for (Player p : Bukkit.getOnlinePlayers()) {

                Scoreboard playerBoard = p.getScoreboard();

                Rating playerRating = RatingCommand.getLatestPlayerRating(p);
                playerBoard.getTeam("rating").setSuffix(playerRating.getTotalScore() + "");
                playerBoard.getTeam("all rating").setSuffix(RatingCommand.getTotalScore(p) + "");

                ArrayList<Player> topPlayers = RatingCommand.getTopPlayers();
                Team place1 = playerBoard.getTeam("place1");
                try {
                    place1.setSuffix(topPlayers.get(0).getName());
                } catch (Exception error){
                    place1.setSuffix("");
                }

                Team place2 = playerBoard.getTeam("place2");
                try {
                    place2.setSuffix(topPlayers.get(1).getName());
                } catch (Exception error) {
                    place2.setSuffix("");
                }

                Team place3 = playerBoard.getTeam("place3");
                try {
                    place3.setSuffix(topPlayers.get(2).getName());
                } catch (Exception error) {
                    place3.setSuffix("");
                }
            }
        }

        return true;
    }
}
