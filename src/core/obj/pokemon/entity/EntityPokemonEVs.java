package core.obj.pokemon.entity;

import java.util.EnumMap;
import java.util.Random;

import core.enums.Stats;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class EntityPokemonEVs extends EnumMap<Stats, Integer> {

	protected int total;
	
	
	protected EntityPokemonEVs() {
		super(Stats.class);
		total = 0;
	}
	
	
	public static EntityPokemonEVs generate() {
		Random random = new Random();
		
		EntityPokemonEVs map = new EntityPokemonEVs();
		map.put(Stats.ATK, random.nextInt(32));
		map.put(Stats.DEF, random.nextInt(32));
		map.put(Stats.SP_ATK, random.nextInt(32));
		map.put(Stats.SP_DEF, random.nextInt(32));
		map.put(Stats.SPEED, random.nextInt(32));
		map.put(Stats.TOT_HP, random.nextInt(32));
		
		return map;
	}
}
