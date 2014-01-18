package es.amadornes.techquake.api.electricity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import es.amadornes.techquake.api.loc.Vector3;
import es.amadornes.techquake.api.pathfind.ElectricPathFinder;

/**
 * TechQuake
 * 
 * EnergyNetwork.java
 * 
 * @author amadornes (amadornes.es) and DavidJotta (davidjuan.es)
 * @license GNU GPL v2 (http://choosealicense.com/licenses/gpl-v2/)
 * @website techquake.amadornes.es
 */

public class EnergyNetwork {
    
    public static EnergyNetwork getEnergyNetwork(World world, int x, int y,
            int z) {
    
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        
        if (tile != null) {
            if (tile instanceof IElectric) {
                EnergyNetwork net = null;
                
                Vector3 v = new Vector3(x, y, z, world);
                for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
                    Vector3 ve = v.getRelative(d);
                    TileEntity te = ve.getTileEntity();
                    if (te != null) {
                        if (te instanceof IElectric) {
                            IElectric e = (IElectric) te;
                            if (e.getEnergyNetwork() != null) {
                                if (net == null) {
                                    net = e.getEnergyNetwork();
                                } else {
                                    if (net != e.getEnergyNetwork()) {
                                        net.merge(e.getEnergyNetwork());
                                    }
                                }
                            }
                        }
                    }
                }
                
                if (net != null) {
                    net.devices.add((IElectric) tile);
                    return net;
                }
                
                return new EnergyNetwork((IElectric) tile);
            }
        }
        return null;
    }
    
    private EnergyNetwork() {
    
    }
    
    private List<IElectric> devices = new ArrayList<IElectric>();
    private IElectric       main    = null;
    
    private EnergyNetwork(IElectric tile) {
    
        devices.add(tile);
    }
    
    public void merge(EnergyNetwork net) {
    
        for (IElectric e : net.devices) {
            if (!devices.contains(e)) devices.add(e);
            e.setEnergyNetwork(this);
        }
        net.devices.clear();
        net.devices = null;
    }
    
    public void remove(IElectric tile) {
    
        if (devices.size() <= 1) {
            return;
        } else {
            devices.remove(tile);
        }
        
        boolean wasMain = false;
        
        if (main == tile) wasMain = true;
        if (wasMain) {
            main = null;
        }
        
        TileEntity tileEntity = ((TileEntity) tile);
        
        List<IElectric> excluded = new ArrayList<IElectric>();
        excluded.add(tile);
        
        int network = 0;
        IElectric last = null;
        for (ForgeDirection d : tile.getConnectableSides()) {
            TileEntity te = new Vector3(tileEntity).getRelative(d)
                    .getTileEntity();
            if (te instanceof IElectric) {
                IElectric e = (IElectric) te;
                if (e.canConnectOnSide(d.getOpposite())) {
                    if (network > 0) {
                        boolean hasPath = new ElectricPathFinder(last, e,
                                excluded).pathfind().hasPath();
                        System.out.println("Has Path: " + hasPath);
                        if (!hasPath) {
                            devices.remove(e);
                            e.setEnergyNetwork(new EnergyNetwork(e));
                            expandNetwork(e, excluded);
                        }
                    }
                    network++;
                    last = e;
                }
            }
        }
    }
    
    public void tick(IElectric tile) {
    
        if (main != null && tile != main) return;
        
        main = tile;
        tick();
    }
    
    private void tick() {
    
        /* Power transfer thingy */
        doPowerTransfer();
    }
    
    private void doPowerTransfer() {
    
        if (devices != null) {
            List<IEnergyEmitter> emitters = getProducers();
            List<IEnergyAcceptor> acceptors = getConsumers();
            
            int outputted = 0;
            for (IEnergyEmitter e : emitters) {
                int p = Math.max(e.drainEnergy(e.getMaxOutput(), false), 0);
                outputted += p;
            }
            int consumed = 0;
            for (IEnergyAcceptor e : acceptors) {
                int p = Math.max(e.insertEnergy(e.getMaxInput(), false), 0);
                consumed += p;
            }
            
            double rel = outputted;
            rel /= Math.max(consumed, 1);
            rel = Math.min(rel, 1);
            
            for (IEnergyAcceptor e : acceptors) {
                double inserted = e.insertEnergy(e.getMaxInput(), false);
                inserted *= rel;
                e.insertEnergy((int)inserted, true);
            }
        }
    }
    
    public List<IElectric> getDevices() {
    
        return devices;
    }
    
    public List<IEnergyAcceptor> getConsumers() {
    
        List<IEnergyAcceptor> l = new ArrayList<IEnergyAcceptor>();
        for (IElectric e : devices) {
            if (e instanceof IEnergyAcceptor) l.add((IEnergyAcceptor) e);
        }
        return l;
    }
    
    public List<IEnergyEmitter> getProducers() {
    
        List<IEnergyEmitter> l = new ArrayList<IEnergyEmitter>();
        for (IElectric e : devices) {
            if (e instanceof IEnergyEmitter) l.add((IEnergyEmitter) e);
        }
        return l;
    }
    
    public List<IEnergyConductor> getConductors() {
    
        List<IEnergyConductor> l = new ArrayList<IEnergyConductor>();
        for (IElectric e : devices) {
            if (e instanceof IEnergyConductor) l.add((IEnergyConductor) e);
        }
        return l;
    }
    
    public List<IEnergyBuffer> getBuffers() {
    
        List<IEnergyBuffer> l = new ArrayList<IEnergyBuffer>();
        for (IElectric e : devices) {
            if (e instanceof IEnergyBuffer) l.add((IEnergyBuffer) e);
        }
        return l;
    }
    
    public IElectric getMainTile() {
    
        return main;
    }
    
    private void expandNetwork(IElectric tile, List<IElectric> blacklist) {
    
        for (ForgeDirection d : tile.getConnectableSides()) {
            TileEntity te = new Vector3((TileEntity) tile).getRelative(d)
                    .getTileEntity();
            if (te != null) {
                if (te instanceof IElectric) {
                    IElectric e = (IElectric) te;
                    if (e.canConnectOnSide(d.getOpposite())) {
                        if (!blacklist.contains(e)) {
                            if (!tile.getEnergyNetwork().devices.contains(te)) {
                                e.getEnergyNetwork().devices.remove(e);
                                e.setEnergyNetwork(tile.getEnergyNetwork());
                                tile.getEnergyNetwork().devices.add(e);
                                expandNetwork(e, blacklist);
                            }
                        }
                    }
                }
            }
        }
    }
    
}
