package es.amadornes.techquake.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * TechQuake
 * 
 * TQBlock.java
 * 
 * @author amadornes (amadornes.es) and DavidJotta (davidjuan.es)
 * @license GNU GPL v2 (http://choosealicense.com/licenses/gpl-v2/)
 * @website techquake.amadornes.es
 */

public abstract class TQBlock extends Block {

    public TQBlock(int id, Material mat) {
        super(id, mat);
    }
    
}
