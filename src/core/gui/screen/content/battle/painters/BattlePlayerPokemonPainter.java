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
import core.obj.entities.player.Player;
import core.obj.pokemon.entity.EntityPokemon;

public class BattlePlayerPokemonPainter extends Painter<Battle> {

	private final EntityPokemon player;
	private final TiledImage playerImage;
	private final Point playerPoint;
	private final TiledImage terrainImage;
	private final Point terrainPoint;
	
	
	public BattlePlayerPokemonPainter(Battle parent) throws IOException {
		super(parent);
		
		player = Player.instance().getTeam().get(0);
		
		terrainImage = ImageHandler.resize(ImageHandler.getImage("battle_player_terrain_gray", "battle\\terrains").getImage(), 0.5f/ContentSettings.tileResize);
		terrainPoint = new Point(ContentSettings.tileSize, (int) (ContentSettings.dimension.getHeight() - 2*ContentSettings.tileSize - terrainImage.getHeight()));
		
		playerImage = ImageHandler.getPokemonPlayerImage(player.getData().getBaseData().getId(), player.getData().isShiny());
		playerPoint = new Point(terrainPoint.x + terrainImage.getWidth()/2 - playerImage.getWidth()/2, terrainPoint.y + terrainImage.getHeight() - playerImage.getHeight());
	}
	
	
	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.red);
		
		// Drawing terrain
		g.drawImage(terrainImage.getImage(), terrainPoint.x, terrainPoint.y, null);
//		g.drawRect(terrainPoint.x, terrainPoint.y, terrainImage.getWidth(), terrainImage.getHeight());
		
		// Drawing pokémon
		g.drawImage(playerImage.getImage(), playerPoint.x, playerPoint.y, null);
//		g.drawRect(playerPoint.x, playerPoint.y, playerImage.getWidth(), playerImage.getHeight());
	}

}
