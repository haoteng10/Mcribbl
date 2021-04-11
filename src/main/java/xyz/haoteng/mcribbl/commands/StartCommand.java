package xyz.haoteng.mcribbl.commands;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import xyz.haoteng.mcribbl.Game;
import xyz.haoteng.mcribbl.Mcribbl;
import xyz.haoteng.mcribbl.listeners.PlayerMoveListener;
import xyz.haoteng.mcribbl.models.Rectangle;

public class StartCommand implements CommandExecutor {

    private BukkitTask task;

    private static String[] prompts = {
            "Apple",
            "Watermelon",
            "Car",
            "Stick Figure",
            "School"
    };

    private Location playerLocation;

    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            return false;
        }

        Player player = (Player) sender;
        playerLocation = player.getLocation();

        //Get CornerBlocks & Setup Drawing Board
        Block[] cornerBlocks = getTwoCornerBlocks(player.getLocation(), 5, 16);
        Rectangle boardArea = new Rectangle(cornerBlocks[0].getLocation(), cornerBlocks[1].getLocation());
        boardArea.changeAllMaterial(Material.QUARTZ_BLOCK, false);

        //Setup Player Cursor
        PlayerMoveListener.toggleEnabled();

        //Start Voting Process
        VoteCommand.initVoting(player);

        //Give the player two prompts to choose from
        String[] twoPrompts = shuffleAndGetPrompts();
        player.sendMessage(ChatColor.GREEN + "Draw: " + ChatColor.GRAY + twoPrompts[0] + ChatColor.GOLD + " or " + ChatColor.GRAY + twoPrompts[1]);

        //Modify the player's inventory
        player.getInventory().clear();
        player.getInventory().setItem(0, new ItemStack(Material.STICK));
        player.getInventory().setItem(1, new ItemStack(Material.SNOWBALL));

        //Play music
        player.playEffect(playerLocation, Effect.RECORD_PLAY, Material.MUSIC_DISC_CHIRP);

        //Start the timer & initialize the Bossbar
        initBossbarAndTimer(48, player);

        return true;
    }

    private void initBossbarAndTimer(int ticks, Player player){
        BossBar bossBar = Bukkit.createBossBar(ChatColor.GOLD + "Countdown", BarColor.YELLOW, BarStyle.SEGMENTED_12);
        if (task == null){
            task = new BukkitRunnable(){
                int seconds = ticks / 4;
                @Override
                public void run() {
                    seconds -= 1;
                    if (seconds == 0) {
                        task.cancel();
                        bossBar.removeAll();

                        //End session
                        Game.endGame(player);

                        player.getWorld().playEffect(playerLocation, Effect.RECORD_PLAY, 0);
                    } else {
                        bossBar.setProgress(seconds / (double) 12);
                    }
                }
            }.runTaskTimer(Mcribbl.plugin, 0, ticks);
        }
        bossBar.setVisible(true);
        for (Player p : Bukkit.getOnlinePlayers()){
            bossBar.addPlayer(p);
        }
    }


    /**
     * @param playerLocation Location of the player
     * @param zOffset Blocks away from the player in the z direction
     * @param size The size of the board (symmetrical)
     * @return Block[] with block for the bottom-left corner and top-right corner
     */
    private Block[] getTwoCornerBlocks(Location playerLocation, int zOffset, int size){
        Block bottomLeftCorner = playerLocation.getWorld().getBlockAt(playerLocation.getBlockX() - size, playerLocation.getBlockY() - size, playerLocation.getBlockZ() + zOffset);
        Block topRightCorner = playerLocation.getWorld().getBlockAt(playerLocation.getBlockX() + size, playerLocation.getBlockY() + size, playerLocation.getBlockZ() + zOffset);
        return new Block[]{bottomLeftCorner, topRightCorner};
    }

    public String[] shuffleAndGetPrompts(){
        //Not real shuffle
        reversesPrompts();
        shiftPromptsLeft();
        String[][] pairs = getAllConsecutivePairs();
        int rand = (int)(Math.random() * pairs.length);
        return pairs[rand];
    }

    public static void shiftPromptsLeft(){
        String[] result = new String[prompts.length];
        for (int i = 0; i < prompts.length - 1; i++){
            result[i] = prompts[i+1];
        }
        result[prompts.length-1] = prompts[0];
        prompts = result;
    }

    public static void reversesPrompts(){
        String[] result = new String[prompts.length];
        int counter = 0;
        int reverseCounter = prompts.length-1;
        while (counter < prompts.length){
            result[counter] = prompts[reverseCounter];
            counter++;
            reverseCounter--;
        }
        prompts = result;
    }

    public static String[][] getAllConsecutivePairs(){
        String[][] result = new String[prompts.length-1][2];
        for (int i = 0; i < prompts.length-1; i++){
            result[i][0] = prompts[i];
            result[i][1] = prompts[i+1];
        }
        return result;
    }
}