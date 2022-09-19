package core.gui.screen.content.battle.painters.superclasses;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import core.enums.Genders;
import core.enums.Stats;
import core.files.TiledImage;
import core.fonts.Font;
import core.gui.interfaces.Painter;
import core.gui.screen.content.battle.Battle;
import core.obj.pokemon.entity.EntityPokemon;
import core.obj.pokemon.entity.EntityPokemonStats;
import jutils.gui.Colors;

public class BattlePokemonLabelPainter extends Painter<Battle> {

	protected EntityPokemon pokemon;
	protected TiledImage labelImage;
	protected Point labelPoint;
	
	protected Rectangle hpBarBounds;
	protected Color hpBarBackground;
	protected Color hpBarColor;
	protected int hpBarLimit;
	
	protected String name;
	protected Point namePoint;
	protected Font nameFont;
	
	protected Genders gender;
	protected Point genderPoint;
	protected Font genderFont;
	
	protected String level;
	protected Point levelPoint;
	protected Font levelFont;
	
	
	protected BattlePokemonLabelPainter(Battle parent) {
		super(parent);
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
	
	protected void setHpBar() {
		Color green = new Color(64, 255, 64);
		Color yellow = new Color(255, 255, 64);
		Color red = new Color(255, 64, 64);
		Color color;
		
		EntityPokemonStats stats = pokemon.getData().getStats();
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
