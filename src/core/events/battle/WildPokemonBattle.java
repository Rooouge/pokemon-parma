package core.events.battle;

import core.enums.TileMovements;
import core.obj.maps.wild.WildPokemonEvent;
import core.obj.pokemon.entity.EntityPokemon;
import core.obj.pokemon.pokedex.Pokedex;
import lombok.Getter;

@Getter
public class WildPokemonBattle extends BattleEvent {

	public static final String KEY = "WildPokemonBattle";
	
	private final WildPokemonEvent event;
	
	
	public WildPokemonBattle(WildPokemonEvent event, TileMovements tile) throws Exception {
		super(tile, new EntityPokemon(Pokedex.instance().baseData(event.getId())).level(event.getLevel()).gender(event.getGender()).shiny(event.isShiny()));
		this.event = event;
		
		int id = entityPokemon.getData().getBaseData().getId();
		int level = event.getLevel();
		
		entityPokemon.getData().getMoves().generateMovesForWildPokemon(id, level);
	}
	
	
}
