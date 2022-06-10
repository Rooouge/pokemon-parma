package core.gui.screen.content.battle.keyhandlers;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;

import core.enums.GameStates;
import core.enums.Types;
import core.events.ChangeStateKeyEvent;
import core.events.GlobalKeyEvent;
import core.events.KeyEventWithRunnable;
import core.events.battle.BattleEvent;
import core.events.battle.BattleMap;
import core.events.battle.WildPokemonBattle;
import core.files.SoundsHandler;
import core.gui.interfaces.OnKeyPressHandler;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.battle.animations.MoveAnimations;
import core.gui.screen.content.battle.painters.BattleFightOptionsPainter;
import core.gui.screen.content.battle.painters.BattlePlayerMoveAnimationPainter;
import core.gui.screen.content.battle.painters.elements.FightOptionsRect;
import core.obj.entities.player.Player;
import core.obj.pokemon.battle.BattlePokemon;
import core.obj.pokemon.moves.Move;
import core.obj.pokemon.moves.attack.SpecialAttackMove;

public class BattleFightOptionsKeyHandler extends OnKeyPressHandler<Battle> {

	private final HashMap<Integer, GlobalKeyEvent> keyMap;
	
	
	public BattleFightOptionsKeyHandler(Battle parent) {
		super(parent);
		
		BattleFightOptionsPainter bfop = (BattleFightOptionsPainter) parent.getPaintersListsMap().get(GameStates.BATTLE_FIGHT_OPTIONS).get(5);
		BattlePlayerMoveAnimationPainter bpmap = (BattlePlayerMoveAnimationPainter) parent.getPaintersListsMap().get(GameStates.BATTLE_PLAYER_MOVE).get(5);
		FightOptionsRect rect = bfop.getFightOptionsRect();
		
		keyMap = new HashMap<>();
		keyMap.put(KeyEvent.VK_DOWN, new KeyEventWithRunnable(KeyEvent.VK_DOWN, rect::down));
		keyMap.put(KeyEvent.VK_UP, new KeyEventWithRunnable(KeyEvent.VK_UP, rect::up));
		keyMap.put(KeyEvent.VK_LEFT, new KeyEventWithRunnable(KeyEvent.VK_LEFT, rect::left));
		keyMap.put(KeyEvent.VK_RIGHT, new KeyEventWithRunnable(KeyEvent.VK_RIGHT, rect::right));
		keyMap.put(KeyEvent.VK_SPACE, new ChangeStateKeyEvent(KeyEvent.VK_SPACE, GameStates.BATTLE_FIGHT_OPTIONS, GameStates.BATTLE_PLAYER_MOVE).withExtra(() ->  {
			SoundsHandler.playSound(SoundsHandler.PRESS);
			try {
//				Move move = rect.getSelected();
				Move move = new SpecialAttackMove("Absorb", Types.GRASS, 100, 25, 20);
				bpmap.setMoveAnimations(new MoveAnimations(parent, move.getName()));
				
				BattleMap map = new BattleMap();
				map.put(BattleMap.MOVE, move);
				
				BattleEvent evt = parent.getBattle();
				map.put(BattleMap.BATTLE_CLASS_KEY, WildPokemonBattle.KEY);
				map.put(BattleMap.PLAYER_PKM, new BattlePokemon(Player.instance().getTeam().get(0).getData()));
				map.put(BattleMap.ENEMY_PKM, new BattlePokemon(evt.getEntityPokemon().getData()));
				
				evt.setMap(map);
			} catch (IOException e) {
				e.printStackTrace();
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
