package es.amadornes.techquake.packet;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import es.amadornes.techquake.lib.ModInfo;

public abstract class CustomPacket extends Packet250CustomPayload {
    
    private NBTTagCompound tag;
    public PacketType      type;
    
    public CustomPacket(NBTTagCompound tag, PacketType type) {
    
        this.tag = tag;
        this.type = type;
        this.channel = ModInfo.CHANNEL_NAME;
        
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            DataOutput out = new DataOutputStream(os);
            
            writePacketData(out);
            
            this.data = os.toByteArray();
            this.length = data.length;
        } catch (IOException e) {
        }
    }
    
    public void readPacketData(DataInput in) throws IOException {
    
        this.channel = in.readUTF();
        
        this.type = PacketType.valueOf(in.readUTF());
        
        this.tag = readNBTTagCompound(in);
    }
    
    public void writePacketData(DataOutput out) throws IOException {
    
        out.writeUTF(this.channel);
        
        out.writeUTF(type.name());
        
        writeNBTTagCompound(this.tag, out);
    }
    
    public static enum PacketType {
        FURNACE_STATUS_CHANGE;
    }
    
    public int getPacketSize() {
    
        return 25;
    }
    
}
