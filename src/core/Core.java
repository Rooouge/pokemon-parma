package core;

import core.enums.GameStates;
import core.files.SoundsHandler;
import core.fonts.Fonts;
import core.gui.screen.GameScreen;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.Exploration;
import core.obj.maps.links.Links;
import jutils.global.Global;
import jutils.threads.Threads;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Core {

	public int fps;
	private GameScreen screen;
	
	
	public void init() throws Exception {
		GameStates.set(GameStates.EXPLORATION_FADE_IN);
		
		
		Log.log("Initializing sounds...");
		SoundsHandler.init();
		Log.log("Sounds initialized.");
		
		Log.log("Initializing content settings...");
		ContentSettings.init();
		Log.log("Content settings initialized.");
		
		Log.log("Initializing fonts...");
		Fonts.init();
		Log.log("Fonts initialized.");
		
		Log.log("Initializing map links...");
		Links.init();
		Log.log("Map links initialized.");
		
		Log.log("Initializing GameScreen...");		
		screen = new GameScreen(Exploration.class);
		Global.add("screen", screen);
		Log.log("GameScreen initialized.");
		
	}
	
	
	public void run() {
		GameScreen screen = Global.get("screen", GameScreen.class);
		
		Threads.run(() -> {
			int fps = 0;
			int maxFps = screen.getMaxFps();
			double delta = 0d;
			long last = System.currentTimeMillis();
			long timer = 0l;
			
			screen.setVisible(true);
			while(true) {
				long now = System.currentTimeMillis();
				long since = now - last;
				
				delta += since / (1000d / maxFps);
				timer += since;
				last = now;
				
				if(delta >= 1d) {
					fps++;
					delta = 0d;
					
					try {
						screen.update();
					} catch (Exception e) {
						e.printStackTrace();
					}
					screen.repaint();
				}
				
				if(timer >= 1000l) {
					Core.fps = fps;
//					System.out.println(fps);
					fps = 0;
					timer = 0l;
				}
			}
			
//			double drawInterval = 1000000000/screen.getMaxFps();
//			double nextDrawTime = System.nanoTime() + drawInterval;
//			double timeLeft;
//			
//			screen.setVisible(true);
//			while(true) {
//				screen.update();
//				screen.repaint();
//				
//				try {
//					timeLeft = (nextDrawTime - System.nanoTime())/1000000;
//					
//					if(timeLeft < 0)
//						timeLeft = 0;
//					
//					Thread.sleep((long) timeLeft);
//					
//					nextDrawTime += drawInterval;
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				
//			}
		});

		Log.log("Screen '" + screen.getTitle() + "' now visible.");
	}
	
}
