package core.obj.pokemon.pokedex;

import java.util.ArrayList;

import core.obj.pokemon.PokemonBaseData;
import jutils.global.Global;

@SuppressWarnings("serial")
public class Pokedex extends ArrayList<PokemonPokedex> {

	public static final String KEY = "pokedex";
	
	
	public static Pokedex instance() {
		return Global.get(KEY, Pokedex.class);
	}
	
	
	public PokemonBaseData baseData(int id) {
		return get(id).getData().getBaseData();
	}
	
}
