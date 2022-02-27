package core.obj.maps.tileentities;

import java.io.File;

import core.gui.GridPosition;
import core.obj.maps.Map;
import core.obj.scripts.Script;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class TileScript extends Script {

	protected final GridPosition pos;
	protected final Map map;
	
	
	public TileScript(File file, Map map, GridPosition pos) {
		super(file, null);
		this.map = map;
		this.pos = pos;
	}

}
