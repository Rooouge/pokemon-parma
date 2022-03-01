package core.gui.screen.content;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import core.enums.GameStates;
import core.gui.interfaces.Painter;
import core.gui.screen.content.pokedex.painters.PokedexPainter;

@SuppressWarnings("serial")
public class Pokedex extends Content {

	private GameStates currentState;
	private final Map<GameStates, Painter<Pokedex>> painters;
	
	
	public Pokedex() throws IOException {
		currentState = GameStates.current();
		
		painters = new HashMap<>();
		painters.put(GameStates.POKEDEX, new PokedexPainter(this));
	}
	
	@Override
	protected void paintComponent(Graphics2D g) {
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
