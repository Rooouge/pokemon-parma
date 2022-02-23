package core.obj.entities.player;

import java.io.IOException;

import core.obj.entities.overworld.PlayerOverworldEntity;
import lombok.Getter;

@Getter
public class Player {

	private final String name;
	private final PlayerOverworldEntity overworldEntity;
	
	
	public Player(String name) throws IOException {
		this.name = name;
		overworldEntity = new PlayerOverworldEntity(name);
	}
	
}
