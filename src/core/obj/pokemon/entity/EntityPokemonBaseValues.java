package core.obj.pokemon.entity;

import java.util.EnumMap;

import core.enums.Stats;

@SuppressWarnings("serial")
public class EntityPokemonBaseValues extends EnumMap<Stats, Integer> {

	public EntityPokemonBaseValues() {
		super(Stats.class);
	}

}
