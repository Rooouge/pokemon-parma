package core.gui.screen.content.pokedex.keypresshandlers;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import core.enums.GameStates;
import core.events.ChangeContentKeyEvent;
import core.events.ChangeStateKeyEvent;
import core.events.GlobalKeyEvent;
import core.gui.interfaces.OnKeyPressHandler;
import core.gui.screen.content.Exploration;
import core.gui.screen.content.Pokedex;
import core.gui.screen.content.pokedex.painters.PokedexPainter;

public class PokedexKeyPressHandler extends OnKeyPressHandler<Pokedex> {
	
	private final Map<Integer, GlobalKeyEvent> keyMap;
	
	
	public PokedexKeyPressHandler(Pokedex parent) {
		super(parent);
		
		PokedexPainter painter = (PokedexPainter) parent.getPainters().get(GameStates.POKEDEX);
		
		keyMap = new HashMap<>();
		keyMap.put(KeyEvent.VK_ESCAPE, new ChangeContentKeyEvent(KeyEvent.VK_ESCAPE, GameStates.POKEDEX, GameStates.EXPLORATION_START_MENU, Exploration.class));
		keyMap.put(KeyEvent.VK_DOWN, new PokedexArrowKeyEvent(KeyEvent.VK_DOWN, painter::scrollDown));
		keyMap.put(KeyEvent.VK_UP, new PokedexArrowKeyEvent(KeyEvent.VK_UP, painter::scrollUp));
		keyMap.put(KeyEvent.VK_LEFT, new PokedexArrowKeyEvent(KeyEvent.VK_LEFT, painter::fastUp));
		keyMap.put(KeyEvent.VK_RIGHT, new PokedexArrowKeyEvent(KeyEvent.VK_RIGHT, painter::fastDown));
	}
	

	@Override
	public void keyPressed(KeyEvent e) throws Exception {
		if(!pressed && !firstLoad) {
			if(keyMap.containsKey(e.getKeyCode()) && !keyMap.get(e.getKeyCode()).isActive()) {
				GlobalKeyEvent evt = keyMap.get(e.getKeyCode());
				
				if(evt.canActivate()) {
					evt.start();
					pressed = true;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(firstLoad)
			firstLoad = false;
		
		if(keyMap.containsKey(e.getKeyCode())) {
			if(!keyMap.get(e.getKeyCode()).isActive())
				pressed = false;
			else
				keyMap.get(e.getKeyCode()).end();
		}
	}

	@Override
	public void update() {
		if(pressed) {
			for(GlobalKeyEvent evt : keyMap.values()) {
				if(evt.isActive()) {
					evt.execute();
					pressed = false;
					if(evt instanceof ChangeStateKeyEvent) {
						evt.end();
					}
				}
			}
		}
	}

}
