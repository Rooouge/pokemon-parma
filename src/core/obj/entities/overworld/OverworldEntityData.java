package core.obj.entities.overworld;

import java.io.IOException;

import org.dom4j.Node;

import core.enums.Directions;
import core.enums.MovementsDescriptor;
import core.enums.OverworldEntityTypes;
import core.files.ImageHandler;
import core.files.TiledImage;
import core.gui.GridPosition;
import core.gui.XYLocation;
import core.gui.screen.content.ContentSettings;
import core.obj.scripts.statescripts.EntityScripts;
import lombok.Getter;
import lombok.Setter;

@Getter
public class OverworldEntityData {
	
	protected final int variant;
	protected final String name;
	protected final OverworldEntityTypes type;
	@Setter
	protected GridPosition originalPos;
	@Setter
	protected GridPosition pos;
	@Setter
	protected XYLocation loc;
	@Setter
	protected Directions originalFacing;
	@Setter
	protected Directions facing;
	@Setter
	protected boolean running;
	@Getter
	@Setter
	protected boolean visible;
	@Getter
	@Setter
	protected MovementsDescriptor movementsDescriptor;
	private TiledImage[] images;
	@Setter
	protected EntityScripts scripts;
	
	
	protected OverworldEntityData(String name) throws IOException {
		this(name, null);
	}
	
	protected OverworldEntityData(String name, OverworldEntityTypes type) throws IOException {
		this.name = name;
		this.type = type;
		variant = -1;
		facing = Directions.DOWN;
		running = false;
		visible = true;
		
		initImages();
	}
	
	public OverworldEntityData(Node root) throws IOException {
		variant = Integer.parseInt(root.valueOf("@id"));
		name = root.selectSingleNode("name").valueOf("@value");
		type = OverworldEntityTypes.getFromIndex(Integer.parseInt(root.selectSingleNode("type").valueOf("@value")));
		running = false;
		
		initImages();
	}
	

	private void initImages() throws IOException {
		int spacing = name.equalsIgnoreCase("player") ? 2*ContentSettings.tileResize : 0;
		int width = ContentSettings.tileSize;
		TiledImage original = ImageHandler.getEntityExplorationImage(name);
		
		int numOfImages = (original.getWidth() + spacing) / (width + spacing);
		images = new TiledImage[numOfImages];
		
		for(int i = 0; i < numOfImages; i++) {
			images[i] = new TiledImage(original.getImage().getSubimage(width*i + spacing*i, 0, width, original.getHeight()));
		}
	}
	
	
	public void setPos(int row, int column) {
		pos = new GridPosition(row, column);		
	}
	
}
