package core.gui.screen.content.battle.animations;

import java.util.Timer;
import java.util.TimerTask;

import core.enums.GameStates;
import core.events.battle.BattleMap;
import core.files.SoundsHandler;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.battle.painters.BattleDamageDescriptionPainter;
import core.gui.screen.content.battle.painters.superclasses.BattlePokemonLabelPainter;
import core.gui.screen.painters.animations.NoGUIAnimation;
import core.obj.pokemon.battle.BattlePokemonData;

public class DamageAnimation extends NoGUIAnimation<Battle> {

	private static final int SPEED = 2;
	protected final BattlePokemonData data;
	protected final BattlePokemonLabelPainter ref;
	protected int flag;
	protected boolean forceOver;
	
	
	public DamageAnimation(Battle parent, BattlePokemonData data, int damage, BattlePokemonLabelPainter ref) {
		super(parent, damage, true);
		this.data = data;
		this.ref = ref;
		flag = 0;
		forceOver = false;
	}


	@Override
	public void onStart() {
		if(!forceOver) {
//			System.out.println("Play " + this.toString() + ", tick = " + tick);
			SoundsHandler.playSound(SoundsHandler.DAMAGE);
		}
	}


	@Override
	public void tick() {
		if(!forceOver) {
//			System.out.println("Tick " + this.toString() + ", tick = " + tick);
			if(tick % (SPEED) == 0) {
				flag++;
				data.getEntityData().getStats().hpDamage(1);
//				System.out.println("Refresh: " + ref.getClass().getSimpleName());
				ref.refresh();
			}
		}
	}
	
	@Override
	public boolean isOver() {
		return flag == duration || data.getEntityData().getStats().isKO();
	}

	@Override
	public void onEnd() {
		flag = 0;
		forceOver = true;
		data.getEntityData().getStats().logHp();
		BattleDamageDescriptionPainter bddp = (BattleDamageDescriptionPainter) parent.getPaintersListsMap().get(GameStates.BATTLE_DAMAGE_DESCRIPTION).get(7);
		boolean criticalHit = parent.getBattle().getMap().get(BattleMap.IS_CRITICAL_HIT, Boolean.class);
//		System.out.println("CH: " + criticalHit);
		
		if(criticalHit) {
			GameStates.set(GameStates.BATTLE_CRITICAL_HIT);
			
			new Timer().schedule(new TimerTask() {
				
				@Override
				public void run() {
					bddp.setRectText();
					GameStates.set(GameStates.BATTLE_DAMAGE_DESCRIPTION);
				}
				
			}, 1500);
			
		} else {
			bddp.setRectText();
			GameStates.set(GameStates.BATTLE_DAMAGE_DESCRIPTION);
		}
	}
}
