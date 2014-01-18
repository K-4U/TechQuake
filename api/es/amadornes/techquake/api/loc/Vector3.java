package es.amadornes.techquake.api.loc;

import java.util.StringTokenizer;

import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class Vector3 {
    
    private double x, y, z;
    private World  w = null;
    
    public Vector3(double x, double y, double z) {
    
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector3(double x, double y, double z, World w) {
    
        this(x, y, z);
        this.w = w;
    }
    
    public Vector3(TileEntity te) {
    
        this(te.xCoord, te.yCoord, te.zCoord, te.worldObj);
    }
    
    public boolean hasWorld() {
    
        return w != null;
    }
    
    public Vector3 add(double x, double y, double z) {
    
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }
    
    public Vector3 add(ForgeDirection dir) {
    
        this.x += dir.offsetX;
        this.y += dir.offsetY;
        this.z += dir.offsetZ;
        return this;
    }
    
    public Vector3 subtract(double x, double y, double z) {
    
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }
    
    public Vector3 getRelative(double x, double y, double z) {
    
        return clone().add(x, y, z);
    }
    
    public Vector3 getRelative(ForgeDirection dir) {
    
        return getRelative(dir.offsetX, dir.offsetY, dir.offsetZ);
    }
    
    public Vector3 clone() {
    
        return new Vector3(x, y, z, w);
    }
    
    public boolean hasTileEntity() {
    
        if (hasWorld()) { return w
                .getBlockTileEntity((int) x, (int) y, (int) z) != null; }
        return false;
    }
    
    public TileEntity getTileEntity() {
    
        if (hasTileEntity()) { return w.getBlockTileEntity((int) x, (int) y,
                (int) z); }
        return null;
    }
    
    public boolean isBlock(Block b) {
    
        if (hasWorld()) {
            if (b == null && w.getBlockId((int) x, (int) y, (int) z) == 0) return true;
            return Block.blocksList[w.getBlockId((int) x, (int) y, (int) z)] == b;
        }
        return false;
    }
    
    public int getBlockId() {
    
        if (hasWorld()) { return w.getBlockId((int) x, (int) y, (int) z); }
        return -1;
    }
    
    public Block getBlock() {
    
        if (hasWorld()) {
            if (w.getBlockId((int) x, (int) y, (int) z) == 0) return null;
            return Block.blocksList[w.getBlockId((int) x, (int) y, (int) z)];
        }
        return null;
    }
    
    public World getWorld() {
    
        return w;
    }
    
    public double getX() {
    
        return x;
    }
    
    public double getY() {
    
        return y;
    }
    
    public double getZ() {
    
        return z;
    }
    
    public int getBlockX() {
    
        return (int) x;
    }
    
    public int getBlockY() {
    
        return (int) y;
    }
    
    public int getBlockZ() {
    
        return (int) z;
    }
    
    @Override
    public boolean equals(Object obj) {
    
        if (obj instanceof Vector3) {
            Vector3 vec = (Vector3) obj;
            return vec.w == w && vec.x == x && vec.y == y && vec.z == z;
        }
        return false;
    }
    
    public Vec3 toVec3() {
    
        return Vec3.createVectorHelper(x, y, z);
    }
    
    @Override
    public String toString() {
    
        String s = "Vector3{";
        if (hasWorld()) s += "w=" + w.provider.dimensionId + ";";
        s += "x=" + x + ";y=" + y + ";z=" + z + "}";
        return s;
    }
    
    public static Vector3 fromString(String s){
        if(s.startsWith("Vector3{") && s.endsWith("}")){
            World w = null;
            int x = 0, y = 0, z = 0;
            StringTokenizer st = new StringTokenizer(s.substring("Vector3{".length(), s.length() - "Vector3{".length() - 1), ";");
            while(st.hasMoreTokens()){
                String t = st.nextToken();
                if(t.toLowerCase().startsWith("x"))
                    x = Integer.parseInt(t.split("=")[1]);
                if(t.toLowerCase().startsWith("y"))
                    y = Integer.parseInt(t.split("=")[1]);
                if(t.toLowerCase().startsWith("z"))
                    z = Integer.parseInt(t.split("=")[1]);

                if(t.toLowerCase().startsWith("w")){
                    int world = Integer.parseInt(t.split("=")[1]);
                    for(World wo : MinecraftServer.getServer().worldServers){
                        if(wo.provider.dimensionId == world){
                            w = wo;
                            break;
                        }
                    }
                }
            }
            
            if(w != null){
                return new Vector3(x, y, z, w);
            }else{
                return new Vector3(x, y, z);
            }
        }
        return null;
    }
    
}
