package core.gui.screen.content.battle.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;

import core.enums.Stats;
import core.events.battle.WildPokemonBattle;
import core.files.ImageHandler;
import core.files.TiledImage;
import core.fonts.Font;
import core.fonts.Fonts;
import core.gui.interfaces.Painter;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.battle.Battle;
import core.obj.pokemon.entity.EntityPokemon;
import core.obj.pokemon.entity.EntityPokemonStats;
import jutils.gui.Colors;

public class BattleEnemyPokemonLabelPainter extends Painter<Battle> {

	private final EntityPokemon enemy;
	private final TiledImage labelImage;
	private final Point labelPoint;
	
	private final Rectangle hpBarBounds;
	private final Color hpBarBackground;
	private Color hpBarColor;
	private int hpBarLimit;
	
	private final String name;
	private final Point namePoint;
	
	
	public BattleEnemyPokemonLabelPainter(Battle parent) throws IOException {
		super(parent);
		
		WildPokemonBattle wildBattle = (WildPokemonBattle) parent.getBattle();
		enemy = wildBattle.getEntityPokemon();
		
		labelImage = ImageHandler.resize(ImageHandler.getImage("battle_enemy_pkm_label", "battle").getImage(), 0.5f/ContentSettings.tileResize);
		labelPoint = new Point(ContentSettings.tileSize, ContentSettings.tileSize);
		
		hpBarBounds = new Rectangle(labelPoint.x + 24*labelImage.getWidth()/175, labelPoint.y + 40*labelImage.getHeight()/62, (143-24+1)*labelImage.getWidth()/175, (43-40+1)*labelImage.getHeight()/62);
		hpBarBackground = Colors.GRAY_64;
		
		
		name = enemy.getData().getDisplayName();
		namePoint = new Point(labelPoint.x + 12*labelImage.getWidth()/175, labelPoint.y + 17*labelImage.getHeight()/62);
		
		
		refresh();
	}
	
	
	@Override
	public void paint(Graphics2D g) {
		// HP bar
		g.setColor(hpBarBackground);
		g.fillRect(hpBarBounds.x, hpBarBounds.y, hpBarBounds.width, hpBarBounds.height);
		g.setColor(hpBarColor);
		g.fillRect(hpBarBounds.x, hpBarBounds.y, hpBarLimit, hpBarBounds.height);
		
		
		// Label
		g.drawImage(labelImage.getImage(), labelPoint.x, labelPoint.y, null);
		
		// Name
		Font original = Fonts.BATTLE_FONT;
		g.setColor(Colors.GRAY_32);
		g.setFont(original.deriveFont(3f*original.getSize2D()/2f));
		g.drawString(name, namePoint.x, namePoint.y);
	}
	
	
	public void refresh() {
		setHpBar(enemy.getData().getStats());
	}
	
	private void setHpBar(EntityPokemonStats stats) {
		Color green = new Color(64, 255, 64);
		Color yellow = new Color(255, 255, 64);
		Color red = new Color(255, 64, 64);
		Color color;
		
		int hp = stats.get(Stats.HP);
		int hpTot = stats.get(Stats.TOT_HP);
		int percent = (int) ((1f*hp)/(1f*hpTot)*100f);
		
		
		if(percent <= 10)
			color = red;
		else if(percent <= 50)
			color = yellow;
		else
			color = green;
		
		hpBarColor = color;
		hpBarLimit = (int) ((hpBarBounds.getWidth()*1f / 100f)*(percent*1f));
		
//		System.out.println("HP: " + hp + ", HPTOT: " + hpTot + ", %: " + percent);
//		System.out.println("W: " + hpBarBounds.getWidth() + ", L: " + hpBarLimit);
	}
}
