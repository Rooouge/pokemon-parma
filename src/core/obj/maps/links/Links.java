package core.obj.maps.links;

import java.util.ArrayList;
import java.util.List;

import core.enums.Directions;
import core.obj.maps.Map;
import core.obj.maps.MapLinks;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Links {

	private List<Link> links;
	
	
	public void init() {
		links = new ArrayList<>();
		
		links.add(new Link("test_map", "test_map2", Directions.UP, 10));
	}
	
	
	public MapLinks getLinks(Map map) throws Exception {
		MapLinks mLinks = new MapLinks(map);
		String mapName = map.getData().getRegistryName();
		
		for(Link l : links) {
			if(l.isInLink(mapName)) {
				mLinks.add(l);
			}
		}
		
		mLinks.loadMaps();
		return mLinks;
	}
}
