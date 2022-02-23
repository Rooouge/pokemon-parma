package core.fonts;

import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class Font extends java.awt.Font {

	private final FontRenderContext frc;
	
	
	public Font(java.awt.Font font) {
		super(font.getName(), font.getStyle(), font.getSize());
		
		frc = new FontRenderContext(new AffineTransform(), true, true);
	}
	
	
	public int width(String str) {
		return (int)(getStringBounds(str, frc)).getWidth();
	}
	
	public int height() {
		return height("SAMPLE");
	}
	
	public int height(String str) {
		return (int)(getStringBounds(str, frc)).getHeight();
	}
}
