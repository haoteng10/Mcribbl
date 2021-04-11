package xyz.haoteng.mcribbl.models;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Cuboid {
    private final int xMin;
    private final int xMax;
    private final int yMin;
    private final int yMax;
    private final int zMin;
    private final int zMax;

    private final World world;

    private Block[] blockArray;

    @Override
    public String toString() {
        return "Rectangle{" +
                "xMin=" + xMin +
                ", xMax=" + xMax +
                ", yMin=" + yMin +
                ", yMax=" + yMax +
                ", zMin=" + zMin +
                ", ZMax=" + zMax +
                ", world=" + world +
                ", totalBlocks=" + blockArray.length +
                '}';
    }

    public Cuboid(final Location loc1, final Location loc2){
        this.xMin = min(loc1.getBlockX(),loc2.getBlockX());
        this.xMax = max(loc1.getBlockX(),loc2.getBlockX());
        this.yMin = min(loc1.getBlockY(),loc2.getBlockY());
        this.yMax = max(loc1.getBlockY(),loc2.getBlockY());
        this.zMin = min(loc1.getBlockZ(), loc2.getBlockZ());
        this.zMax = max(loc1.getBlockZ(), loc2.getBlockZ());

        this.world = loc1.getWorld();

        this.blockArray = new Block[getTotalBlocks()];
        populateBlockArray();
    }

    private void populateBlockArray(){
        int blockCounter = 0; //Execution Count
        for (int x = xMin; x <= xMax; x++){
            for (int y = yMin; y <= yMax; y++){
                for (int z = zMin; z <= zMax; z++) {
                    final Block block = world.getBlockAt(x, y, z);
                    blockArray[blockCounter] = block;
                    blockCounter++;
                }
            }
        }
    }

    public int getHeight(){
        return yMax - yMin + 1;
    }

    public int getXWidth(){
        return xMax - xMin + 1;
    }

    private int getZWidth() {
        return zMax - zMin + 1;
    }

    public int getTotalBlocks(){
        return getHeight() * getXWidth() * getZWidth();
    }

    public void changeAllMaterial(Material targetMaterial, boolean applyPhysics){
        for (Block block : blockArray){
            block.setType(targetMaterial, applyPhysics);
        }
    }

    public void changeEdgeMaterial(Material targetMaterial, boolean applyPhysics){
        for (Block block : blockArray){
            Location blockLoc = block.getLocation();
            if (blockLoc.getBlockX() == xMin || blockLoc.getBlockX() == xMax || blockLoc.getBlockY() == yMin || blockLoc.getBlockY() == yMax || blockLoc.getBlockZ() == zMin || blockLoc.getBlockZ() == zMax){
                block.setType(targetMaterial, applyPhysics);
            }
        }
    }

    public boolean changeSideMaterial(int[][] blueprint, Material material) {
//        if (blueprint.length != getZWidth() || blueprint[0].length != getXWidth()) return false;

        System.out.println("A");

        for (int row = 0; row < blueprint.length; row++){
            for (int col = 0; col < blueprint[row].length; col++){
                if (blueprint[row][col] == 1) {
                    System.out.println("B");
                    blockArray[row*blueprint.length+col].setType(material);
                }
            }
        }

        return true;
    }

    private int min(int one, int two){
        if (one < two){
            return one;
        } else {
            return two;
        }
    }

    private int max(int one, int two){
        if (one > two){
            return one;
        } else {
            return two;
        }
    }

}
