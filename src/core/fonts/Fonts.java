package core.fonts;

import java.awt.GraphicsEnvironment;

import core.Log;
import core.files.FileHandler;
import core.gui.screen.content.ContentSettings;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Fonts {

	public Font SCRIPT_TEXT_FONT;
	public Font BATTLE_FONT;
	public Font BATTLE_OPTIONS_FONT;
	
	
	public void init() throws Exception {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		java.awt.Font font = Font.createFont(Font.TRUETYPE_FONT, FileHandler.getFile("fonts", "exploration-1", "ttf"));
		ge.registerFont(font);
		Log.log("Registered Font '" + font.getName() + "'");
		SCRIPT_TEXT_FONT = new Font(new java.awt.Font(font.getName(), Font.TRUETYPE_FONT, 12*ContentSettings.tileResize));
		
		font = Font.createFont(Font.TRUETYPE_FONT, FileHandler.getFile("fonts", "Power Green", "ttf"));
		ge.registerFont(font);
		Log.log("Registered Font '" + font.getName() + "'");
		BATTLE_FONT = new Font(new java.awt.Font(font.getName(), Font.TRUETYPE_FONT, 6*ContentSettings.tileResize));
		
		BATTLE_OPTIONS_FONT = new Font(BATTLE_FONT.deriveFont(3f*Fonts.BATTLE_FONT.getSize2D()/2f));
	}
}
