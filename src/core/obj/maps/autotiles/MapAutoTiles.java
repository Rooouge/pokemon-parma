package core.obj.maps.autotiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Node;

import core.Log;
import core.files.FileHandler;
import core.files.ImageHandler;
import core.files.TiledImage;
import core.gui.GridPosition;
import core.obj.maps.Map;
import jutils.config.Config;
import lombok.Getter;

@SuppressWarnings("serial")
public class MapAutoTiles extends ArrayList<AutoTile> {

	@Getter
	private final Map map;
	
	
	public MapAutoTiles(Map map, Node root) throws IOException {
		this.map = map;
		
		if(root != null) {
			String ext = Config.getValue("autotile-file-extension");
			
			List<Node> autotiles = root.selectNodes("autotile");
			for(Node n : autotiles) {
				String resName = n.valueOf("@name");
				TiledImage image = ImageHandler.getAutotileImage(resName);
				
				String[] args;
				try (
					BufferedReader br = new jutils.files.BufferedReader(FileHandler.getFile("autotiles", resName, ext));
				) {
					args = br.readLine().split(";");
				}
				
				int numOfSprite = Integer.parseInt(args[0]);
				int times = numOfSprite;
				int delay = Integer.parseInt(args[1]);
				
				
				String[] posString = n.valueOf("@pos").split(",");
				GridPosition pos = new GridPosition(Integer.parseInt(posString[0]), Integer.parseInt(posString[1]));
				
				AutoTile at = new AutoTile(resName, image, times, numOfSprite, delay);
				spawn(at, pos);
				Log.log("Added AutoTile '" + at.getResName() + "' at pos: " + at.getPos());
			}
		} else {
			Log.log("<autotiles> node not found");
		}
		
		
	}
	
	
	public boolean spawn(AutoTile at, GridPosition pos) {
		boolean result = add(at);
		
		if(result) {
			at.setPos(pos);
			at.setOriginalPos(new GridPosition(pos.row, pos.column));
			at.setLoc(map.getData().getLocationFromPos(pos));
		}
		
		return result;
	}
	
	public void respawnAll() {
		List<AutoTile> temp = new ArrayList<>(this);
		
		clear();
		for(AutoTile at : temp) {
			spawn(at, at.getOriginalPos());
		}
	}
	
	public AutoTile getFromPos(GridPosition pos) {
		for(AutoTile at : this) {
			if(at.getPos().equals(pos))
				return at;
		}
		
		return null;
	}
}
