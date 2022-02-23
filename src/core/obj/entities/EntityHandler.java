package core.obj.entities;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import core.Log;
import core.files.XMLHandler;
import core.obj.entities.overworld.OverworldEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EntityHandler {

	public OverworldEntity get(String resource) throws Exception {
		Log.log("Entity request: " + resource);
		String[] values = resource.split("-");
		String name = values[0];
		int variant = Integer.parseInt(values[1]);
		
		File file = XMLHandler.getFile("entities", name.toLowerCase());
		Document doc = new SAXReader().read(file);
		Element root = doc.getRootElement();
		
		List<Node> variants = root.selectNodes("variant");
		for(Node node : variants) {
			int nodeVariant = Integer.parseInt(node.valueOf("@id"));
			if(variant == nodeVariant)
				return new OverworldEntity(node);
		}
		
		return null;
	}
}
