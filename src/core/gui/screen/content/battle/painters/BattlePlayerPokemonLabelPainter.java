package core.gui.screen.content.battle.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;

import core.enums.Stats;
import core.files.ImageHandler;
import core.fonts.Font;
import core.fonts.Fonts;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.battle.painters.superclasses.BattlePokemonLabelPainter;
import core.obj.entities.player.Player;
import core.obj.pokemon.entity.EntityPokemonExpHandler;
import core.obj.pokemon.entity.EntityPokemonStats;
import core.obj.pokemon.entity.ExpHandler;
import jutils.gui.Colors;

public class BattlePlayerPokemonLabelPainter extends BattlePokemonLabelPainter {

	protected String hp;
	protected Point hpPoint;
	protected final Font hpFont;

	protected final Rectangle expBarBounds;
	protected final Color expBarBackground;
	protected final Color expBarColor;
	protected int expBarLimit;
	
	
	public BattlePlayerPokemonLabelPainter(Battle parent) throws IOException {
		super(parent);
		
		pokemon = Player.instance().getTeam().get(0);
		
		labelImage = ImageHandler.resize(ImageHandler.getImage("battle_player_pkm_label", "battle").getImage(), 0.5f/ContentSettings.tileResize);
		labelPoint = new Point(ContentSettings.dimension.width - labelImage.getWidth() - ContentSettings.tileSize/2, ContentSettings.dimension.height - 2*ContentSettings.tileSize - labelImage.getHeight() - 4*ContentSettings.tileResize);
		
		hpBarBounds = new Rectangle(labelPoint.x + 44*labelImage.getWidth()/185, labelPoint.y + 36*labelImage.getHeight()/66, (169-44+ContentSettings.tileResize/2)*labelImage.getWidth()/185, (39-36+ContentSettings.tileResize/2)*labelImage.getHeight()/66);
		hpBarBackground = Colors.GRAY_64;
		
		expBarBounds = new Rectangle(labelPoint.x + 36*labelImage.getWidth()/185, labelPoint.y + 58*labelImage.getHeight()/66, (161-36+ContentSettings.tileResize/2)*labelImage.getWidth()/185, (61-58+ContentSettings.tileResize/2)*labelImage.getHeight()/66);
		expBarColor = new Color(150,255,32);
		expBarBackground = Colors.GRAY_64;
		
		
		name = pokemon.getData().getDisplayName();
		namePoint = new Point(labelPoint.x + 32*labelImage.getWidth()/185, labelPoint.y + 20*labelImage.getHeight()/66);
		nameFont = new Font(Fonts.BATTLE_FONT.deriveFont(3f*Fonts.BATTLE_FONT.getSize2D()/2f));
		
		gender = pokemon.getData().getGender();
		genderPoint = new Point(namePoint.x + nameFont.getWidth(name) + 2*ContentSettings.tileResize, namePoint.y);
		genderFont = new Font(nameFont.deriveFont(nameFont.getSize2D()/2f).deriveFont(Font.BOLD));
		
		level = "Lv" + pokemon.getData().getLevel();
		levelFont = new Font(nameFont.deriveFont(2f*nameFont.getSize2D()/3f));
		levelPoint = new Point(labelPoint.x + 172*labelImage.getWidth()/185 - levelFont.getWidth(level), namePoint.y);
		
		hpFont = new Font(levelFont.deriveFont(2f*levelFont.getSize2D()/3f));
		
		refresh();
	}
	
	
	@Override
	public void paint(Graphics2D g) {
		// Exp bar
		g.setColor(expBarBackground);
		g.fillRect(expBarBounds.x, expBarBounds.y, expBarBounds.width, expBarBounds.height);
		g.setColor(expBarColor);
		g.fillRect(expBarBounds.x, expBarBounds.y, expBarLimit, expBarBounds.height);
		
		super.paint(g);
		
		// HP
		g.setColor(Colors.GRAY_32);
		g.setFont(hpFont);
		g.drawString(hp, hpPoint.x, hpPoint.y);
	}
	
	
	@Override
	public void refresh() {
		setHpBar();
		setExpBar();
		setHp();
	}
	
	private void setExpBar() {
		int level = pokemon.getData().getLevel();
		EntityPokemonExpHandler expHandler = pokemon.getData().getExp();
		
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
	
	protected void setHp() {
		EntityPokemonStats stats = pokemon.getData().getStats();
		int hp = stats.get(Stats.HP);
		int hpTot = stats.get(Stats.TOT_HP);
		
		this.hp = hp + "/" + hpTot;
		hpPoint = new Point(labelPoint.x + 172*labelImage.getWidth()/185 - hpFont.getWidth(this.hp), labelPoint.y + 50*labelImage.getHeight()/66);
	}
}
