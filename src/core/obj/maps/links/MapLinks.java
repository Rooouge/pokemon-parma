package core.obj.maps.links;

import java.util.ArrayList;
import java.util.List;

import core.enums.Directions;
import core.obj.maps.Map;
import core.obj.maps.Maps;
import lombok.Getter;

@SuppressWarnings("serial")
public class MapLinks extends ArrayList<Link> {
	
	private final Map map;
	@Getter
	private final List<Map> neighbors;
	
	
	public MapLinks(Map map) {
		this.map = map;
		neighbors = new ArrayList<>();
	}
	
	
	public void loadMaps() throws Exception {
		String regName = map.getData().getRegistryName();
		
		for(Link l : this) {
			if(l.isInLink(regName))
				neighbors.add(Maps.getMap(l.getNeighborName(regName)));
		}
	}
	
	
	public boolean hasLink(Directions dir) {
		for(Link l : this) {
			if(l.getDir(map.getData().getRegistryName()).equals(dir))
				return true;
		}
		
		return false;
	}
	
	public Link getLink(Directions dir) {
		for(Link l : this) {
			if(l.getDir(map.getData().getRegistryName()).equals(dir))
				return l;
		}
		
		return null;
	}
}
