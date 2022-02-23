package core.obj.maps;

import java.io.File;
import java.util.HashMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import core.Log;
import core.files.XMLHandler;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Maps {

	private java.util.Map<String, Map> maps = new HashMap<>();
	
	
	
	public Map getMap(String name) throws Exception {
		if(maps.containsKey(name)) {
			return maps.get(name);
		}
		
		Log.log("Map request: " + name);
		
		File file = XMLHandler.getFile("maps", name);
		Document doc = new SAXReader().read(file);
		Element root = doc.getRootElement();
		
		Map map = new Map(root);
		maps.put(name, map);
		return map;
	}
}
