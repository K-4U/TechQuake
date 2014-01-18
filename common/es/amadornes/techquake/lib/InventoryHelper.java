package es.amadornes.techquake.lib;

import net.minecraft.item.ItemStack;

public class InventoryHelper {
    
    public static boolean canAdd(ItemStack item, ItemStack added) {
    
        if (added != null) {
            if (item == null) return true;
            
            if (item.isItemEqual(added)
                    && ItemStack.areItemStackTagsEqual(item, added)) {
                if (item.stackSize + added.stackSize <= item.getMaxStackSize()) { return true; }
            }
        }
        
        return false;
    }
    
    public static boolean canAdd(ItemStack[] items, ItemStack added) {
    
        if (added != null) {
            
            int left = added.stackSize;
            
            for (int i = 0; i < items.length; i++) {
                if (items[i] == null) { return true; }
                
                if (items[i].isItemEqual(added)
                        && ItemStack.areItemStackTagsEqual(items[i], added)) {
                    int a = left;
                    if (items[i].stackSize + a <= items[i].getMaxStackSize()) {
                        left -= a;
                    }
                }
                
                if (left <= 0) return true;
            }
        }
        
        return false;
    }
    
    public static ItemStack add(ItemStack item, ItemStack added) {
    
        if (canAdd(item, added)) {
            if (item == null) return added;
            
            ItemStack is = item.copy();
            is.stackSize += added.stackSize;
            return is;
        }
        return null;
    }
    
    public static ItemStack[] add(ItemStack[] items, ItemStack added) {
    
        //int alreadyAdded = 0;
        
        if (canAdd(items, added)) {
            /*
             * for (int i = 0; i < items.length; i++) { if (alreadyAdded >=
             * added.stackSize){ //System.out.println("Slot " + i +
             * ": Already added everything. [" + alreadyAdded + "/" +
             * added.stackSize + "]"); break; } if (items[i] == null) { items[i]
             * = added; items[i].stackSize -= alreadyAdded;
             * //System.out.println("Slot " + i + ": Null, adding " +
             * items[i].stackSize); break; } int a = Math.min(added.stackSize,
             * added.getMaxStackSize() - added.stackSize);
             * //System.out.println("Slot " + i +
             * ": Contained something, adding " + a); items[i].stackSize += a;
             * alreadyAdded += a; }
             */
            
            int left = added.stackSize;
            
            for (int i = 0; i < items.length; i++) {
                if (items[i] == null) {
                    items[i] = added;
                    items[i].stackSize = left;
                    break;
                }
                
                if (items[i].isItemEqual(added)
                        && ItemStack.areItemStackTagsEqual(items[i], added)) {
                    int a = left;
                    if (items[i].stackSize + a <= items[i].getMaxStackSize()) {
                        left -= a;
                        items[i].stackSize += a;
                    }
                }
                
                if (left <= 0) break;
            }
        }
        return items;
    }
    
    public static ItemStack consume(ItemStack item, int amount) {
    
        item.stackSize -= amount;
        if (item.stackSize > 0) return item;
        return null;
    }
    
}
