package test;

import core.Core;
import core.obj.pokemon.entity.EntityPokemon;
import core.obj.pokemon.pokedex.PokedexHandler;

public class Test {

	public static void main(String[] args) {
		try {
			Core.init();
			new EntityPokemon(PokedexHandler.get().baseData(1));
			Core.run();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
