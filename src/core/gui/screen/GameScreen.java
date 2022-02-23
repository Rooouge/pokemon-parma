package core.gui.screen;

import javax.swing.JFrame;

import core.gui.screen.content.Content;
import jutils.config.Config;
import jutils.global.Global;
import jutils.gui.listeners.OnExitListener;
import lombok.Getter;

@SuppressWarnings("serial")
public class GameScreen extends JFrame {

	private Content content;
	@Getter
	private int maxFps;
	private GlobalKeyEventHandler keyHandler;
	
	
	public GameScreen(Class<? extends Content> contentClass) throws Exception {
		init(contentClass);
	}
	
	
	private void init(Class<? extends Content> contentClass) throws Exception {
		maxFps = Integer.parseInt(Config.getValue("screen.max-fps"));
		setTitle(Config.getValue("screen.title"));
		addWindowListener(new OnExitListener());
		
		keyHandler = new GlobalKeyEventHandler();
		addKeyListener(keyHandler);
		Global.add(GlobalKeyEventHandler.KEY, keyHandler);
		
		content = contentClass.getDeclaredConstructor().newInstance();
		Global.add("content", content);
		setContentPane(content);
		
		setToCenter();
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
	
}
