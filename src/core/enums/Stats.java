package core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Stats {

	ATK("Attacco", "attack"),
	DEF("Difesa", "defense"),
	SP_ATK("Attacco speciale", "sp_attack"),
	SP_DEF("Difesa speciale", "sp_defense"),
	SPEED("Velocità", "speed"),
	HP("HP", "hp"),
	TOT_HP("TOT HP", "hp");
	
	
	private final String label;
	private final String xmlNode;
}
