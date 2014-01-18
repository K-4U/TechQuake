package es.amadornes.techquake.packet;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import es.amadornes.techquake.lib.ModInfo;
import es.amadornes.techquake.packet.CustomPacket.PacketType;
import es.amadornes.techquake.tileentity.electric.machine.TileEntityElectricFurnace;

/**
 * TechQuake
 * 
 * PacketHandler.java
 * 
 * @author amadornes (amadornes.es) and DavidJotta (davidjuan.es)
 * @license GNU GPL v2 (http://choosealicense.com/licenses/gpl-v2/)
 * @website techquake.amadornes.es
 */

public class PacketHandler implements IPacketHandler {
    
    @Override
    public void onPacketData(INetworkManager manager,
            Packet250CustomPayload packet, Player player) {
    
        DataInput data = new DataInputStream(new ByteArrayInputStream(
                packet.data));
        
        try {
            if(data.readUTF().equals(ModInfo.CHANNEL_NAME)){//If it's our channel, handle it
                PacketType t = PacketType.valueOf(data.readUTF());
                switch (t) {
                    case FURNACE_STATUS_CHANGE:
                        onFurnaceStatusChangePacket(data, (EntityPlayer) player);
                        break;
                }
            }
        } catch (Exception exception) {
        }
    }
    
    private void onFurnaceStatusChangePacket(DataInput data, EntityPlayer player) throws Exception {
    
        NBTTagCompound tag = readNBT(data);
        NBTTagCompound tile = tag.getCompoundTag("tile");
        World w = player.worldObj;
        if(w != null){
            System.out.println("Updating");
            TileEntityElectricFurnace te = (TileEntityElectricFurnace) w.getBlockTileEntity(tile.getInteger("x"), tile.getInteger("y"), tile.getInteger("z"));
            te.isBurning = tag.getBoolean("isBurning");
            System.out.println("OldAngle: " + te.lidAngle);
            te.lidAngle = tag.getDouble("angle");
            System.out.println("NewAngle: " + te.lidAngle);
        }
    }
    
    public static NBTTagCompound readNBT(DataInput data) {
    
        byte[] nbtBytes;
        try {
            nbtBytes = new byte[data.readShort()];
            data.readFully(nbtBytes);
            return CompressedStreamTools.decompress(nbtBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
