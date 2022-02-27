package core.obj.scripts;

import java.io.File;

import core.Log;
import core.files.FileHandler;
import core.gui.GridPosition;
import core.obj.entities.overworld.OverworldEntity;
import core.obj.entities.overworld.OverworldEntityData;
import core.obj.maps.Map;
import core.obj.maps.entities.MapEntities;
import core.obj.maps.tileentities.TileEntity;
import core.obj.maps.tileentities.TileScripts;
import core.obj.scripts.statescripts.EntityScripts;
import jutils.config.Config;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Scripts {

	private final String ext = Config.getValue("script-file-extension");
	
	public EntityScripts getEntityScripts(Map map, OverworldEntity entity, MapEntities entities) throws Exception {
		String mRegistryName = map.getData().getRegistryName();
		OverworldEntityData eData = entity.getData();
		String eName = eData.getName();
		int eVariant = eData.getVariant();
		String stateFileName = (mRegistryName + "/" + eName + "_" + eVariant).toLowerCase();
		EntityScripts scripts = null;
		
		for(int state = 0; true; state++) {
			String scriptFileName = stateFileName + "__" + state;
			File file = getFile(scriptFileName, state == 0);
			
			if(file == null)
				return scripts;
			
			
			if(state == 0)
				scripts = new EntityScripts(new File(file.getParentFile(), file.getName().replace("__0", "") + ".state"));
			
			scripts.add(ScriptCompiler.compile(map, entity, entities, file));
			Log.log("[X] Founded entity script '" + scriptFileName + "." + ext + "'");
		}
	}
	
	public TileScripts getTileScripts(TileEntity te, Map map, GridPosition pos, MapEntities entities) throws Exception {
		String mRegistryName = map.getData().getRegistryName();
		String tilePos = pos.row + "_" + pos.column;
		String stateFileName = (mRegistryName + "/tiles/" + tilePos).toLowerCase();
		TileScripts scripts = null;
		
		for(int state = 0; true; state++) {
			String scriptFileName = stateFileName + "__" + state;
			File file = getFile(scriptFileName, state == 0);
			
			if(file == null)
				return scripts;
			
			if(state == 0)
				scripts = new TileScripts(new File(file.getParentFile(), file.getName().replace("__0", "") + ".state"));
			
			scripts.add(ScriptCompiler.compile(map, te, entities, file));
			Log.log("[X] Founded tile script '" + scriptFileName + "." + ext + "'");
		}
	}
	
	
	private File getFile(String name, boolean toLog) {
		File file = null;
		try {
			file = FileHandler.getFile("scripts", name, ext);
		} catch (Exception e) {
			if(toLog)
				Log.log("[ ] Script '" + name + "." + ext + "' not found");
		}
		
		return file;
	}
}
