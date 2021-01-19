package xyz.haoteng.mcribbl.models;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Rectangle {
    private final int xMin;
    private final int xMax;
    private final int yMin;
    private final int yMax;

    private final int zValue;

    private final World world;

    private Block[] blockArray;

    @Override
    public String toString() {
        return "Rectangle{" +
                "xMin=" + xMin +
                ", xMax=" + xMax +
                ", yMin=" + yMin +
                ", yMax=" + yMax +
                ", zValue=" + zValue +
                ", world=" + world +
                ", totalBlocks=" + blockArray.length +
                '}';
    }

    public Rectangle(final Location loc1, final Location loc2){
        this.xMin = Math.min(loc1.getBlockX(),loc2.getBlockX());
        this.xMax = Math.max(loc1.getBlockX(),loc2.getBlockX());
        this.yMin = Math.min(loc1.getBlockY(),loc2.getBlockY());
        this.yMax = Math.max(loc1.getBlockY(),loc2.getBlockY());
        this.world = loc1.getWorld();
        this.zValue = loc1.getBlockZ();
        this.blockArray = new Block[getTotalBlocks()];
        populateBlockArray();
    }

    private void populateBlockArray(){
        int blockCounter = 0; //Execution Count
        for (int x = xMin; x <= xMax; x++){
            for (int y = yMin; y <= yMax; y++){
                final Block block = world.getBlockAt(x, y, zValue);
                blockArray[blockCounter] = block;
                blockCounter++;
            }
        }
    }

    public int getHeight(){
        return yMax - yMin + 1;
    }

    public int getXWidth(){
        return xMax - xMin + 1;
    }

    public int getTotalBlocks(){
        return getHeight() * getXWidth() * 1;
    }

    public void changeAllMaterial(Material targetMaterial, boolean applyPhysics){
        for (Block block : blockArray){
            block.setType(targetMaterial, applyPhysics);
        }
    }
}
