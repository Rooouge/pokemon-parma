package core.obj.pokemon.containers;

import java.util.ArrayList;

import core.obj.pokemon.entity.EntityPokemon;
import lombok.RequiredArgsConstructor;

@SuppressWarnings("serial")
@RequiredArgsConstructor
public class PokemonContainer extends ArrayList<EntityPokemon> {

	private final int maxSize;
	
	
	@Override
	public boolean add(EntityPokemon e) {
		if(isFull())
			return false;
		
		return super.add(e);
	}
	
	public boolean isFull() {
		return size() >= maxSize;
	}
}
