package core.obj.pokemon.pokedex;

import org.dom4j.Node;

import core.enums.PokedexPokemonStates;
import core.obj.pokemon.PokemonBaseData;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PokemonPokedexData {
	
	protected final PokemonBaseData baseData;
	@Setter
	protected PokedexPokemonStates state;
	
	
	public PokemonPokedexData(Node root) {
		this(root, PokedexPokemonStates.UNKNOWN);
	}
	
	public PokemonPokedexData(Node root, PokedexPokemonStates state) {
		baseData = new PokemonBaseData(root);
		this.state = state;
	}

}
