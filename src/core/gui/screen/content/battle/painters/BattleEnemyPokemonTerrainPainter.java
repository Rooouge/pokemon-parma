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
public class BattleEnemyPokemonTerrainPainter extends Painter<Battle> {

	private final TiledImage terrainImage;
	private final Point terrainPoint;
	
	
	public BattleEnemyPokemonTerrainPainter(Battle parent) throws IOException {
		super(parent);
		
		terrainImage = ImageHandler.resize(ImageHandler.getImage("battle_enemy_terrain_gray", "battle\\terrains").getImage(), 0.5f/ContentSettings.tileResize);
		terrainPoint = new Point(ContentSettings.dimension.width - terrainImage.getWidth() - ContentSettings.tileSize, 5*ContentSettings.tileSize/2);
	}
	
	
	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.red);
		// Drawing terrain
		
		g.drawImage(terrainImage.getImage(), terrainPoint.x, terrainPoint.y, null);
//		g.drawRect(terrainPoint.x, terrainPoint.y, terrainImage.getWidth(), terrainImage.getHeight());			
	}
}
