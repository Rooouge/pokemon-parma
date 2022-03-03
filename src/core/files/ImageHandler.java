package core.files;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import core.gui.screen.content.ContentSettings;
import jutils.config.Config;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ImageHandler {

	//Images
	public final ImageIcon ICON = new ImageIcon(Config.getValue("icon"));
	
	/*
	 * Getting files
	 */
	
	public ImageIcon getImageIcon(String resName) {
		return new ImageIcon(Config.getValue("images") + resName + ".png");
	}
	
	
	public File getImageFile(String resName, String subFolder) {
		return new File(Config.getValue("images") + subFolder + "/" + resName + ".png");
	}
	
	public TiledImage getImage(String resName, String subfolder) throws IOException {
		BufferedImage input = ImageIO.read(getImageFile(resName, subfolder));
		return resize(input);
	}
	
	public TiledImage getMapImage(String resName) throws IOException {
		BufferedImage input = ImageIO.read(getImageFile(resName, "maps"));
		return resize(input);
	}
	
	public TiledImage getEntityExplorationImage(String entityName) throws IOException {
		BufferedImage input = ImageIO.read(getImageFile(entityName + "_exploration", "entities/overworld"));
		return resize(input);
	}
	
	public TiledImage getAutotileImage(String resName) throws IOException {
		BufferedImage input = ImageIO.read(FileHandler.getFile("autotiles", resName, "png"));
		return resize(input);
	}
	
	public TiledImage getPokemonEnemyImage(int id, boolean shiny) throws IOException {
		BufferedImage input = ImageIO.read(getImageFile("" + id, "pokemon"));
		
		return resize(input.getSubimage(shiny ? (int) (input.getWidth()*2f/5f) : 0, 0, input.getWidth()/5, input.getHeight()), 3f/4f);
	}
	
	/*
	 * Elaborating images
	 */
	
	public TiledImage resize(BufferedImage input) {
		int resize = ContentSettings.tileResize;
		
		BufferedImage image = new BufferedImage(input.getWidth()*resize, input.getHeight()*resize, input.getType());
		Graphics g = image.getGraphics();
		
		g.drawImage(input, 0, 0, image.getWidth(), image.getHeight(), null);
		g.dispose();
		
		return new TiledImage(optimize(image));
	}
	
	public TiledImage resize(BufferedImage input, float refactor) {
		int resize = (int) (ContentSettings.tileResize * refactor);
		
		BufferedImage image = new BufferedImage(input.getWidth()*resize, input.getHeight()*resize, input.getType());
		Graphics g = image.getGraphics();
		
		g.drawImage(input, 0, 0, image.getWidth(), image.getHeight(), null);
		g.dispose();
		
		return new TiledImage(optimize(image));
	}
	
	
	public TiledImage horizontalFlip(TiledImage source) {
		return new TiledImage(horizontalFlip(source.getImage()));
	}
	
	public BufferedImage horizontalFlip(BufferedImage source) {
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-source.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return op.filter(source, null);
	}
	
	public BufferedImage optimize(BufferedImage image) {
		GraphicsConfiguration gfxConfig = GraphicsEnvironment
				.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice()
				.getDefaultConfiguration();
		
		if(image.getColorModel().equals(gfxConfig.getColorModel()))
			return image;
		
		BufferedImage optImage = gfxConfig.createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());
		Graphics g = optImage.createGraphics();
		
		g.drawImage(image, 0, 0, null);
		g.dispose();
		
		return optImage;
	}
	
	
}
