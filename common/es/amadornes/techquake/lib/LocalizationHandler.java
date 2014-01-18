package es.amadornes.techquake.lib;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.LanguageRegistry;

/**
 * TechQuake
 * 
 * LocalizationHandler.java
 * 
 * @author amadornes (amadornes.es) and DavidJotta (davidjuan.es)
 * @license GNU GPL v2 (http://choosealicense.com/licenses/gpl-v2/)
 * @website techquake.amadornes.es
 */

public class LocalizationHandler {
	
	public static String LANG_FOLDER_LOCATION = "/assets/" + ModInfo.MOD_ID + "/lang/";

	public static void loadLanguages(){
		for(String lFile : getLanguageFiles()){
			LanguageRegistry.instance().loadLocalization(lFile, LocalizationHelper.getLocale(lFile), LocalizationHelper.isXML(lFile));
		}
	}
	
	public static List<String> getLanguageFiles(){
		List<String> l = new ArrayList<String>();
		
		l.add(LANG_FOLDER_LOCATION + "en_US.xml");
		
		return l;
	}
	
	public static class LocalizationHelper{
		
		public static boolean isXML(String file){
			return file.toLowerCase().endsWith(".xml");
		}
		
		public static String getLocale(String file){
			return file.substring(file.lastIndexOf('/') + 1, file.lastIndexOf('.'));
		}
		
		public static String localize(String key){
			return LanguageRegistry.instance().getStringLocalization(key);
		}
		
	}
	
}