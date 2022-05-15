package core.gui.screen.content.battle.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;

import core.enums.Genders;
import core.enums.Stats;
import core.files.ImageHandler;
import core.files.TiledImage;
import core.fonts.Font;
import core.fonts.Fonts;
import core.gui.interfaces.Painter;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.battle.Battle;
import core.obj.entities.player.Player;
import core.obj.pokemon.entity.EntityPokemon;
import core.obj.pokemon.entity.EntityPokemonExpHandler;
import core.obj.pokemon.entity.EntityPokemonStats;
import core.obj.pokemon.entity.ExpHandler;
import jutils.gui.Colors;

public class BattlePlayerPokemonLabelPainter extends Painter<Battle> {

	private final EntityPokemon player;
	private final TiledImage labelImage;
	private final Point labelPoint;
	
	private final Rectangle hpBarBounds;
	private final Color hpBarBackground;
	private Color hpBarColor;
	private int hpBarLimit;
	
	private String hp;
	private Point hpPoint;
	private final Font hpFont;

	private final Rectangle expBarBounds;
	private final Color expBarBackground;
	private final Color expBarColor;
	private int expBarLimit;
	
	private final String name;
	private final Point namePoint;
	private final Font nameFont;
	
	private final Genders gender;
	private final Point genderPoint;
	private final Font genderFont;
	
	private final String level;
	private final Point levelPoint;
	private final Font levelFont;
	
	
	public BattlePlayerPokemonLabelPainter(Battle parent) throws IOException {
		super(parent);
		
		player = Player.instance().getTeam().get(0);
		
		labelImage = ImageHandler.resize(ImageHandler.getImage("battle_player_pkm_label", "battle").getImage(), 0.5f/ContentSettings.tileResize);
		labelPoint = new Point(ContentSettings.dimension.width - labelImage.getWidth() - ContentSettings.tileSize/2, ContentSettings.dimension.height - 2*ContentSettings.tileSize - labelImage.getHeight() - 4*ContentSettings.tileResize);
		
		hpBarBounds = new Rectangle(labelPoint.x + 44*labelImage.getWidth()/185, labelPoint.y + 36*labelImage.getHeight()/66, (169-44+ContentSettings.tileResize/2)*labelImage.getWidth()/185, (39-36+ContentSettings.tileResize/2)*labelImage.getHeight()/66);
		hpBarBackground = Colors.GRAY_64;
		
		expBarBounds = new Rectangle(labelPoint.x + 36*labelImage.getWidth()/185, labelPoint.y + 58*labelImage.getHeight()/66, (161-36+ContentSettings.tileResize/2)*labelImage.getWidth()/185, (61-58+ContentSettings.tileResize/2)*labelImage.getHeight()/66);
		expBarColor = new Color(150,255,32);
		expBarBackground = Colors.GRAY_64;
		
		
		name = player.getData().getDisplayName();
		namePoint = new Point(labelPoint.x + 32*labelImage.getWidth()/185, labelPoint.y + 20*labelImage.getHeight()/66);
		nameFont = new Font(Fonts.BATTLE_FONT.deriveFont(3f*Fonts.BATTLE_FONT.getSize2D()/2f));
		
		gender = player.getData().getGender();
		genderPoint = new Point(namePoint.x + nameFont.getWidth(name) + 2*ContentSettings.tileResize, namePoint.y);
		genderFont = new Font(nameFont.deriveFont(nameFont.getSize2D()/2f).deriveFont(Font.BOLD));
		
		level = "Lv" + player.getData().getLevel();
		levelFont = new Font(nameFont.deriveFont(2f*nameFont.getSize2D()/3f));
		levelPoint = new Point(labelPoint.x + 172*labelImage.getWidth()/185 - levelFont.getWidth(level), namePoint.y);
		
		hpFont = new Font(levelFont.deriveFont(2f*levelFont.getSize2D()/3f));
		
		refresh();
	}
	
	
	@Override
	public void paint(Graphics2D g) {
		// HP bar
		g.setColor(hpBarBackground);
		g.fillRect(hpBarBounds.x, hpBarBounds.y, hpBarBounds.width, hpBarBounds.height);
		g.setColor(hpBarColor);
		g.fillRect(hpBarBounds.x, hpBarBounds.y, hpBarLimit, hpBarBounds.height);
		
		// Exp bar
		g.setColor(expBarBackground);
		g.fillRect(expBarBounds.x, expBarBounds.y, expBarBounds.width, expBarBounds.height);
		g.setColor(expBarColor);
		g.fillRect(expBarBounds.x, expBarBounds.y, expBarLimit, expBarBounds.height);
		
		
		// Label
		g.drawImage(labelImage.getImage(), labelPoint.x, labelPoint.y, null);
		
		
		// Name
		g.setColor(Colors.GRAY_32);
		g.setFont(nameFont);
		g.drawString(name, namePoint.x, namePoint.y);
		
		// Gender
		g.setColor(gender.getColor());
		g.setFont(genderFont);
		g.drawString(gender.getCode() + "", genderPoint.x, genderPoint.y);
		
		// Level
		g.setColor(Colors.GRAY_32);
		g.setFont(levelFont);
		g.drawString(level, levelPoint.x, levelPoint.y);
		
		// HP
		g.setColor(Colors.GRAY_32);
		g.setFont(hpFont);
		g.drawString(hp, hpPoint.x, hpPoint.y);
	}
	
	
	public void refresh() {
		setHpBar();
		setExpBar();
		setHp();
	}
	
	private void setHpBar() {
		Color green = new Color(64, 255, 64);
		Color yellow = new Color(255, 255, 64);
		Color red = new Color(255, 64, 64);
		Color color;
		
		EntityPokemonStats stats = player.getData().getStats();
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
	
	private void setExpBar() {
		int level = player.getData().getLevel();
		EntityPokemonExpHandler expHandler = player.getData().getExp();
		
		int levelExp = ExpHandler.instance().get(expHandler.getType()).get(level);
		int nextLevelExp = ExpHandler.instance().get(expHandler.getType()).get(level == 100 ? level : level+1);
		
		int actualExp = expHandler.getExp() - levelExp;
		int maxExp = nextLevelExp - levelExp;

		expBarLimit = (int) ((expBarBounds.getWidth()*1f) * ((actualExp*1f)/(maxExp*1f)));
		if(expBarBounds.getWidth() - expBarLimit < ContentSettings.tileResize)
			expBarLimit = (int) (expBarBounds.getWidth() - ContentSettings.tileResize);
		
//		System.out.println("LV: " + levelExp + ", NX: " + nextLevelExp + ", AC: " + actualExp + ", MX: " + maxExp);
//		System.out.println("W: " + expBarBounds.getWidth() + ", L: " + expBarLimit);
	}
	
	private void setHp() {
		EntityPokemonStats stats = player.getData().getStats();
		int hp = stats.get(Stats.HP);
		int hpTot = stats.get(Stats.TOT_HP);
		
		this.hp = hp + "/" + hpTot;
		hpPoint = new Point(labelPoint.x + 172*labelImage.getWidth()/185 - hpFont.getWidth(this.hp), labelPoint.y + 50*labelImage.getHeight()/66);
	}
}
