package es.amadornes.techquake.lib;

/**
 * TechQuake
 * 
 * ModInfo.java
 * 
 * @author amadornes (amadornes.es) and DavidJotta (davidjuan.es)
 * @license GNU GPL v2 (http://choosealicense.com/licenses/gpl-v2/)
 * @website techquake.amadornes.es
 */

public class ModInfo {
    
    /* General Mod related constants */
	public static final String MOD_ID = "techquake";
	public static final String NAME = "TechQuake";
	public static final String VERSION = "0.0.1b";
	
	/* Network related stuff */
	public static final String CHANNEL_NAME = MOD_ID;
	public static final String CLIENT_PROXY = "es.amadornes." + MOD_ID + ".proxy.ClientProxy";
	public static final String SERVER_PROXY = "es.amadornes." + MOD_ID + ".proxy.CommonProxy";
	
}
