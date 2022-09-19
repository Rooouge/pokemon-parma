package core.gui.screen.content.battle.painters;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;

import core.events.battle.WildPokemonBattle;
import core.files.ImageHandler;
import core.fonts.Font;
import core.fonts.Fonts;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.battle.painters.superclasses.BattlePokemonLabelPainter;
import jutils.gui.Colors;

public class BattleEnemyPokemonLabelPainter extends BattlePokemonLabelPainter {

	public BattleEnemyPokemonLabelPainter(Battle parent) throws IOException {
		super(parent);
		
		WildPokemonBattle wildBattle = (WildPokemonBattle) parent.getBattle();
		pokemon = wildBattle.getEntityPokemon();
		
		labelImage = ImageHandler.resize(ImageHandler.getImage("battle_enemy_pkm_label", "battle").getImage(), 0.5f/ContentSettings.tileResize);
		labelPoint = new Point(ContentSettings.tileSize/2, ContentSettings.tileSize/2);
		
		hpBarBounds = new Rectangle(labelPoint.x + 24*labelImage.getWidth()/175, labelPoint.y + 40*labelImage.getHeight()/62, (143-24+ContentSettings.tileResize/2)*labelImage.getWidth()/175, (43-40+ContentSettings.tileResize/2)*labelImage.getHeight()/62);
		hpBarBackground = Colors.GRAY_64;
		
		
		name = pokemon.getData().getDisplayName();
		namePoint = new Point(labelPoint.x + 12*labelImage.getWidth()/175, labelPoint.y + 20*labelImage.getHeight()/62);
		nameFont = new Font(Fonts.BATTLE_FONT.deriveFont(3f*Fonts.BATTLE_FONT.getSize2D()/2f));
		
		gender = pokemon.getData().getGender();
		genderPoint = new Point(namePoint.x + nameFont.getWidth(name) + 2*ContentSettings.tileResize, namePoint.y);
		genderFont = new Font(nameFont.deriveFont(nameFont.getSize2D()/2f).deriveFont(Font.BOLD));
		
		level = "Lv" + pokemon.getData().getLevel();
		levelFont = new Font(nameFont.deriveFont(2f*nameFont.getSize2D()/3f));
		levelPoint = new Point(labelPoint.x + 145*labelImage.getWidth()/175 - levelFont.getWidth(level), namePoint.y);
		
		
		refresh();
	}
	
}
