package core.gui.screen.content;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import core.Core;
import core.enums.GameStates;
import core.gui.interfaces.Painter;
import core.gui.screen.GlobalKeyEventHandler;
import core.obj.actions.Action;
import jutils.gui.ColoredPanel;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public abstract class Content<T extends ColoredPanel> extends ColoredPanel {

	protected final List<Action> actions;
	protected final List<Action> toAdd;
	protected final GlobalKeyEventHandler keyHandler;
	protected final java.util.Map<GameStates, Painter<T>> painters;
	protected final Timer deallocator;
	protected final boolean forceChache;
	protected GameStates currentState;
	
	
	public Content(boolean forceCache) {
		super(Color.black);
		setPreferredSize(ContentSettings.dimension);
		
		actions = new ArrayList<>();
		toAdd = new ArrayList<>();
		painters = new HashMap<>();
		keyHandler = GlobalKeyEventHandler.instance();
		
		this.forceChache = forceCache;
		deallocator = new Timer();
		
		currentState = GameStates.current();
	}
	

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintComponent((Graphics2D) g);
	}
	
	protected void paintComponent(Graphics2D g) {
		g.setColor(Color.white);
		g.drawString("Current fps: " + Core.fps, 10, 20);
	}
	
	public void update() throws Exception {
		List<Action> over = new ArrayList<>();
		
		if(!toAdd.isEmpty()) {
			actions.addAll(toAdd);
			toAdd.clear();
		}
		
		//Executing actions
		for(Action a : actions) {
			if(a.execute())
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
		actions.add(action);
	}
	
	public void toAddAction(Action action) {
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
	
	
	protected abstract void initKeyHandlers();
	protected abstract void initPainters() throws IOException;
}
