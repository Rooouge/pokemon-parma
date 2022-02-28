package core.obj.pokemon.pokedex;

import java.util.ArrayList;

import core.obj.pokemon.PokemonBaseData;

@SuppressWarnings("serial")
public class Pokedex extends ArrayList<PokemonPokedex> {

	
	public PokemonBaseData baseData(int id) {
		return get(id).getData().getBaseData();
	}
	
}
