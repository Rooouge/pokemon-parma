package core.events.battle;

import core.enums.TileMovements;
import core.obj.maps.wild.WildPokemonEvent;
import core.obj.pokemon.entity.EntityPokemon;
import core.obj.pokemon.pokedex.Pokedex;
import lombok.Getter;

@Getter
public class WildPokemonBattle extends BattleEvent {

	private final WildPokemonEvent event;
	private final EntityPokemon entityPokemon;
	
	
	public WildPokemonBattle(WildPokemonEvent event, TileMovements tile) throws Exception {
		super(tile);
		this.event = event;
		entityPokemon = new EntityPokemon(Pokedex.instance().baseData(event.getId())).level(event.getLevel()).gender(event.getGender()).shiny(event.isShiny());
	}
	
}
