package core.obj.maps;

import org.dom4j.Node;

import core.obj.maps.links.Links;
import lombok.Getter;

@Getter
public class Map {

	private final MapData data;
	private final MapImages images;
	private final MapMovements movement;
	private final MapEntities entities;
	private final MapAutoTiles autoTiles;
	private MapLinks links;
	
	
	public Map(Node root) throws Exception {
		data = new MapData(this, root);
		images = new MapImages(data.getRegistryName());
		movement = new MapMovements(this, root.selectSingleNode("movements"));
		entities = new MapEntities(this, root.selectSingleNode("entities"));
		autoTiles = new MapAutoTiles(this, root.selectSingleNode("autotiles"));
	}
	
	
	public void initLinks() throws Exception {
		links = Links.getLinks(this);
	}
}
