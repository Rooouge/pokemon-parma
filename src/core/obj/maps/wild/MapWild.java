package core.obj.maps.wild;

import java.util.ArrayList;
import java.util.Random;

import org.dom4j.Node;

import core.Log;
import core.obj.maps.Map;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class MapWild extends ArrayList<WildPokemonEvent> {

	private final Map map;
	
	
	public MapWild(Map map, Node root) {
		this.map = map;
		
		if(root != null) {
			Log.log("Parsing wild events");
			for(Node wildNode : root.selectNodes("wild")) {
				int id = Integer.parseInt(wildNode.valueOf("@id"));
				int minLv = Integer.parseInt(wildNode.valueOf("@min"));
				int maxLv = Integer.parseInt(wildNode.valueOf("@max"));
				int chance = Integer.parseInt(wildNode.valueOf("@chance"));
				int bound = Integer.parseInt(wildNode.valueOf("@bound"));
				
				add(new WildPokemonEvent(id, minLv, maxLv, chance, bound));
				Log.log("[X] Founded wild event for Pokémon with id: " + id);
			}
		}
	}
	
	
	public WildPokemonEvent random() {
		return get(new Random().nextInt(size()));
	}
}
