package core.gui.screen.content;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import core.enums.GameStates;
import core.gui.interfaces.Painter;

@SuppressWarnings("serial")
public class Pokedex extends Content {

	private GameStates currentState;
	private final Map<GameStates, Painter<Pokedex>> painters;
	
	
	public Pokedex() {
		currentState = GameStates.current();
		
		painters = new HashMap<>();
	}
	
	@Override
	protected void paintComponent(Graphics2D g) {
		System.out.println("Paint");
		Painter<Pokedex> painter = painters.get(currentState);
		
		switch(currentState) {
		case POKEDEX:
			painter.paint(g);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void update() throws Exception {
		currentState = GameStates.current();
		
		super.update();
	}
}
