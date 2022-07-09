package core.gui.screen.content.battle.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;

import core.files.ImageHandler;
import core.files.TiledImage;
import core.gui.interfaces.Painter;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.battle.Battle;
import lombok.Getter;

@Getter
public class BattlePlayerPokemonTerrainPainter extends Painter<Battle> {

	private final TiledImage terrainImage;
	private final Point terrainPoint;
	
	
	public BattlePlayerPokemonTerrainPainter(Battle parent) throws IOException {
		super(parent);
		
		terrainImage = ImageHandler.resize(ImageHandler.getImage("battle_player_terrain_gray", "battle\\terrains").getImage(), 0.5f/ContentSettings.tileResize);
		terrainPoint = new Point(ContentSettings.tileSize, (int) (ContentSettings.dimension.getHeight() - 2*ContentSettings.tileSize - terrainImage.getHeight()));
	}
	
	
	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.red);
		
		// Drawing terrain
		g.drawImage(terrainImage.getImage(), terrainPoint.x, terrainPoint.y, null);
//		g.drawRect(terrainPoint.x, terrainPoint.y, terrainImage.getWidth(), terrainImage.getHeight());
	}
}
