package core.gui.screen.content.pokedex.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import core.enums.PokedexPokemonStates;
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
	private final TiledImage unknown;
	private TiledImage image;
	
	
	public PokedexPainter(Pokedex parent) throws IOException {
		super(parent);
		
		background = ImageHandler.getImage("pokedex_background", "pokedex");
		pokedex = PokedexHandler.get();
		listIndex = 1;
		
		int tile = ContentSettings.tileSize;

		listFont = new Font(Fonts.SCRIPT_TEXT_FONT.deriveFont(3f/4f*Fonts.SCRIPT_TEXT_FONT.getSize2D()));
		listX = (ContentSettings.horTiles - 5)*tile + tile/4;
		firstListY = 2*tile - listFont.height();
		listSelected = 0;

		unknown = ImageHandler.resize(ImageHandler.getImage("0", "pokemon").getImage(), 2f/5f);
		refreshImage();
	}
	

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(background.getImage(), 0, 0, null);
		
		TiledImage image = unknown;
		
		for(int i = listIndex; i < listIndex + 7; i++) {
			String str = "---";
			PokemonPokedex p = pokedex.get(i);
			PokedexPokemonStates state = p.getData().getState();
			boolean founded = state.founded();
			
			if(state.founded())
				str = p.getData().getBaseData().getName();
			
			
			if(listSelected == i - listIndex) {
				g.setFont(listFont);
				g.setColor(Color.decode("#CC0000"));
				
				refreshImage();
				if(founded)
					image = this.image;
			} else {
				g.setFont(listFont);
				g.setColor(Color.black);
			}
			
			g.drawString(i + ") " + str, listX, firstListY + ContentSettings.tileSize*(i-listIndex));
		}
		
		g.drawImage(image.getImage(), (int) (ContentSettings.tileSize*5f/4f), (int) (ContentSettings.tileSize*5f/2f), null);
	}
	
	
	public void scrollDown() {
//		System.out.println("D.PRE:   " + listSelected + " --- " + listIndex + " (" + pokedex.size() + ")");
		
		if(listSelected < 6) {
			if((listIndex < 3 && (listSelected < 3 || listSelected <= listIndex)) || (listIndex == pokedex.size() - 7))
				listSelected++;
			else 
				listIndex++;
		}
		
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
		
//		System.out.println("U.POST:   " + listSelected + " --- " + listIndex);
	}

	public void fastDown() {
		listIndex += 7;
		listSelected = 3;
		
		if(listIndex > pokedex.size() - 7) {
			listSelected = 6;
			listIndex = pokedex.size() - 7;
		}
		
//		System.out.println("FD.POST:   " + listSelected + " --- " + listIndex + " (" + pokedex.size() + ")");
	}
	
	public void fastUp() {
		listIndex -= 7;
		listSelected = 3;
		
		if(listIndex < 1) {
			listIndex = 1;
			listSelected = 0;
		}
		
//		System.out.println("FU.POST:   " + listSelected + " --- " + listIndex + " (" + pokedex.size() + ")");
	}
	
	
	private void refreshImage() {
		try {
			image = ImageHandler.getPokemonEnemyImage(get().getData().getBaseData().getId(), false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public PokemonPokedex get() {
		return pokedex.get(listIndex + listSelected);
	}
}
