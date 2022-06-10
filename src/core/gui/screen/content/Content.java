package core.gui.screen.content;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import core.Core;
import core.Log;
import core.enums.GameStates;
import core.gui.interfaces.Painter;
import core.gui.screen.GameScreen;
import core.gui.screen.GlobalKeyEventHandler;
import core.gui.screen.painters.ScreenPainter;
import core.gui.screen.painters.animations.GUIAnimation;
import core.obj.actions.Action;
import jutils.gui.ColoredPanel;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
public abstract class Content<T extends ColoredPanel> extends ColoredPanel {

	protected final List<Action> actions;
	protected final List<Action> toAdd;
	protected final GlobalKeyEventHandler keyHandlers;
	protected final java.util.Map<GameStates, Painter<T>> painters;
	protected Timer deallocator;
	protected final boolean forceChache;
	protected final int deallocationDelay;
	protected final ScreenPainter screenPainter;
	protected GameStates currentState;
	@Setter
	protected boolean forceStop;
	@Setter
	protected GUIAnimation<? extends Content<? extends ColoredPanel>> animation;
	
	
	public Content(boolean forceCache, int deallocationDelay) {
		super(Color.black);
		setPreferredSize(ContentSettings.dimension);
		
		actions = new ArrayList<>();
		toAdd = new ArrayList<>();
		painters = new HashMap<>();
		keyHandlers = GlobalKeyEventHandler.instance();
		
		this.forceChache = forceCache;
		this.deallocationDelay = deallocationDelay;
		deallocator = new Timer();

		screenPainter = GameScreen.instance().getPainter();
		currentState = GameStates.current();
		forceStop = false;
	}
	

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		paintComponent(g2d);
		afterPaint(g2d);
	}
	
	protected void paintComponent(Graphics2D g) {
		g.setColor(Color.white);
		g.drawString("Current fps: " + Core.fps, 10, 20);
	}
	
	protected void afterPaint(Graphics2D g) {
		screenPainter.paint(g);
	}
	
	
	public void update() throws Exception {
		if(animation != null) {
			animation.update();
			if(animation != null && animation.isForceLock())
				return;
		}
		
		List<Action> over = new ArrayList<>();
		
		if(!toAdd.isEmpty()) {
			actions.addAll(toAdd);
			toAdd.clear();
		}
		
		//Executing actions
		for(Action a : actions) {
			if(forceStop || a.execute())
				over.add(a);
		}
		
		//Removing finished actions
		if(actions.size() == over.size())
			clearActions();
		else {
			for(Action a : over) {
				actions.remove(a);
			}
		}
	}
	
	public void addAction(Action action) {
		if(!forceStop)
			actions.add(action);
	}
	
	public void toAddAction(Action action) {
		if(!forceStop)
			toAdd.add(action);
	}
	
	public boolean removeAction(Action action) {
		return actions.remove(action);
	}
	
	public void clearActions() {
		actions.clear();
	}
	
	public boolean hasNoAction() {
		return actions.isEmpty();
	}
	
	public void scheduleDeallocation(TimerTask task) {
		String name = getClass().getSimpleName();
		int mills = deallocationDelay * 1000;
		
		deallocator.cancel();
		deallocator.purge();
		deallocator = new Timer();		
		deallocator.schedule(task, mills);
		
		Log.log("Scheduled deallocation for " + name + " in " + deallocationDelay + " second(s)");
	}
	
	public void animationOver() {
		this.animation = null;
	}
	
	
	protected abstract void initKeyHandlers();
	protected abstract void initPainters() throws IOException;
	public abstract void reload();
}
