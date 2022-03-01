package core.gui.screen.content.pokedex.painters;

import java.awt.Graphics2D;
import java.io.IOException;

import core.files.ImageHandler;
import core.files.TiledImage;
import core.gui.interfaces.Painter;
import core.gui.screen.content.Pokedex;
import core.obj.pokemon.pokedex.PokedexHandler;

public class PokedexPainter extends Painter<Pokedex> {

	private TiledImage background;
	private core.obj.pokemon.pokedex.Pokedex pokedex;
	private int index;
	private final int listX;
	private final int firstListY;
	
	
	public PokedexPainter(Pokedex parent) throws IOException {
		super(parent);
		
		background = ImageHandler.getImage("pokedex_background", "pokedex");
		pokedex = PokedexHandler.get();
		index = 0;
	}
	

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(background.getImage(), 0, 0, null);
		
	}

}
