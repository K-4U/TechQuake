package es.amadornes.techquake.api.pathfind;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import es.amadornes.techquake.api.electricity.IElectric;
import es.amadornes.techquake.api.electricity.IEnergyRemoteConductor;
import es.amadornes.techquake.api.loc.Vector3;


public class ElectricPathFinder {
    
    private IElectric from, to;
    
    private List<Path> paths = new ArrayList<Path>();
    
    private List<IElectric> excluded = new ArrayList<IElectric>();
    
    public ElectricPathFinder(IElectric from, IElectric to, List<IElectric> excluded) {
        this.from = from;
        this.to = to;
        if(excluded != null)
            this.excluded = excluded;
    }
    
    public ElectricPathFinder pathfind(){
        pathfind(from, new Path());
        return this;
    }
    
    private void pathfind(IElectric from, Path p){
        TileEntity tileEntity = (TileEntity) from;
        for(ForgeDirection d : from.getConnectableSides()){
            if(paths.size() == 0){
                TileEntity te = new Vector3(tileEntity).getRelative(d).getTileEntity();
                if(te instanceof IElectric){
                    IElectric e = (IElectric) te;
                    
                    if(!excluded.contains(e)){
                        if(e.canConnectOnSide(d.getOpposite())){
                            if(!p.containsStep(new Vector3(te))){
                                Path pa = p.clone();
                                pa.addStep(new Vector3(te));
                                if(e == to){
                                    paths.add(pa);
                                    return;
                                }
                                pathfind(e, pa);
                            }
                        }
                    }
                }
            }
        }
        if(from instanceof IEnergyRemoteConductor){
            IEnergyRemoteConductor c = (IEnergyRemoteConductor) from;
            for(IElectric e : c.getConnectedDevices()){
                if(!p.containsStep(new Vector3((TileEntity) e))){
                    Path pa = p.clone();
                    pa.addStep(new Vector3((TileEntity) e));
                    pathfind(e, pa);
                }
            }
        }
    }
    
    public boolean hasPath(){
        return paths.size() > 0;
    }
    
}
