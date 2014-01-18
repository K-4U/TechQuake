package es.amadornes.techquake.api.pathfind;

import java.util.ArrayList;
import java.util.List;

import es.amadornes.techquake.api.loc.Vector3;


public class Path {
    
    private List<Vector3> steps = new ArrayList<Vector3>();
    
    public void addStep(Vector3 step){
        steps.add(step);
    }
    
    public boolean containsStep(Vector3 step){
        for(Vector3 v : steps)
            if(v.equals(step))
                return true;
        return false;
    }
    
    public List<Vector3> getSteps(){
        return steps;
    }
    
    @Override
    public Path clone() {
        Path p = new Path();
        for(Vector3 v : steps)
            p.addStep(v);
        return p;
    }
    
}
