package core.gui.screen.content.pokedex.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import core.files.ImageHandler;
import core.files.TiledImage;
import core.fonts.Font;
import core.fonts.Fonts;
import core.gui.interfaces.Painter;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.Pokedex;
import core.obj.pokemon.pokedex.PokedexHandler;
import core.obj.pokemon.pokedex.PokemonPokedex;

public class PokedexPainter extends Painter<Pokedex> {

	private TiledImage background;
	private core.obj.pokemon.pokedex.Pokedex pokedex;
	private final int listX;
	private final int firstListY;
	private final Font listFont;
	private int listIndex;
	private int listSelected;
	private TiledImage image;
	private TiledImage shinyImage;
	
	
	public PokedexPainter(Pokedex parent) throws IOException {
		super(parent);
		
		background = ImageHandler.getImage("pokedex_background", "pokedex");
		pokedex = PokedexHandler.get();
		listIndex = 1;
		
		int tile = ContentSettings.tileSize;

		listFont = new Font(Fonts.SCRIPT_TEXT_FONT.deriveFont(3f/4f*Fonts.SCRIPT_TEXT_FONT.getSize2D()));
		listX = (ContentSettings.horTiles - 5)*tile + tile/2;
		firstListY = 2*tile - listFont.height();
		listSelected = 0;


		refreshImage();
	}
	

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(background.getImage(), 0, 0, null);
		
		
		for(int i = listIndex; i < listIndex + 7; i++) {
			PokemonPokedex p = pokedex.get(i);
			
			if(listSelected == i - listIndex) {
				g.setFont(listFont.deriveFont(Font.ITALIC));
				g.setColor(Color.decode("#CC0000"));
			} else {
				g.setFont(listFont);
				g.setColor(Color.black);
			}
			
			g.drawString(p.getData().getBaseData().getName(), listX, firstListY + ContentSettings.tileSize*(i-listIndex));
		}
		
		g.drawImage(image.getImage(), ContentSettings.tileSize, (int) (ContentSettings.tileSize*5f/2f), null);
		g.drawImage(shinyImage.getImage(), ContentSettings.tileSize*4, (int) (ContentSettings.tileSize*5f/2f), null);
	}
	
	
	public void scrollDown() {
//		System.out.println("D.PRE:   " + listSelected + " --- " + listIndex + " (" + pokedex.size() + ")");
		
		if(listSelected < 6) {
			if((listIndex < 3 && (listSelected < 3 || listSelected <= listIndex)) || (listIndex == pokedex.size() - 7))
				listSelected++;
			else 
				listIndex++;
		}

		refreshImage();
//		System.out.println("D.POST:   " + listSelected + " --- " + listIndex);
	}
	
	public void scrollUp() {
//		System.out.println("U.PRE:   " + listSelected + " --- " + listIndex + " (" + pokedex.size() + ")");
		
		if(listSelected > 0) {
			if((listIndex > pokedex.size() - 10 && (listSelected > 3 || listSelected >= listIndex)) || (listIndex == 1))
				listSelected--;
			else
				listIndex--;
		}
		
		refreshImage();
//		System.out.println("U.POST:   " + listSelected + " --- " + listIndex);
	}

	public void fastDown() {
		listIndex += 7;
		listSelected = 3;
		
		if(listIndex > pokedex.size() - 7) {
			listSelected = 6;
			listIndex = pokedex.size() - 7;
		}

		refreshImage();
//		System.out.println("FD.POST:   " + listSelected + " --- " + listIndex + " (" + pokedex.size() + ")");
	}
	
	public void fastUp() {
		listIndex -= 7;
		listSelected = 3;
		
		if(listIndex < 1) {
			listIndex = 1;
			listSelected = 0;
		}

		refreshImage();
//		System.out.println("FU.POST:   " + listSelected + " --- " + listIndex + " (" + pokedex.size() + ")");
	}
	
	
	private void refreshImage() {
		try {
			image = ImageHandler.getPokemonEnemyImage(get().getData().getBaseData().getId(), false);
			shinyImage = ImageHandler.getPokemonEnemyImage(get().getData().getBaseData().getId(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public PokemonPokedex get() {
		return pokedex.get(listIndex + listSelected);
	}
}
