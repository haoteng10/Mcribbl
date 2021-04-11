package xyz.haoteng.mcribbl;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import xyz.haoteng.mcribbl.models.Cuboid;

import java.util.ArrayList;

public class BonusMatch {

    private Player player;
    private Cuboid arena;
    private ArrayList mobs;

    public BonusMatch(Player player){
        this.player = player;
        mobs = new ArrayList<>();
    }

    public void start(){
        //Creates an unbreakable arena around the player
        arena = new Cuboid(new Location(Bukkit.getWorld("world_nether"), 0, 30, 0), new Location(Bukkit.getWorld("world_nether"), 25, 60, 25));
        arena.changeAllMaterial(Material.AIR, false);
        arena.changeEdgeMaterial(Material.BARRIER, true);

        //Floor Blueprint
        int[][] flr = new int[26][26];

        for (int row = 0; row < flr.length; row++){
            for (int col = 0; col < flr[row].length; col++){
                if (row == col || row == (flr.length-col-1)) {
                    flr[row][col] = 1;
                }
            }
        }

        System.out.println("==========");
        for (int[] row : flr){
            for (int val : row){
                System.out.print(val + " ");
            }
            System.out.println();
        }

        arena.changeSideMaterial(flr, Material.GLOWSTONE);

        //Teleport the player
        player.teleport(new Location(Bukkit.getWorld("world_nether"), 10, 30, 10));

        //Change the game mode of the player
        player.setGameMode(GameMode.SURVIVAL);

        //Spawns specific mobs
        Entity ghast = Bukkit.getWorld("world_nether").spawnEntity(player.getLocation(), EntityType.GHAST);
        mobs.add(ghast);

    }

    public void neutralizeMobs(){
        for (int i = 0; i < mobs.size(); i++){
            if (mobs.get(i) instanceof Ghast){
                ((Entity) mobs.get(i)).remove();
                Entity cow = (Entity) mobs.set(i, Bukkit.getWorld("world_nether").spawnEntity(player.getLocation(), EntityType.COW));
                System.out.println("Lovely cow: " + cow.getName());
            }

            if (mobs.get(i) instanceof Pig){
                ((Entity) mobs.get(i)).remove();
                Entity pig = (Entity) mobs.remove(i);
                System.out.println("Lovely pig: " + pig.getName());
                i--;
            }
        }
    }

}
