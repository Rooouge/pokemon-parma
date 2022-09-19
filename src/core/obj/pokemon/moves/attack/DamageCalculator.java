package core.obj.pokemon.moves.attack;

import java.util.Random;

import core.enums.Stats;
import core.events.battle.BattleMap;
import core.obj.pokemon.battle.BattlePokemon;
import core.obj.pokemon.battle.BattlePokemonData;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DamageCalculator {

	public int calculate(BattleMap map, AttackMove move, Stats attackStat, Stats defenseStat) {
		/**
		 * (((((( 2L / 5 ) + 2 ) * A * P ) / D ) / 50 ) + 2 ) * M * STAB * R / 255
		 * 
		 * L = the Level of the attacking pokemon
		 * A = the effective applicable Attack power of the attacking pokemon
		 * P = the effective Power of the move used
		 * D = the effective applicable Defense of the pokemon hit by the attack
		 * M = Multipliers, which means type advantages and nothing else
		 * STAB = Same Type Attack Bonus.  If you use a move with a pokemon which
		 * is the same type as that pokemon, the damage goes up by 1.5x.  So STAB
		 * is equal to 1.5 when it applies and 1 when it does not apply.
		 * R = a random number from 217 to 255 inclusive
		 * 
		 * round UP final division
		 */
		
		BattlePokemon attacker = map.get(BattleMap.ATK, BattlePokemon.class);
		BattlePokemonData atData = attacker.getData();
		BattlePokemon defender = map.get(BattleMap.DEF, BattlePokemon.class);
		BattlePokemonData dfData = defender.getData();
//		System.out.println("Atk: " + atData.getEntityData().getDisplayName() + ", Def: " + dfData.getEntityData().getDisplayName());
		

		int intValue = 0;
		boolean critical = false;
		double M = move.getTypeModifier(dfData);
//		System.out.println("M: " + M);
		if(M > 0.0) {			
			double L = atData.getEntityData().getLevel();
			double A = atData.getEntityData().getStats().get(Stats.SP_ATK);
			double P = move.getDamage();
			double D = dfData.getEntityData().getStats().get(Stats.SP_DEF);
			double STAB = move.getSameTypeAttackBonus(atData);
			double R = new Random().nextInt(39) + 217;
			
	//		System.out.println("L: " + L);
	//		System.out.println("A: " + A);
	//		System.out.println("P: " + P);
	//		System.out.println("D: " + D);
	//		System.out.println("STAB: " + STAB);
	//		System.out.println("R: " + R);
			
			/*
			 * At this point I need to check if any instrument or moves influences the variables
			 */
			
			/*
			 * If your total after dividing by 50 is more than 997, you take
			 * 997 instead of what you have.  Your total will NEVER be more than 997
			 */
			double value = Math.floor(Math.floor((Math.floor(((2*L) / 5) + 2) * A * P) / D) / 50);
			if(value > 997.0)
				value = 997.0;
			
			intValue = (int) Math.ceil((value + 2) * M * STAB * R / 255);
			
			/*
			 * Attempt critical hit: 2x damage
			 */
			critical = criticalAttempt();
			map.put(BattleMap.IS_CRITICAL_HIT, critical);
			
			if(critical)
				intValue *= 2;
		}
		
		map.put(BattleMap.DAMAGE_DESCRIPTION, getDamageDescription(M, critical));
		
		return intValue;
	}
	
	
	private boolean criticalAttempt() {
		Random random = new Random();
		int threshold = random.nextInt(64);
		int value = random.nextInt(256);
		
		return value < threshold;
	}
	
	private String[] getDamageDescription(double M, boolean critical) {
		if(M == 0.0)
			return new String[] {"Non ha alcun","effetto..."};
		if(M > 1.0)
			return new String[] {"è".toUpperCase() + " Superefficace!",""};
		if(M < 1.0)
			return new String[] {"Non è molto", "efficace."};
		
		return null;
	}
}
