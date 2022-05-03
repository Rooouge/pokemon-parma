package core.obj.entities.player;

import java.io.IOException;

import core.obj.entities.overworld.PlayerOverworldEntity;
import core.obj.pokemon.containers.PlayerTeam;
import jutils.global.Global;
import lombok.Getter;

@Getter
public class Player {

	private final String name;
	private final PlayerOverworldEntity overworldEntity;
	private final PlayerTeam team;
	
	
	public Player(String name) throws IOException {
		this.name = name;
		overworldEntity = new PlayerOverworldEntity(name);
		team = new PlayerTeam();
	}
	
	
	public static Player instance() {
		return Global.get("player", Player.class);
	}
	
}
