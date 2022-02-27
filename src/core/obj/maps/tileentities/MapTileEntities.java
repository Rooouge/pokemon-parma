package core.obj.maps.tileentities;

import java.util.ArrayList;

import org.dom4j.Node;

import core.gui.GridPosition;
import core.obj.maps.Map;
import core.obj.maps.scripts.MapScriptTypes;
import core.obj.scripts.Scripts;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class MapTileEntities extends ArrayList<TileEntity> {

	private final Map map;
	
	
	public MapTileEntities(Map map, Node root) throws Exception {
		this.map = map;
		
		for(Node tileNode : root.selectNodes("script")) {
			if(MapScriptTypes.getFromValue(tileNode.valueOf("@type")).equals(MapScriptTypes.TILE_SCRIPT)) {
				System.out.println(tileNode.asXML());
				TileEntity te = new TileEntity(map, tileNode);
				te.getData().setScripts(Scripts.getTileScripts(te, map, te.getData().getPos(), map.getEntities()));
				
				add(te);
			}
		}
	}
	
	
	public TileEntity getFromPos(GridPosition pos) {
//		System.out.println("Size: " + size());
		for(TileEntity te : this) {
//			System.out.println("- " + te.getData().getPos());
			if(te.getData().getPos().equals(pos))
				return te;
		}
		
		return null;
	}
}
