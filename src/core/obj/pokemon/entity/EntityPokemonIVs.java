package core.obj.pokemon.entity;

import java.util.EnumMap;
import java.util.Random;

import core.enums.Stats;

@SuppressWarnings("serial")
public class EntityPokemonIVs extends EnumMap<Stats, Integer> {

	protected EntityPokemonIVs() {
		super(Stats.class);
	}
	
	
	public static EntityPokemonIVs generate() {
		Random random = new Random();
		
		EntityPokemonIVs map = new EntityPokemonIVs();
		map.put(Stats.ATK, random.nextInt(32));
		map.put(Stats.DEF, random.nextInt(32));
		map.put(Stats.SP_ATK, random.nextInt(32));
		map.put(Stats.SP_DEF, random.nextInt(32));
		map.put(Stats.SPEED, random.nextInt(32));
		map.put(Stats.TOT_HP, random.nextInt(32));
		
		return map;
	}
}
