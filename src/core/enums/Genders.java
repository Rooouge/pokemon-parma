package core.enums;

import java.awt.Color;
import java.util.Random;

import jutils.gui.Colors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Genders {

	MALE('M', new Color(0,96,255)),
	FEMALE('F', new Color(255,32,32)),
	UNKNOWN(' ', Colors.GRAY_32);
	
	
	private final char code;
	private final Color color;
	
	
	public static Genders randomWild() {
		return new Random().nextInt(2) == 0 ? MALE : FEMALE;
	}
}
