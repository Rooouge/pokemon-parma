package core.obj.maps;

import org.dom4j.Node;

import core.obj.maps.autotiles.MapAutoTiles;
import core.obj.maps.entities.MapEntities;
import core.obj.maps.images.MapImages;
import core.obj.maps.links.Links;
import core.obj.maps.links.MapLinks;
import core.obj.maps.movements.MapMovements;
import core.obj.maps.scripts.MapScripts;
import core.obj.maps.tileentities.MapTileEntities;
import lombok.Getter;

@Getter
public class Map {

	private final MapData data;
	private final MapImages images;
	private final MapMovements movement;
	private final MapEntities entities;
	private final MapAutoTiles autoTiles;
	private final MapScripts scripts;
	private final MapTileEntities tileEntities;
	private MapLinks links;
	
	
	public Map(Node root) throws Exception {
		data = new MapData(this, root);
		images = new MapImages(data.getRegistryName());
		movement = new MapMovements(this, root.selectSingleNode("movements"));
		entities = new MapEntities(this, root.selectSingleNode("entities"));
		autoTiles = new MapAutoTiles(this, root.selectSingleNode("autotiles"));
		scripts = new MapScripts(this, root.selectSingleNode("scripts"));
		tileEntities = new MapTileEntities(this, root.selectSingleNode("scripts"));
	}
	
	
	public void initLinks() throws Exception {
		links = Links.getLinks(this);
	}
}
