package core.gui.screen.content;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import core.Core;
import core.obj.actions.Action;
import jutils.gui.ColoredPanel;
import lombok.Getter;

@SuppressWarnings("serial")
public class Content extends ColoredPanel {

	@Getter
	protected List<Action> actions;
	
	
	public Content() {
		super(Color.black);
		setPreferredSize(ContentSettings.dimension);
		
		actions = new ArrayList<>();
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
	
	public boolean removeAction(Action action) {
		return actions.remove(action);
	}
	
	public void clearActions() {
		actions.clear();
	}
	
	public boolean hasNoAction() {
		return actions.isEmpty();
	}
}
