package core.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PokedexPokemonStates {

	UNKNOWN(-1),
	FOUNDED(0),
	CAUGHT(1);
	
	
	private final int value;
	
	
	public boolean equals(PokedexPokemonStates state) {
		return value == state.value;
	}
	
	public boolean founded() {
		return equals(FOUNDED);
	}
	
	public boolean caught() {
		return equals(CAUGHT);
	}
}
