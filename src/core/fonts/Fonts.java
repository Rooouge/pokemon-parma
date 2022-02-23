package core.fonts;

import java.awt.GraphicsEnvironment;

import core.Log;
import core.files.FileHandler;
import core.gui.screen.content.ContentSettings;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Fonts {

	public static Font SCRIPT_TEXT_FONT;
	
	
	public void init() throws Exception {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		java.awt.Font font = Font.createFont(Font.TRUETYPE_FONT, FileHandler.getFile("fonts", "exploration", "ttf"));
		ge.registerFont(font);
		Log.log("Registered Font '" + font.getName() + "'");
		SCRIPT_TEXT_FONT = new Font(new java.awt.Font("Power Clear", Font.TRUETYPE_FONT, 12*ContentSettings.tileResize));
	}
}
