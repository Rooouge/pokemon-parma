package core.gui.screen.content.exploration.transitions;

import java.awt.Graphics2D;
import java.io.IOException;

import core.enums.GameStates;
import core.enums.TileMovements;
import core.events.battle.BattleEvent;
import core.events.battle.WildPokemonBattle;
import core.files.MusicHandler;
import core.gui.screen.GameScreen;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.exploration.Exploration;
import core.gui.screen.painters.ScreenPainter;
import core.gui.screen.painters.animations.GUIAnimation;
import core.obj.maps.wild.WildPokemonEvent;
import jutils.global.Global;
import jutils.threads.Threads;

public class BattleTransition extends GUIAnimation<Exploration> {
	
	protected WildPokemonEvent event;
	protected TileMovements tile;
	protected WildPokemonBattle battle;
	protected int flag;
	
	
	public BattleTransition(Exploration parent, String resName) throws IOException {
		super(parent, resName, "battle/transitions", 150, true);
		flag = 0;
	}
	
	
	public void setEvent(WildPokemonEvent event, TileMovements tile) {
		this.event = event;
		this.tile = tile;
	}
	
	
	@Override
	public void onStart() {
		parent.setForceStop(true);
		
//		Log.info("-- 0 --");
		
		MusicHandler.stopMapMusic(parent.getActiveMap());
		MusicHandler.playMusic(BattleEvent.BATTLE_MUSIC);

//		Log.info("-- 1 --");
		Threads.run(() -> {
			try {
//				Log.info("-- 2 --");
				event.generate();
//				Log.info("-- 3 --");
				battle = new WildPokemonBattle(event, tile);
//				Log.info("-- 4 --");
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void tick() {
		if(tick % (duration / animation.length) == 0) {
//			System.out.println(flag);
			flag++;
		}
	}

	@Override
	public void onEnd() {
		flag = 0;
		GameScreen screen = GameScreen.instance();
		ScreenPainter painter = screen.getPainter();
		
		painter.fadeOut(() -> {
			try {
				screen.switchContent(Battle.class);
				Global.get("content", Battle.class).setEvent(battle);
				parent.setForceStop(false);
				painter.fadeIn(GameStates.BATTLE_INTRO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void paint(Graphics2D g) {
//		System.out.println(flag < animation.length ? flag : animation.length-1);
		g.drawImage(animation[flag < animation.length ? flag : animation.length-1], 0, 0, null);
//		g.drawImage(animation[0], 0, 0, null);
	}

}
