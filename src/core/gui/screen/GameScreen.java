package core.gui.screen;

import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
	@Getter
	private Content<? extends ColoredPanel> content;
	private final CardLayout layout;
	private final JPanel container;
	
	
	public GameScreen(Class<? extends Content<? extends ColoredPanel>> contentClass) throws Exception {
		Global.add(KEY, this);
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
		
		layout = new CardLayout();
		container = new JPanel(layout);
		setContentPane(container);
		
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
				Log.log(name + " will be kept loaded");
			} else {
				content.scheduleDeallocation(new TimerTask() {
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
				});
			}
		}
		
		String key = contentClass.getName();
		if(!map.containsKey(key)) {
			content = contentClass.getDeclaredConstructor().newInstance();
			map.put(contentClass.getName(), content);
			add(content, key);
		} else {
			content = map.get(contentClass.getName());
			content.reload();
		}
		
		Global.add("content", content);
//		setContentPane(content);
		layout.show(container, key);
		pack();
		
	}
	
}
