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
import core.gui.screen.content.pokedex.Pokedex;
import core.obj.pokemon.pokedex.PokemonPokedex;

public class PokedexPainter extends Painter<Pokedex> {

	private TiledImage background;
	private core.obj.pokemon.pokedex.Pokedex pokedex;
	private final int listX;
	private final int firstListY;
	private final Font listFont;
	private int listIndex;
	private int listSelected;
	private int tempIndex;
	private int tempSelected;
	private final TiledImage unknown;
	private final TiledImage caught;
	private final Color defColor;
	private final Color selColor;
	private TiledImage image;
	
	
	public PokedexPainter(Pokedex parent) throws IOException {
		super(parent);
		
		background = ImageHandler.getImage("pokedex_background", "pokedex");
		pokedex = core.obj.pokemon.pokedex.Pokedex.instance();
		
		int tile = ContentSettings.tileSize;

		listFont = new Font(Fonts.SCRIPT_TEXT_FONT.deriveFont(3f/4f*Fonts.SCRIPT_TEXT_FONT.getSize2D()));
		listX = (ContentSettings.horTiles - 5)*tile;
		firstListY = 2*tile - listFont.height();
		
		defColor = Color.decode("#0088FF");
		selColor = Color.decode("#00BBFF");
		
		unknown = ImageHandler.getUnknownImage();
		caught = ImageHandler.resize(ImageHandler.getImage("caught", "pokedex").getImage(), 1f/(ContentSettings.tileResize*2f));
		
		listIndex = 1;
		listSelected = 0;
		tempIndex = listIndex;
		tempSelected = listSelected;
		refreshImage();
	}
	

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(background.getImage(), 0, 0, null);
		
		TiledImage drawImage = unknown;
		
		for(int i = listIndex; i < listIndex + 7; i++) {
			int y = firstListY + ContentSettings.tileSize*(i-listIndex);
			TiledImage stateImage = null;
			String str = "---";
			PokemonPokedex p = pokedex.get(i);
			PokedexPokemonStates state = p.getData().getState();
			boolean foundCond = state.found();
			
			
			if(foundCond) {
				str = p.getData().getBaseData().getName();
				
				if(state.equals(PokedexPokemonStates.CAUGHT))
					stateImage = caught;
			}
			
			if(listSelected == i - listIndex) {
				g.setFont(listFont);
				g.setColor(selColor);
				
				if(foundCond)
					drawImage = image;
			} else {
				g.setFont(listFont);
				g.setColor(defColor);
			}
			
			if(stateImage != null)
				g.drawImage(stateImage.getImage(), listX - ContentSettings.tileSize/4*3, y - ContentSettings.tileSize/9*4, null);
			
			g.drawString(i + ") " + str, listX, y);
		}

		g.drawImage(drawImage.getImage(), (int) (ContentSettings.tileSize*5f/4f), (int) (ContentSettings.tileSize*5f/2f), null);
	}
	
	
	public void scrollDown() {
//		System.out.println("D.PRE:   " + listSelected + " --- " + listIndex + " (" + pokedex.size() + ")");
		
		tempIndex = listIndex;
		tempSelected = listSelected;
		
		if(tempSelected < 6) {
			if((tempIndex < 3 && (tempSelected < 3 || tempSelected <= tempIndex)) || (tempIndex == pokedex.size() - 7))
				tempSelected++;
			else 
				tempIndex++;
		}
		
		refreshImage();
		
//		System.out.println("D.POST:   " + listSelected + " --- " + listIndex);
	}
	
	public void scrollUp() {
//		System.out.println("U.PRE:   " + listSelected + " --- " + listIndex + " (" + pokedex.size() + ")");
		
		tempIndex = listIndex;
		tempSelected = listSelected;
		
		if(tempSelected > 0) {
			if((tempIndex > pokedex.size() - 10 && (tempSelected > 3 || tempSelected >= tempIndex)) || (tempIndex == 1))
				tempSelected--;
			else
				tempIndex--;
		}
		
		refreshImage();
		
//		System.out.println("U.POST:   " + listSelected + " --- " + listIndex);
	}

	public void fastDown() {
		tempIndex = listIndex;
		tempSelected = listSelected;
		
		tempIndex += 7;
		tempSelected = 3;
		
		if(tempIndex > pokedex.size() - 7) {
			tempSelected = 6;
			tempIndex = pokedex.size() - 7;
		}
		
		refreshImage();
		
//		System.out.println("FD.POST:   " + listSelected + " --- " + listIndex + " (" + pokedex.size() + ")");
	}
	
	public void fastUp() {
		tempIndex = listIndex;
		tempSelected = listSelected;
		
		tempIndex -= 7;
		tempSelected = 3;
		
		if(tempIndex < 1) {
			tempIndex = 1;
			tempSelected = 0;
		}
		
		refreshImage();
		
//		System.out.println("FU.POST:   " + listSelected + " --- " + listIndex + " (" + pokedex.size() + ")");
	}
	
	
	private void refreshImage() {
		try {
			image = ImageHandler.getPokemonEnemyImage(get().getData().getBaseData().getId(), false);
			listIndex = tempIndex;
			listSelected = tempSelected;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public PokemonPokedex get() {
		return pokedex.get(tempIndex + tempSelected);
	}
}
