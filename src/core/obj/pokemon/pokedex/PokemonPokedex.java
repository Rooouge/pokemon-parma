package core.obj.pokemon.pokedex;

import org.dom4j.Node;

import lombok.Getter;

@Getter
public class PokemonPokedex {

	private final PokemonPokedexData data;
	
	
	public PokemonPokedex(Node root) {
		data = new PokemonPokedexData(root);
	}
}
