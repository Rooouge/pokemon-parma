package core.obj.pokemon.containers;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Boxes {

	private List<Box> boxes;
	
	
	public void init() {
		int numOfBoxes = 14;
		boxes = new ArrayList<>();
		
		for(int i = 0; i < numOfBoxes; i++) {
			boxes.add(new Box(i));
		}
	}
	
	
	@SuppressWarnings("serial")
	@Getter
	private class Box extends PokemonContainer {

		private final int index;
		
		
		public Box(int index) {
			super(30);
			this.index = index;
		}
		
	}
}
