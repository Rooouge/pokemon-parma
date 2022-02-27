package core.obj.maps.scripts;

import core.obj.maps.Map;
import core.obj.scripts.Script;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class MapScript extends Script {

	protected final Map map;
	protected final MapScriptTypes type;
	
	
	public MapScript(Map map, MapScriptTypes type) {
		super(null, null);
		this.map = map;
		this.type = type;
	}

}
