package core.obj.pokemon.battle;

import core.obj.pokemon.entity.EntityPokemonData;
import lombok.Getter;

@Getter
public class BattlePokemon {

	protected final BattlePokemonData data;
	
	
	public BattlePokemon(EntityPokemonData entityData) {
		data = new BattlePokemonData(entityData);
	}
}
