package core.gui.screen;

import java.awt.CardLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import javax.swing.JFrame;

import core.Log;
import core.enums.GameStates;
import core.gui.screen.content.Content;
import core.gui.screen.painters.ScreenPainter;
import jutils.config.Config;
import jutils.global.Global;
import jutils.gui.ColoredPanel;
import jutils.gui.listeners.OnExitListener;
import lombok.Getter;

@SuppressWarnings("serial")
public class GameScreen extends JFrame {

	public static final String KEY = "screen";
	
	private final Map<String, Content<? extends ColoredPanel>> map;
	@Getter
	private final ScreenPainter painter;
	@Getter
	private final int maxFps;
	private final GlobalKeyEventHandler keyHandler;
	private final CardLayout layout;
	private final ColoredPanel pane;
	@Getter
	private Content<? extends ColoredPanel> content;
	
	
	public GameScreen(Class<? extends Content<? extends ColoredPanel>> contentClass) throws Exception {
		Global.add(KEY, this);
		layout = new CardLayout();
		pane = new ColoredPanel(Color.black, layout);
		setContentPane(pane);
		map = new HashMap<>();
		
		maxFps = Integer.parseInt(Config.getValue("screen.max-fps"));
		setTitle(Config.getValue("screen.title"));
		addWindowListener(new OnExitListener());
		
		Log.log("Initializing GlobalKeyEventHandler...");
		keyHandler = new GlobalKeyEventHandler();
		addKeyListener(keyHandler);
		Global.add(GlobalKeyEventHandler.KEY, keyHandler);
		Log.log("GlobalKeyEventHandler initialized.");
		
		painter = new ScreenPainter(this);
		painter.fadeIn(GameStates.EXPLORATION);
		
		switchContent(contentClass);
		setToCenter();		
		setResizable(false);
		requestFocus();
	}
	
	
	public static GameScreen instance() {
		return Global.get(KEY, GameScreen.class);
	}
	
	
	public void setToCenter() {
		setLocationRelativeTo(null);
	}
	
	
	public void update() throws Exception {
		keyHandler.update();
		content.update();
	}
	
	
	public void switchContent(Class<? extends Content<? extends ColoredPanel>> contentClass) throws Exception {
		final Content<? extends ColoredPanel> test = content;
		if(test != null) {
			String name = test.getClass().getSimpleName();
			if(content.isForceChache()) {
				Log.log("Not checking " + name);
			} else {
				content.getDeallocator().schedule(new TimerTask() {
					@Override
					public void run() {
						String className = contentClass.getSimpleName();
						Log.log("Checking " + name + " with " + className);
						
						if(!test.equals(content)) {
							Log.log("Memory of " + name + " is now free");
							Content<? extends ColoredPanel> panel = map.remove(name);
							remove(panel);
						} else {
							Log.log("Not removing " + name);
						}
					}
				}, 10000);
			}
		}
		
		String className = contentClass.getSimpleName();
		if(!map.containsKey(className)) {
			content = contentClass.getDeclaredConstructor().newInstance();
			map.put(className, content);
			add(content, className);
			layout.show(pane, className);
		} else {
			content = map.get(className);
			layout.show(pane, className);
		}
		
		Global.add("content", content);
//		setContentPane(content);
		pack();
	}
	
}
