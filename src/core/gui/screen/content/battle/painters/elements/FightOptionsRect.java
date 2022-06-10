package core.gui.screen.content.battle.painters.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import core.enums.Types;
import core.files.ImageHandler;
import core.fonts.Font;
import core.fonts.Fonts;
import core.gui.GUIUtils;
import core.gui.screen.content.ContentSettings;
import core.obj.pokemon.entity.EntityPokemon;
import core.obj.pokemon.entity.EntityPokemonMoves;
import core.obj.pokemon.moves.Dictionary;
import core.obj.pokemon.moves.Move;

public class FightOptionsRect {

	private final EntityPokemonMoves moves;
	private final int margin;
	private final Rectangle backgroundRect;
	private final Color backgroundColor;
	private final Rectangle topBorderRect;
	private final Color borderColor;
	private final Rectangle leftRect;
	private final Rectangle rightRect;
	private final int arcW;
	private final int arcH;
	private final Color rectColor;
	private final String[] options;
	private final Font optionsFont;
	private final Color optionsColor;
	private final Point[] points;
	private final Polygon[] selectors;
	private final Color selectorColor;
	private final int selectorBorders;
	private final Color selectorBorderColor;
	private int selected;
	private final Point[] rightPoints;
	private String ppLabel;
	private BufferedImage typeImage;
	
	
	public FightOptionsRect(EntityPokemon pokemon) {
		this.moves = pokemon.getData().getMoves();
		
		Dimension dim = ContentSettings.dimension;
		int tile = ContentSettings.tileSize;
		int resize = ContentSettings.tileResize;
		this.margin = tile/8;
		rectColor = Color.white;
		arcW = margin*2;
		arcH = arcW;
		
		backgroundRect = new Rectangle(0, dim.height - 2*tile, dim.width, 2*tile);
		backgroundColor = new Color(70, 64, 76);
		
		topBorderRect = new Rectangle(backgroundRect.x, backgroundRect.y, backgroundRect.width, resize);
		borderColor = Color.black;
		
		
		leftRect = new Rectangle(backgroundRect.x + margin, backgroundRect.y + margin, 3*backgroundRect.width/4 - 2*margin, backgroundRect.height - 2*margin);
		rightRect = new Rectangle(3*dim.width/4 + margin, backgroundRect.y + margin, backgroundRect.width/4 - 2*margin, backgroundRect.height - 2*margin);
		
		
		options = pokemon.getData().getMoves().stream().map(m -> Dictionary.instance().get(m.getName()).toUpperCase()).toArray(String[]::new);
		optionsFont = Fonts.BATTLE_OPTIONS_FONT;
		optionsColor = Color.black;
		
		points = new Point[options.length];
		selectors = new Polygon[options.length];
		int h = optionsFont.height();
		
		String longetsRight = "";
		if(options.length == 4) {
			longetsRight = options[1].length() > options[3].length() ? options[1] : options[3];
		} else if(options.length >= 2) {
			longetsRight = options[1];
		}
		
		int rw = optionsFont.getWidth(longetsRight);		
		
		for(int i = 0; i < options.length; i++) {
			switch (i) {
			case 0:
				points[0] = new Point(leftRect.x + 4*margin, leftRect.y + (int) (2*leftRect.getHeight()/5));
				break;
			case 1:
				points[1] = new Point(leftRect.x + (int) (leftRect.getWidth()) - 4*margin - rw, points[0].y);
				break;
			case 2:
				points[2] = new Point(points[0].x, leftRect.y + (int) (4*leftRect.getHeight()/5));
				break;
			case 3:
				points[3] = new Point(points[1].x, points[2].y);
				break;
			default:
				break;
			}
			
			selectors[i] = new Polygon(
					new int[] {points[i].x - 3*margin, points[i].x - margin, points[i].x - 3*margin}, 
					new int[] {points[i].y - 4*h/5, points[i].y - h/2, points[i].y - h/5}, 
					3
			);
		}

		selectorColor = Color.red;
		selectorBorderColor = Color.black;
		selectorBorders = resize/2;
		selected = 0;
		
		rightPoints = new Point[4];
		rightPoints[0] = new Point(rightRect.x + margin, rightRect.y + (int) (2*rightRect.getHeight()/5));
		rightPoints[2] = new Point(rightPoints[0].x, rightRect.y + (int) (4*rightRect.getHeight()/5));
		refreshRight();
	}
	
	
	public void paint(Graphics2D g) {
		GUIUtils.fillRect(g, backgroundRect, backgroundColor);
		GUIUtils.fillRoundedRect(g, leftRect, rectColor, arcW, arcH);
		GUIUtils.fillRoundedRect(g, rightRect, rectColor, arcW, arcH);
		GUIUtils.fillRect(g, topBorderRect, borderColor);
		
		g.setColor(optionsColor);
		g.setFont(optionsFont);
		for(int i = 0; i < options.length; i++) {
			g.drawString(options[i], points[i].x, points[i].y);
		}
		
		g.setColor(selectorColor);
		g.fillPolygon(selectors[selected]);
		g.setColor(selectorBorderColor);
		for(int i = 0; i < selectorBorders; i++) {
			Polygon s = selectors[selected];
			int[] xs = s.xpoints;
			int[] ys = s.ypoints;
			int n = s.npoints;
			
			int[] x = new int[n];
			int[] y = new int[n];
			x[0] = xs[0] + i;
			x[1] = xs[1] - i;
			x[2] = xs[2] + i;
			y[0] = ys[0] + i;
			y[1] = ys[1];
			y[2] = ys[2] - i;
			
			g.drawPolygon(x, y, n);
		}
		
		for(int i = 0; i < rightPoints.length; i++) {
			switch (i) {
			case 0:
				g.drawString("PP", rightPoints[i].x, rightPoints[i].y);
				break;
			case 1:
				g.drawString(ppLabel, rightPoints[i].x, rightPoints[i].y);
				break;
			case 2:
				g.drawString("TIPO", rightPoints[i].x, rightPoints[i].y);
				break;
			default:
				if(rightPoints[i] != null)
					g.drawImage(typeImage, rightPoints[i].x, rightPoints[i].y, null);
				break;
			}
		}
	}
	
	
	public void down() {
		if(selected < 2 && options.length > 2) {
			selected+=2;
			refreshRight();
		}
	}
	
	public void up() {
		if(selected > 1) {
			selected -=2;
			refreshRight();
		}
	}
	
	public void left() {
		if(selected % 2 != 0) {
			selected--;
			refreshRight();
		}
	}
	
	public void right() {
		if(selected % 2 == 0 && options.length % 2 == 0) {
			selected++;
			refreshRight();
		}
	}
	
	private void refreshRight() {
		Move move = moves.get(selected);
		ppLabel = move.getCurrentPp() + "/" + move.getPp();
		rightPoints[1] = new Point(rightRect.x + (int) rightRect.getWidth() - margin - optionsFont.getWidth(ppLabel), rightPoints[0].y);
		
		Types type = move.getType();
		try {
			BufferedImage temp = ImageHandler.getImage(type.name(), "types").getImage();
			
			int w = (int) (rightRect.getWidth()/2) - margin;
			int h = optionsFont.height();
			
			typeImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics g = typeImage.getGraphics();
			g.drawImage(temp, 0, 0, w, h, null);
			
			rightPoints[3] = new Point((int) (rightRect.x + rightRect.getWidth()/2), rightPoints[2].y - optionsFont.height());
		} catch (IOException e) {
			e.printStackTrace();
			typeImage = null;
			rightPoints[3] = null;
		}
	}
	
	
	public Move getSelected() {
		return moves.get(selected);
	}
}
