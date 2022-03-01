package core.gui.screen;

import javax.swing.JFrame;

import core.gui.screen.content.Content;
import jutils.config.Config;
import jutils.global.Global;
import jutils.gui.listeners.OnExitListener;
import lombok.Getter;

@SuppressWarnings("serial")
public class GameScreen extends JFrame {

	public static final String KEY = "screen";
	
	
	private Content content;
	@Getter
	private int maxFps;
	private GlobalKeyEventHandler keyHandler;
	
	
	public GameScreen(Class<? extends Content> contentClass) throws Exception {
		init(contentClass);
		Global.add(KEY, this);
	}
	
	
	public static GameScreen instance() {
		return Global.get(KEY, GameScreen.class);
	}
	
	
	private void init(Class<? extends Content> contentClass) throws Exception {
		maxFps = Integer.parseInt(Config.getValue("screen.max-fps"));
		setTitle(Config.getValue("screen.title"));
		addWindowListener(new OnExitListener());
		
		keyHandler = new GlobalKeyEventHandler();
		addKeyListener(keyHandler);
		Global.add(GlobalKeyEventHandler.KEY, keyHandler);
		
		switchContent(contentClass);
		
		setResizable(false);
		requestFocus();
	}
	
	public void setToCenter() {
		pack();
		setLocationRelativeTo(null);
	}
	
	
	public void update() throws Exception {
		keyHandler.update();
		content.update();
	}
	
	
	public void switchContent(Class<? extends Content> contentClass) throws Exception {
		content = contentClass.getDeclaredConstructor().newInstance();
		Global.add("content", content);
		setContentPane(content);
		setToCenter();
	}
	
}
