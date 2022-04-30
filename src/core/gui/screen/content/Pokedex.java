package core.gui.screen.content;

import java.awt.Graphics2D;
import java.io.IOException;

import core.enums.GameStates;
import core.gui.interfaces.Painter;
import core.gui.screen.content.pokedex.keypresshandlers.PokedexKeyPressHandler;
import core.gui.screen.content.pokedex.painters.PokedexPainter;

@SuppressWarnings("serial")
public class Pokedex extends Content<Pokedex> {	
	
	public static final String TURN_ON = "pokédex-on";
	public static final String TURN_OFF = "pokédex-off";
	
	
	public Pokedex() throws IOException {
		super(false);
		currentState = GameStates.current();

		initPainters();
		initKeyHandlers();
	}
	
	
	@Override
	protected void initKeyHandlers() {
		keyHandler.add(new PokedexKeyPressHandler(this), GameStates.POKEDEX);
	}
	
	@Override
	protected void initPainters() throws IOException {
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
