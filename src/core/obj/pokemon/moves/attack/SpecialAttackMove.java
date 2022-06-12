package core.obj.pokemon.moves.attack;

import java.util.Random;

import core.enums.MoveTypes;
import core.enums.Stats;
import core.enums.Types;
import core.events.battle.BattleMap;
import core.obj.pokemon.battle.BattlePokemon;
import core.obj.pokemon.battle.BattlePokemonData;

public class SpecialAttackMove extends AttackMove {

	public SpecialAttackMove(String name, Types type, int precision, int pp, int damage) {
		super(name, type, MoveTypes.SPECIAL, precision, pp, damage);
		
	}

	
	@Override
	public int calculateDamage(BattleMap map) {
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
		
		
		double L = atData.getEntityData().getLevel();
		double A = atData.getEntityData().getStats().get(Stats.SP_ATK);
		double P = damage;
		double D = dfData.getEntityData().getStats().get(Stats.SP_DEF);
		double M = getTypeModifier(dfData);
		double STAB = getSameTypeAttackBonus(atData);
		double R = new Random().nextInt(39) + 217;
		
//		System.out.println("L: " + L);
//		System.out.println("A: " + A);
//		System.out.println("P: " + P);
//		System.out.println("D: " + D);
//		System.out.println("M: " + M);
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
		
		return (int) Math.ceil((value + 2) * M * STAB * R / 255);
	}

}
