package core.gui.screen.content.battle.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;

import core.events.battle.WildPokemonBattle;
import core.files.ImageHandler;
import core.files.TiledImage;
import core.gui.interfaces.Painter;
import core.gui.screen.content.battle.Battle;
import core.obj.pokemon.entity.EntityPokemon;

public class BattleEnemyPokemonPainter extends Painter<Battle> {

	private final EntityPokemon enemy;
	private final TiledImage enemyImage;
	private final Point enemyPoint;
	
	
	public BattleEnemyPokemonPainter(Battle parent, TiledImage terrainImage, Point terrainPoint) throws IOException {
		super(parent);
		
		WildPokemonBattle wildBattle = (WildPokemonBattle) parent.getBattle();
		enemy = wildBattle.getEntityPokemon();
		
		
		enemyImage = ImageHandler.getPokemonEnemyImage(enemy.getData().getBaseData().getId(), wildBattle.getEvent().isShiny());
		enemyPoint = new Point(terrainPoint.x + terrainImage.getWidth()/2 - enemyImage.getWidth()/2, terrainPoint.y - terrainImage.getHeight() + calculateYOffsetByEnemyImage()/2);
	}
	
	/*
	 * Method used to place at the corret Y in the screen the enemy's image
	 */
	private int calculateYOffsetByEnemyImage() {
		int offset = 0;
		
		for(int r = enemyImage.getHeight()-1; r >= 0; r--) {
			for(int c = 0; c < enemyImage.getWidth(); c++) {
				if(!ImageHandler.isTransparent(enemyImage.getImage().getRGB(r, c)))
					return offset;
			}
			
			offset++;
		}
		
		return offset;
	}

	
	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.red);
		
		// Drawing pokémon
		g.drawImage(enemyImage.getImage(), enemyPoint.x, enemyPoint.y, null);
//		g.drawRect(enemyPoint.x, enemyPoint.y, enemyImage.getWidth(), enemyImage.getHeight());
	}

}
