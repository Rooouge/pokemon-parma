package core.obj.pokemon.pokedex;

import core.obj.pokemon.PokemonBaseData;
import lombok.Getter;

@Getter
public class PokemonPokedexData extends PokemonBaseData {

	private final int id; //Numer-1
	
	public PokemonPokedexData(String name, int id) {
		super(name);
		this.id = id;
	}

}
