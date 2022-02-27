package core.obj.maps.tileentities;

import org.dom4j.Node;

import core.gui.GridPosition;
import core.obj.maps.Map;
import jutils.asserts.AssertException;
import lombok.Getter;
import lombok.Setter;

@Getter
public class TileEntityData {

	private final Map map;
	private final GridPosition pos;
	@Setter
	private TileScripts scripts;
	
	
	public TileEntityData(Map map, Node root) throws AssertException {
		this.map = map;
		pos = new GridPosition(root.valueOf("@tile"));
	}
}
