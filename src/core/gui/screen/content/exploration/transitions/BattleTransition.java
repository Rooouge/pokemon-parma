package core.gui.screen.content.exploration.transitions;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import core.enums.GameStates;
import core.enums.TileMovements;
import core.events.battle.BattleEvent;
import core.events.battle.WildPokemonBattle;
import core.files.ImageHandler;
import core.files.MusicHandler;
import core.files.TiledImage;
import core.gui.screen.GameScreen;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.exploration.Exploration;
import core.gui.screen.painters.ScreenPainter;
import core.gui.screen.painters.animations.GUIAnimation;
import core.obj.maps.wild.WildPokemonEvent;
import jutils.global.Global;
import jutils.threads.Threads;

public class BattleTransition extends GUIAnimation {
	
	protected final Exploration exp;
	protected final BufferedImage[] animation;
	protected WildPokemonEvent event;
	protected TileMovements tile;
	protected WildPokemonBattle battle;
	protected int flag;
	
	
	public BattleTransition(Exploration parent, String resName) throws IOException {
		super(parent, 150, true);
		this.exp = parent;
		
		TiledImage animationRaw = ImageHandler.getImage(resName, "battle/transitions");
		BufferedImage img = new BufferedImage(animationRaw.getWidth(), animationRaw.getHeight(), BufferedImage.TYPE_INT_ARGB);
		img.getGraphics().drawImage(animationRaw.getImage(), 0, 0, null);
		animation = new BufferedImage[animationRaw.getHeight() / (ContentSettings.verTiles*ContentSettings.tileSize)];
		
		int w = animationRaw.getWidth();
		int h = animationRaw.getHeight()/animation.length;
		
//		System.out.println("W: " + animationRaw.getWidth() + ", H: " + animationRaw.getHeight() + " --- L: " + animation.length);
		for(int i = 0; i < animation.length; i++) {
//			System.out.println(i + ")   0, " + ((i)*animationRaw.getHeight()/animation.length)/ContentSettings.tileResize + ", " + animationRaw.getWidth()/ContentSettings.tileResize + ", " + (animationRaw.getHeight()/animation.length)/ContentSettings.tileResize);
//			animation[i] = animationRaw.getImage().getSubimage(0, (i)*animationRaw.getHeight()/animation.length, animationRaw.getWidth(), animationRaw.getHeight()/animation.length);
			
			animation[i] = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics g = animation[i].getGraphics();
			g.drawImage(img.getSubimage(0, (i)*img.getHeight()/animation.length, w, h), 0, 0, null);
		}
		
		flag = 0;
	}
	
	
	public void setEvent(WildPokemonEvent event, TileMovements tile) {
		this.event = event;
		this.tile = tile;
	}
	
	
	@Override
	public void onStart() {
		Exploration exp = (Exploration) parent;
		exp.setForceStop(true);
		
//		Log.info("-- 0 --");
		
		MusicHandler.stopMapMusic(exp.getActiveMap());
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
				exp.setForceStop(false);
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
