package core.obj.scripts.actions;

import java.awt.font.FontRenderContext;

import javax.sound.sampled.Clip;

import core.files.SoundsHandler;
import core.fonts.Font;
import core.fonts.Fonts;
import core.obj.scripts.ScriptAction;
import jutils.asserts.Assert;
import jutils.asserts.AssertException;
import jutils.global.Global;
import lombok.Getter;

public class TextAction extends ScriptAction {
	
	public static final String MAX_WIDTH = "textscript-max-width";
	
	@Getter
	private final String line1;
	@Getter
	private final String line2;
	private final Clip sound;
	
	
	public TextAction(String text) throws AssertException {
		super(false, NO_DELAY);
		
		sound = SoundsHandler.get(SoundsHandler.PRESS);
		
		Font font = Fonts.SCRIPT_TEXT_FONT;
		FontRenderContext frc = font.getFrc();
		int maxWidth = Global.get(MAX_WIDTH, Integer.class);
		int stringWidth = (int) font.getStringBounds(text, frc).getWidth();
		Assert.isFalse(stringWidth > maxWidth*2, "[The given text is too long/width {extra: " + calculateExtraString(text, maxWidth*2, font) + "}]");
		
		int i = text.length();
		String temp = text;
		while(font.getStringBounds(temp.substring(0, i), frc).getWidth() > maxWidth) {
			temp = temp.substring(0, i);
			
			do {
				i--;
			} while(temp.charAt(i) != ' ');
		}
		
		String line1t = text.substring(0, i).trim();
		String line2t = text.substring(i).trim();
		int line2w = (int) font.getStringBounds(line2t, frc).getWidth();
		boolean flag = false;
		
		while(line2w > maxWidth) {
			flag = true;
			i++;
			line1t = text.substring(0, i).trim();
			line2t = text.substring(i).trim();
			line2w = (int) font.getStringBounds(line2t, frc).getWidth();
		}
		
		if(flag)
			line1t += "_";
		
		line1 = line1t;
		line2 = line2t;
//		System.out.println(line1 + ": " + font.getStringBounds(line1, frc).getWidth() + " - " + maxWidth);
//		System.out.println(line2 + ": " + font.getStringBounds(line2, frc).getWidth() + " - " + maxWidth);
	}
	
	
	private String calculateExtraString(String text, int max, Font font) {
		int i = text.length()+1;
		
		do {
			i--;
		} while(font.getStringBounds(text.substring(0, i), font.getFrc()).getWidth() > max);
		
		return text.substring(i);
	}
	
	@Override
	public void execute() {
		super.execute();
		
		SoundsHandler.playSound(sound);
	}
}
