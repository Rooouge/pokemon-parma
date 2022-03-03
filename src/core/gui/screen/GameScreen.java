package core.gui.screen;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import javax.swing.JFrame;

import core.Log;
import core.gui.screen.content.Content;
import jutils.config.Config;
import jutils.global.Global;
import jutils.gui.ColoredPanel;
import jutils.gui.listeners.OnExitListener;
import lombok.Getter;

@SuppressWarnings("serial")
public class GameScreen extends JFrame {

	public static final String KEY = "screen";
	
	private final Map<String, Content<? extends ColoredPanel>> map;
	private Content<? extends ColoredPanel> content;
	@Getter
	private int maxFps;
	private GlobalKeyEventHandler keyHandler;
	
	
	public GameScreen(Class<? extends Content<? extends ColoredPanel>> contentClass) throws Exception {
		map = new HashMap<>();
		init(contentClass);
		Global.add(KEY, this);
	}
	
	
	public static GameScreen instance() {
		return Global.get(KEY, GameScreen.class);
	}
	
	
	private void init(Class<? extends Content<? extends ColoredPanel>> contentClass) throws Exception {
		maxFps = Integer.parseInt(Config.getValue("screen.max-fps"));
		setTitle(Config.getValue("screen.title"));
		addWindowListener(new OnExitListener());
		
		Log.log("Initializing GlobalKeyEventHandler...");
		keyHandler = new GlobalKeyEventHandler();
		addKeyListener(keyHandler);
		Global.add(GlobalKeyEventHandler.KEY, keyHandler);
		Log.log("GlobalKeyEventHandler initialized.");
		
		switchContent(contentClass);
		setToCenter();
		
		setResizable(false);
		requestFocus();
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
						Log.log("Checking " + name + " with " + content.getClass().getSimpleName());
						if(!test.equals(content)) {
							Log.log("Memory of " + name + " is now free");
							map.remove(test.getClass().getName());
						} else {
							Log.log("Not removing " + name);
						}
					}
				}, 10000);
			}
		}
		
		if(!map.containsKey(contentClass.getName())) {
			content = contentClass.getDeclaredConstructor().newInstance();
			map.put(contentClass.getName(), content);
		} else {
			content = map.get(contentClass.getName());
		}
		
		Global.add("content", content);
		setContentPane(content);
		pack();
	}
	
}
