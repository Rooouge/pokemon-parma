package core.obj.maps.wild;

import java.util.ArrayList;
import java.util.Random;

import org.dom4j.Node;

import core.Log;
import core.enums.TileMovements;
import core.events.exploration.ExplorationBattleEventHandler;
import core.gui.GridPosition;
import core.obj.entities.player.Player;
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
				Log.log("[X] Founded wild event for Pokémon with id: " + id + " (Min: " + minLv + ", Max: " + maxLv + ")");
			}
		}
	}
	
	
	public void wildPokemonAttempt() {
		GridPosition playerPos = Player.instance().getOverworldEntity().getData().getPos();
		TileMovements tile = TileMovements.getFromValue(map.getMovement().getMovement(playerPos.row, playerPos.column));
		
		if(TileMovements.canSpawn(tile)) {
			WildPokemonEvent evt = map.getWild().random();
			
			if(evt.attempt())
				ExplorationBattleEventHandler.wildBattle(evt, tile);
		}
	}
	
	public WildPokemonEvent random() {
		return get(new Random().nextInt(size()));
	}
	
}
