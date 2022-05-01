package core.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PokedexPokemonStates {

	UNKNOWN(-1),
	FOUND(0),
	CAUGHT(1);
	
	
	private final int value;
	
	
	public boolean equals(PokedexPokemonStates state) {
		return value == state.value;
	}
	
	public boolean found() {
		return equals(FOUND) || equals(CAUGHT);
	}
	
	public boolean caught() {
		return equals(CAUGHT);
	}
	
	
	public static PokedexPokemonStates getFromValue(int value) {
		switch (value) {
		case 0:
			return FOUND;
		case 1:
			return CAUGHT;
		default:
			return UNKNOWN;
		}
	}
	
	
	public static PokedexPokemonStates random() {
//		return getFromValue(new Random().nextInt(3) - 1);
		return CAUGHT;
	}
}
