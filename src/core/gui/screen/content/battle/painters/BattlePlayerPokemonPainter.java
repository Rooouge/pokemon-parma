package core.gui.screen.content.battle.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;

import core.files.ImageHandler;
import core.files.TiledImage;
import core.gui.interfaces.Painter;
import core.gui.screen.content.battle.Battle;
import core.obj.entities.player.Player;
import core.obj.pokemon.entity.EntityPokemon;

public class BattlePlayerPokemonPainter extends Painter<Battle> {

	private final EntityPokemon player;
	private final TiledImage playerImage;
	private final Point playerPoint;
	
	
	public BattlePlayerPokemonPainter(Battle parent, TiledImage terrainImage, Point terrainPoint) throws IOException {
		super(parent);
		
		player = Player.instance().getTeam().get(0);
		
		playerImage = ImageHandler.getPokemonPlayerImage(player.getData().getBaseData().getId(), player.getData().isShiny());
		playerPoint = new Point(terrainPoint.x + terrainImage.getWidth()/2 - playerImage.getWidth()/2, terrainPoint.y + terrainImage.getHeight() - playerImage.getHeight());
	}
	
	
	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.red);
		
		// Drawing pokémon
		g.drawImage(playerImage.getImage(), playerPoint.x, playerPoint.y, null);
//		g.drawRect(playerPoint.x, playerPoint.y, playerImage.getWidth(), playerImage.getHeight());
	}

}
