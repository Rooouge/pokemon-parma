package core.events.battle;

import core.enums.TileMovements;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BattleEvent {

	protected final TileMovements tile;
	
}
