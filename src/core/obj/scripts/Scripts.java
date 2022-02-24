package core.obj.scripts;

import java.io.File;

import core.Log;
import core.files.FileHandler;
import core.obj.entities.overworld.OverworldEntity;
import core.obj.entities.overworld.OverworldEntityData;
import core.obj.maps.Map;
import core.obj.maps.entities.MapEntities;
import jutils.config.Config;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Scripts {

	public EntityScripts get(Map map, OverworldEntity entity, MapEntities entities) throws Exception {
		String mRegistryName = map.getData().getRegistryName();
		OverworldEntityData eData = entity.getData();
		String eName = eData.getName();
		int eVariant = eData.getVariant();
		String ext = Config.getValue("script-file-extension");
		
		String name = null;
		EntityScripts scripts = null;
		
		for(int state = 0; true; state++) {
			name = (mRegistryName + "/" + eName + "_" + eVariant).toLowerCase() + "__" + state;
			
			File file = getFile("scripts", name, ext);
			if(file == null)
				return scripts;
			
			if(state == 0)
				scripts = new EntityScripts(file);
			
			scripts.add(ScriptCompiler.compile(map, entity, entities, file));
			Log.log("[X] Founded script '" + name + "." + ext + "'");
		}
	}
	
	private File getFile(String dirName, String name, String ext) {
		File file = null;
		try {
			file = FileHandler.getFile(dirName, name, ext);
		} catch (Exception e) {
			Log.log("[ ] Script '" + name + "." + ext + "' not found");
		}
		
		return file;
	}
}
