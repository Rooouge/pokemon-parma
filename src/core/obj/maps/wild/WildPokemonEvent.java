package core.obj.maps.wild;

import java.util.Random;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class WildPokemonEvent {

	
	private final int id;
	private final int minLevel;
	private final int maxLevel;
	private final int chance;
	private final int bound;
	private int level;
	private boolean shiny;
	
	
	public boolean attempt() {
		int value = new Random().nextInt(bound);
//		System.out.println("Chance: " + chance + ", Bound: " + bound + ", Value: " + value);
		return value < chance;
	}
	
	public WildPokemonEvent generate() {
		Random random = new Random();
		
		level = random.nextInt(maxLevel-minLevel) + minLevel;
		shiny = random.nextInt(8192) == 0;
		
		return this;
	}
	
	
	@Override
	public String toString() {
		return "Wild Pokémon with id: " + id + " at level: " + level + (shiny ? " (Shiny)" : "");
	}
}
