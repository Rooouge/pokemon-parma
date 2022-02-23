package core.obj.maps;

import java.awt.Dimension;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import org.dom4j.Node;

import core.Log;
import core.enums.Directions;
import core.files.MusicHandler;
import core.gui.GridPosition;
import core.gui.XYLocation;
import core.gui.screen.content.ContentSettings;
import core.obj.maps.links.Link;
import lombok.Getter;

@Getter
public class MapData {

	private final String name;
	private final String registryName;
	private final Dimension size;
	private final GridPosition spawnpoint;
	private final Clip music;
	private final long loopAt;
	private final long loopTo;
	private XYLocation loc;
	private GridPosition pos;	
	
	
	public MapData(Node root) {
		name = root.selectSingleNode("name").valueOf("@name");
		Log.log("Map name: " + name);
		registryName = name.toLowerCase().replace(" ", "_");
		
		int w = Integer.parseInt(root.selectSingleNode("size/width").getStringValue());
		int h = Integer.parseInt(root.selectSingleNode("size/height").getStringValue());
		size = new Dimension(w, h);
		
		// Location & Position
		int spawnRow = Integer.parseInt(root.selectSingleNode("spawnpoint/row").getStringValue());
		int spawnColumn = Integer.parseInt(root.selectSingleNode("spawnpoint/column").getStringValue());
		spawnpoint = new GridPosition(spawnRow, spawnColumn);
		loc = new XYLocation(-1*(ContentSettings.tileSize * (spawnColumn - ContentSettings.horTiles/2)), -1*(ContentSettings.tileSize * (spawnRow - ContentSettings.verTiles/2)));
		setPosFromLoc();
		
		// Music
		Node musicNode = root.selectSingleNode("music");
		music = MusicHandler.get(musicNode.selectSingleNode("name").getStringValue());
		
		Node loopNode = musicNode.selectSingleNode("loop");
		loopAt = Integer.parseInt(loopNode.selectSingleNode("at").getStringValue());
		loopTo = Integer.parseInt(loopNode.selectSingleNode("to").getStringValue());
		
		float volume = Float.parseFloat(musicNode.selectSingleNode("volume").getStringValue());
		FloatControl control = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
		control.setValue(20f * (float) Math.log10(volume));		
	}
	
	
	public void setPosFromLoc() {
		pos = loc.convert();
	}
	
	public void setLocFromPos() {
		loc = pos.convert();
	}
	
	public XYLocation getLocationFromPos(GridPosition pos) {
//		System.out.println(loc + " - " + this.pos + " --- " + pos);
		return new XYLocation(loc.x + (pos.column)*ContentSettings.tileSize, loc.y + (pos.row)*ContentSettings.tileSize);
//		return pos.convert();
	}
	
	public void setPosAsNeighbor(Map main, Link l) {
		MapData mData = main.getData();
		GridPosition mPos = mData.pos;
		Directions dir = l.getDir(mData.registryName);
		
//		System.out.println("Connection from " + mData.registryName + " to " + registryName + " = " + dir);
		
		switch (dir) {
		case DOWN:
			pos = new GridPosition(mPos.row + mData.size.height, mPos.column + l.getOffset(registryName));
			break;
		case UP:
			pos = new GridPosition(mPos.row - size.height, mPos.column + l.getOffset(registryName));
			break;
		case LEFT:
			pos = new GridPosition(mPos.row + l.getOffset(registryName), mPos.column - size.width);
			break;
		case RIGHT:
			pos = new GridPosition(mPos.row + l.getOffset(registryName), mPos.column + mData.size.width);
			break;
		}
		
		setLocFromPos();
	}
}
