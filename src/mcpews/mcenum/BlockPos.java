/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.mcenum;

/**
 *
 * @author Jocopa3
 */
public class BlockPos {
    int x, y, z;
    transient double xPos, yPos, zPos;
    
    public BlockPos(String x, String y, String z) {
        this.x = Integer.parseInt(x.replace("~", ""));
        xPos = Double.parseDouble(x.replace("~", ""));
        this.y = Integer.parseInt(y.replace("~", ""));
        yPos = Double.parseDouble(y.replace("~", ""));
        this.z = Integer.parseInt(z.replace("~", ""));
        zPos = Double.parseDouble(z.replace("~", ""));
    }
    
    public BlockPos(double x, double y, double z) {
        xPos = x;
        this.x = (int)Math.floor(x);
        yPos = y;
        this.y = (int)Math.floor(y);
        zPos = z;
        this.z = (int)Math.floor(z);
    }
    
    public double getX() {
        return xPos;
    }
    
    public double getY() {
        return yPos;
    }
    
    public double getZ() {
        return zPos;
    }
    
    public double distance(BlockPos pos) {
        return Math.sqrt(Math.pow(xPos - pos.xPos, 2.0) + Math.pow(yPos - pos.yPos, 2.0) + Math.pow(zPos - pos.zPos, 2.0));
    }
        
    @Override
    public String toString() {
        return "X: " + xPos + ", Y: " + yPos + ", Z: " + zPos;
    }
}
