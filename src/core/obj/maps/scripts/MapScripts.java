package core.obj.maps.scripts;

import java.util.ArrayList;

import org.dom4j.Node;

import core.obj.maps.Map;
import core.obj.scripts.ScriptCompiler;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class MapScripts extends ArrayList<MapScript> {

	private final Map map;
	
	
	public MapScripts(Map map, Node root) throws Exception {
		this.map = map;
		
		if(root != null) {
			for(Node sNode : root.selectNodes("script")) {
				MapScriptTypes type = MapScriptTypes.getFromValue(sNode.valueOf("@type"));
				String value = sNode.getStringValue();
				add(ScriptCompiler.compile(map, type, value.split("\n")));
			}
		}
	}
	
	
	public MapScript getOnEnter() {
		for(MapScript s : this) {
			if(s.getType().equals(MapScriptTypes.EXEC_ON_ENTER))
				return s;
		}
		
		return null;
	}
}
