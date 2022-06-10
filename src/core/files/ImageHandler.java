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
		
		BufferedImage subImage = input.getSubimage(shiny ? (int) (input.getWidth()*2f/5f) : 0, 0, input.getWidth()/5, input.getHeight());
		return resize(subImage, 3f/4f);
	}
	
	public TiledImage getPokemonPlayerImage(int id, boolean shiny) throws IOException {
		BufferedImage input = ImageIO.read(getImageFile("" + id, "pokemon"));
		
		BufferedImage subImage = input.getSubimage(shiny ? (int) (input.getWidth()*3f/5f) : (int) (input.getWidth()*1f/5f), 0, input.getWidth()/5, input.getHeight());
		return resize(subImage, 3f/4f);
	}
	
	public TiledImage getUnknownImage() throws IOException {
		BufferedImage input = ImageIO.read(getImageFile("unknown", "pokemon"));
		
		return resize(input, 3f/4f);
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
		float resize = ContentSettings.tileResize * refactor;
		
//		System.out.println("--- " + input.getWidth() + " - " + input.getHeight() + " [R: " + refactor + ", " + resize + "]");
		BufferedImage image = new BufferedImage((int) (input.getWidth()*resize), (int) (input.getHeight()*resize), input.getType());
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
	
	
	public boolean isTransparent(int pixel) {
		return (pixel>>24) == 0x00;
	}
	
	/*
	 * Animations
	 */
	
	public BufferedImage[] getAnimation(String resName, String dir) throws IOException {
		BufferedImage[] animation;
		TiledImage animationRaw = ImageHandler.getImage(resName, dir);
		BufferedImage img = new BufferedImage(animationRaw.getWidth(), animationRaw.getHeight(), BufferedImage.TYPE_INT_ARGB);
		img.getGraphics().drawImage(animationRaw.getImage(), 0, 0, null);
		animation = new BufferedImage[animationRaw.getHeight() / (ContentSettings.verTiles*ContentSettings.tileSize)];
		
		int w = animationRaw.getWidth();
		int h = animationRaw.getHeight()/animation.length;
		
//		System.out.println("W: " + animationRaw.getWidth() + ", H: " + animationRaw.getHeight() + " --- L: " + animation.length);
		for(int i = 0; i < animation.length; i++) {
//			System.out.println(i + ")   0, " + ((i)*animationRaw.getHeight()/animation.length)/ContentSettings.tileResize + ", " + animationRaw.getWidth()/ContentSettings.tileResize + ", " + (animationRaw.getHeight()/animation.length)/ContentSettings.tileResize);
//			animation[i] = animationRaw.getImage().getSubimage(0, (i)*animationRaw.getHeight()/animation.length, animationRaw.getWidth(), animationRaw.getHeight()/animation.length);
			
			animation[i] = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics g = animation[i].getGraphics();
			g.drawImage(img.getSubimage(0, (i)*img.getHeight()/animation.length, w, h), 0, 0, null);
		}
		
		return animation;
	}
}
