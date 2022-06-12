package core.enums;

import java.util.Random;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Natures {

	LONELY(Stats.ATK, Stats.DEF),
	BRAVE(Stats.ATK, Stats.SPEED),
	ADAMANT(Stats.ATK, Stats.SP_ATK),
	NAUGHTY(Stats.ATK, Stats.SP_DEF),
	BOLD(Stats.DEF, Stats.ATK),
	RELAXED(Stats.DEF, Stats.SPEED),
	IMPISH(Stats.DEF, Stats.SP_ATK),
	LAX(Stats.DEF, Stats.SP_DEF),
	TIMID(Stats.SPEED, Stats.ATK),
	HASTY(Stats.SPEED, Stats.DEF),
	JOLLY(Stats.SPEED, Stats.SP_ATK),
	NAIVE(Stats.SPEED, Stats.SP_DEF),
	MODEST(Stats.SP_ATK, Stats.ATK),
	MILD(Stats.SP_ATK, Stats.DEF),
	QUIET(Stats.SP_ATK, Stats.SPEED),
	RASH(Stats.SP_ATK, Stats.SP_DEF),
	CALM(Stats.SP_DEF, Stats.ATK),
	GENTLE(Stats.SP_DEF, Stats.DEF),
	SASSY(Stats.SP_DEF, Stats.SPEED),
	CAREFUL(Stats.SP_DEF, Stats.SP_ATK),
	QUIRKY(null,null),
	HARDY(null,null),
	SERIOUS(null,null),
	BASHFUL(null,null),
	DOCILE(null,null);
	
	
	private final Stats buff;
	private final Stats nerf;
	
	
	public static Natures random() {
		return values()[new Random().nextInt(values().length)];
	}
	
	
	public double getModifier(Stats stat) {
		if(stat.equals(buff))
			return 1.1;
		if(stat.equals(nerf))
			return 0.9;
		
		return 1.0;
	}
}
