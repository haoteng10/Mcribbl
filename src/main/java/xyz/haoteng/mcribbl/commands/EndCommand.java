package xyz.haoteng.mcribbl.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;
import xyz.haoteng.mcribbl.listeners.PlayerMoveListener;
import xyz.haoteng.mcribbl.models.Rating;

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

            //Refresh the player's sidebar
            Rating playerRating = RatingCommand.getLatestPlayerRating(player); //Assuming the player is ending it, not someone else
            player.getScoreboard().getTeam("rating").setSuffix(ChatColor.BLUE + "" + playerRating.getTotalScore());
        }

        return true;
    }
}
