package core.gui.screen.content.battle.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;

import core.enums.Genders;
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
	private final Font nameFont;
	
	private final Genders gender;
	private final Point genderPoint;
	private final Font genderFont;
	
	private final String level;
	private final Point levelPoint;
	private final Font levelFont;
	
	
	public BattleEnemyPokemonLabelPainter(Battle parent) throws IOException {
		super(parent);
		
		WildPokemonBattle wildBattle = (WildPokemonBattle) parent.getBattle();
		enemy = wildBattle.getEntityPokemon();
		
		labelImage = ImageHandler.resize(ImageHandler.getImage("battle_enemy_pkm_label", "battle").getImage(), 0.5f/ContentSettings.tileResize);
		labelPoint = new Point(ContentSettings.tileSize/2, ContentSettings.tileSize/2);
		
		hpBarBounds = new Rectangle(labelPoint.x + 24*labelImage.getWidth()/175, labelPoint.y + 40*labelImage.getHeight()/62, (143-24+ContentSettings.tileResize/2)*labelImage.getWidth()/175, (43-40+ContentSettings.tileResize/2)*labelImage.getHeight()/62);
		hpBarBackground = Colors.GRAY_64;
		
		
		name = enemy.getData().getDisplayName();
		namePoint = new Point(labelPoint.x + 12*labelImage.getWidth()/175, labelPoint.y + 20*labelImage.getHeight()/62);
		nameFont = new Font(Fonts.BATTLE_FONT.deriveFont(3f*Fonts.BATTLE_FONT.getSize2D()/2f));
		
		gender = enemy.getData().getGender();
		genderPoint = new Point(namePoint.x + nameFont.getWidth(name) + 2*ContentSettings.tileResize, namePoint.y);
		genderFont = new Font(nameFont.deriveFont(nameFont.getSize2D()/2f).deriveFont(Font.BOLD));
		
		level = "Lv" + enemy.getData().getLevel();
		levelFont = new Font(nameFont.deriveFont(2f*nameFont.getSize2D()/3f));
		levelPoint = new Point(labelPoint.x + 145*labelImage.getWidth()/175 - levelFont.getWidth(level), namePoint.y);
		
		
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
		
		// Namewwwwww
		g.setColor(Colors.GRAY_32);
		g.setFont(nameFont);
		g.drawString(name, namePoint.x, namePoint.y);
		
		//Gender
		g.setColor(gender.getColor());
		g.setFont(genderFont);
		g.drawString(gender.getCode() + "", genderPoint.x, genderPoint.y);
		
		//Level
		g.setColor(Colors.GRAY_32);
		g.setFont(levelFont);
		g.drawString(level, levelPoint.x, levelPoint.y);
	}
	
	
	public void refresh() {
		setHpBar();
	}
	
	private void setHpBar() {
		Color green = new Color(64, 255, 64);
		Color yellow = new Color(255, 255, 64);
		Color red = new Color(255, 64, 64);
		Color color;
		
		EntityPokemonStats stats = enemy.getData().getStats();
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
