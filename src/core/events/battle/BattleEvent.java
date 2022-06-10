package core.events.battle;

import javax.sound.sampled.Clip;

import core.enums.TileMovements;
import core.files.MusicHandler;
import core.obj.pokemon.entity.EntityPokemon;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public class BattleEvent {

	public static final Clip BATTLE_MUSIC = MusicHandler.get("wild_battle");
	
	protected final TileMovements tile;
	protected final EntityPokemon entityPokemon;
	@Setter
	protected BattleMap map;
	
}
