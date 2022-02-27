package core.gui;

import core.gui.screen.content.ContentSettings;
import jutils.asserts.Assert;
import jutils.asserts.AssertException;

public class GridPosition {

	public int row;
	public int column;
	
	
	public GridPosition(int r, int c) {
		this.row = r;
		this.column = c;
	}
	
	public GridPosition(String s) throws AssertException {
		String[] args = s.split(",");
		Assert.isTrue(args.length == 2, "Failed to create GridPosition from string '" + s + "'");
		row = Integer.parseInt(args[0]);
		column = Integer.parseInt(args[1]);
	}
	
	
	public void add(int dr, int dc) {
		row += dr;
		column += dc;
	}
	
	public XYLocation convert() {
		return new XYLocation(column*ContentSettings.tileSize, row*ContentSettings.tileSize);
	}
	
	@Override
	public String toString() {
		return "(r: " + row + ", c: " + column + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof GridPosition) {
			GridPosition pos = (GridPosition) obj;
			return row == pos.row && column == pos.column;
		}
		return super.equals(obj);
	}
	
	public int compare(GridPosition pos) {
		if(before(pos))
			return -1;
		if(after(pos))
			return 1;
		return 0;
	}
	
	private boolean before(GridPosition pos) {
		return row < pos.row || (row == pos.row && column < pos.column);
	}
	
	private boolean after(GridPosition pos) {
		return row > pos.row || (row == pos.row && column > pos.column);
	}
}
