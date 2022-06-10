package core.obj.pokemon.moves.attack;

import java.util.function.Function;

import core.Log;
import core.enums.MoveTypes;
import core.enums.Types;
import core.events.battle.BattleMap;
import core.obj.pokemon.battle.BattlePokemonData;
import core.obj.pokemon.moves.Move;
import lombok.Getter;

public abstract class AttackMove extends Move {

	protected final int damage;
	@Getter
	protected Function<BattleMap, Integer> damageFunction;
	
	
	protected AttackMove(String name, Types type, MoveTypes moveType, int precision, int pp, int damage) {
		super(name, type, moveType, precision, pp);
		this.damage = damage;
		
		damageFunction = new Function<BattleMap, Integer>() {
			
			@Override
			public Integer apply(BattleMap map) {
				return calculateDamage(map);
			}
			
		};
	}
	
	
	public AttackMove withDamageFunction(Function<BattleMap, Integer> damageSupplier) {
		this.damageFunction = damageSupplier;
		return this;
	}
	
	
	public double getTypeModifier(BattlePokemonData data) {
		Types dfType1 = data.getEntityData().getBaseData().getMainType();
		Types dfType2 = data.getEntityData().getBaseData().getSecondaryType();
		double M1 = type.getMultiplierForType(dfType1);
		double M2 = type.getMultiplierForType(dfType2);
		double M = M1*M2;

		Log.log("T: " + type);
		Log.log("1: " + dfType1 + " " + M1);
		Log.log("2: " + dfType2 + " " + M2);
		Log.log("M: " + M);
		
		return M;
	}
	
	public double getSameTypeAttackBonus(BattlePokemonData data) {
		Types dfType1 = data.getEntityData().getBaseData().getMainType();
		Types dfType2 = data.getEntityData().getBaseData().getSecondaryType();
		double STAB = (type.equals(dfType1) || type.equals(dfType2)) ? 1.5 : 1.0;
		
		return STAB;
	}
	
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
	public abstract int calculateDamage(BattleMap map);
}
