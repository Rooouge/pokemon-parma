package core.gui.screen.content.battle.animations;

import core.enums.GameStates;
import core.files.SoundsHandler;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.battle.painters.BattleEnemyPokemonLabelPainter;
import core.gui.screen.painters.animations.NoGUIAnimation;
import core.obj.pokemon.battle.BattlePokemonData;

public class DamageAnimation extends NoGUIAnimation<Battle> {

	private static final int SPEED = 2;
	protected final BattlePokemonData data;
	protected final BattleEnemyPokemonLabelPainter ref;
	protected int flag;
	
	
	public DamageAnimation(Battle parent, BattlePokemonData data, int damage, BattleEnemyPokemonLabelPainter ref) {
		super(parent, damage, true);
		this.data = data;
		this.ref = ref;
		flag = 0;
		
	}


	@Override
	public void onStart() {
		SoundsHandler.playSound(SoundsHandler.DAMAGE);
	}


	@Override
	public void tick() {
		if(tick % (SPEED) == 0) {
			flag++;
			data.getEntityData().getStats().hpDamage(1);
			ref.refresh();
		}
	}
	
	@Override
	public boolean isOver() {
		return flag == duration || data.getEntityData().getStats().isKO();
	}

	@Override
	public void onEnd() {
		flag = 0;
		data.getEntityData().getStats().logHp();
		GameStates.set(GameStates.BATTLE_OPTIONS);
	}
}
