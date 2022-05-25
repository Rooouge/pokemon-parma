package core.obj.pokemon.battle;

import core.obj.pokemon.entity.EntityPokemonData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BattlePokemonData {

	protected final EntityPokemonData entityData;
	
	
	public BattlePokemonData(EntityPokemonData entityData) {
		this.entityData = entityData;
	}
}
