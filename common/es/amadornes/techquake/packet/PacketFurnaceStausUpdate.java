package es.amadornes.techquake.packet;

import es.amadornes.techquake.tileentity.electric.machine.TileEntityElectricFurnace;

public class PacketFurnaceStausUpdate extends CustomPacket {
    
    public PacketFurnaceStausUpdate(TileEntityElectricFurnace te) {
        super(te.getTagStatus(), PacketType.FURNACE_STATUS_CHANGE);
    }
    
}
