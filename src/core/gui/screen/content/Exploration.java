package core.gui.screen.content;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.Log;
import core.enums.GameStates;
import core.files.MusicHandler;
import core.gui.interfaces.Painter;
import core.gui.screen.GlobalKeyEventHandler;
import core.gui.screen.content.exploration.ExplorationEntityScriptKeyPressHandler;
import core.gui.screen.content.exploration.ExplorationKeyPressHandler;
import core.gui.screen.content.exploration.painters.ExplorationEntityScriptPainter;
import core.gui.screen.content.exploration.painters.ExplorationPainter;
import core.obj.entities.overworld.PlayerOverworldEntity;
import core.obj.entities.player.Player;
import core.obj.maps.Map;
import core.obj.maps.MapEntitiesHandler;
import core.obj.maps.Maps;
import core.obj.maps.links.Link;
import jutils.config.Config;
import jutils.global.Global;
import lombok.Getter;

@SuppressWarnings("serial")
public class Exploration extends Content {

	@Getter
	private final List<Map> activeMaps;
	@Getter
	private final java.util.Map<GameStates, Painter<Exploration>> painters;
	@Getter
	private MapEntitiesHandler entityHandler;
	private GameStates currentState;
	
	
	public Exploration() throws Exception {
		super();
		
		painters = new HashMap<>();
		painters.put(GameStates.EXPLORATION, new ExplorationPainter(this));
		painters.put(GameStates.EXPLORATION_ENTITY_SCRIPT, new ExplorationEntityScriptPainter(this));
		
		
		Log.log("Initializing Player...");
		Player player = new Player("player");
		Global.add("player", player);
		Log.log("Player initialized with name '" + player.getName() + "'");
		
		activeMaps = new ArrayList<>();
		Map map = Maps.getMap(Config.getValue("game.active-map"));
		setActiveMap(map, player.getOverworldEntity(), true);
		
		
		currentState = GameStates.current();
		
		
		GlobalKeyEventHandler keyHandler = GlobalKeyEventHandler.getInstance();
		keyHandler.add(new ExplorationKeyPressHandler(this), GameStates.EXPLORATION);
		keyHandler.add(new ExplorationEntityScriptKeyPressHandler(this), GameStates.EXPLORATION_ENTITY_SCRIPT);
	}
	
	
	public void addMap(Map map) {
		activeMaps.add(map);
	}
	
	private void addNeighbors(Map map) throws Exception {
		String regName = map.getData().getRegistryName();
		for(Link l : map.getLinks()) {
			Map n = Maps.getMap(l.getNeighborName(regName));
			addMap(n);
			n.getData().setPosAsNeighbor(map, l);
//			System.out.println("Added neighbor: " + n.getData().getRegistryName() + " in position: " + n.getData().getPos());
		}
	}
	
	public void setActiveMap(Map map, PlayerOverworldEntity pEntity, boolean init) throws Exception {
		map.initLinks();
		
		if(!init) {
			Map aMap = getActiveMap();
			aMap.getEntities().remove(pEntity);
			MusicHandler.stopMapMusic(aMap.getData());
		} else {
			map.getEntities().spawn(pEntity);
		}
		
		activeMaps.clear();
		activeMaps.add(map);
		addNeighbors(map);
		
		/*
		for(int i = 0; i < activeMaps.size(); i++) {
			Map m = activeMaps.get(i);
			System.out.println(i + ") " + m.getData().getName() + ", pos: " + m.getData().getPos());
		}
		*/
		
		MusicHandler.playMapMusic(map.getData());
		entityHandler = new MapEntitiesHandler(ContentSettings.tileOriginalSize, map.getEntities(), 6, this);
	}
	
	public Map getActiveMap() {
		return activeMaps.get(0);
	}
	
	
	@Override
	protected void paintComponent(Graphics2D g) {
		painters.get(GameStates.EXPLORATION).paint(g);
		switch (currentState) {
		default:
			painters.get(currentState).paint(g);
			break;
		}
	}
	
	@Override
	public void update() throws Exception {
		currentState = GameStates.current();
		
		switch (currentState) {
		case EXPLORATION:
			entityHandler.update();
			break;
		case EXPLORATION_ENTITY_SCRIPT:
			break;
		default:
			break;
		}
		
		super.update();
	}
}
