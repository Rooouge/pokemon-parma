package core.gui.screen.content.battle.keyhandlers;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;

import core.enums.GameStates;
import core.enums.Types;
import core.events.ChangeStateKeyEvent;
import core.events.GlobalKeyEvent;
import core.events.KeyEventWithRunnable;
import core.events.KeyMap;
import core.files.SoundsHandler;
import core.gui.interfaces.OnKeyPressHandler;
import core.gui.interfaces.Painter;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.battle.painters.BattleFightOptionsPainter;
import core.gui.screen.content.battle.painters.BattleSetOrderPainter;
import core.gui.screen.content.battle.painters.elements.FightOptionsRect;
import core.obj.pokemon.moves.Move;
import core.obj.pokemon.moves.attack.SpecialAttackMove;
import lombok.Setter;

public class BattleFightOptionsKeyHandler extends OnKeyPressHandler<Battle> {

	private final KeyMap keyMap;
	@Setter
	private boolean flag;
	
	
	public BattleFightOptionsKeyHandler(Battle parent) {
		super(parent);
		
		Map<GameStates, List<Painter<Battle>>> map = parent.getPaintersListsMap();
		BattleFightOptionsPainter bfop = (BattleFightOptionsPainter) map.get(GameStates.BATTLE_FIGHT_OPTIONS).get(7);
		BattleSetOrderPainter bsop = (BattleSetOrderPainter) map.get(GameStates.BATTLE_SET_ORDER).get(7);
		FightOptionsRect rect = bfop.getFightOptionsRect();
		
		flag = false;
		
		keyMap = new KeyMap();
		keyMap.put(new KeyEventWithRunnable(KeyEvent.VK_DOWN, rect::down));
		keyMap.put(new KeyEventWithRunnable(KeyEvent.VK_UP, rect::up));
		keyMap.put(new KeyEventWithRunnable(KeyEvent.VK_LEFT, rect::left));
		keyMap.put(new KeyEventWithRunnable(KeyEvent.VK_RIGHT, rect::right));
		keyMap.put(new ChangeStateKeyEvent(KeyEvent.VK_ESCAPE, GameStates.BATTLE_FIGHT_OPTIONS, GameStates.BATTLE_OPTIONS).pressSound());
		keyMap.put(new ChangeStateKeyEvent(KeyEvent.VK_SPACE, GameStates.BATTLE_FIGHT_OPTIONS, GameStates.NONE).withExtra(() ->  {
			if(!flag) {
				SoundsHandler.playSound(SoundsHandler.PRESS);
				GameStates.set(GameStates.BATTLE_SET_ORDER);
//				Move move = rect.getSelectedMove();	
				Move move;
//				switch (rect.getSelected()) {
//				case 0:
//					move = new SpecialAttackMove("Absorb", Types.GRASS, 100, 25, 20);
//					break;
//				case 1:
//					move = new SpecialAttackMove("Acid", Types.POISON, 100, 30, 40);
//					break;
//				case 2:
//					move = new SpecialAttackMove("Acid Armor", Types.POISON, 100, 30, 40);
//					break;
//				case 3:
//					move = new SpecialAttackMove("Aeroblast", Types.FLYING, 95, 5, 100);
//					break;
//				default:
//					move = null;
//					break;
//				}
				move = new SpecialAttackMove("Aerial Ace", Types.FLYING, 100, 30, 30);
			
				bsop.setOrder(move, this);
				flag = true;
			}
		}));
		
		firstLoad = true;
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
