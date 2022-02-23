package core.gui;

import core.gui.screen.content.ContentSettings;

public class XYLocation {

	public int x;
	public int y;
	
	
	public XYLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	public GridPosition convert() {
		return new GridPosition(y/ContentSettings.tileSize, x/ContentSettings.tileSize);
	}
	
	@Override
	public String toString() {
		return "(x: " + x + ", y: " + y + ")";
	}
}
