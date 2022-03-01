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
import core.gui.screen.content.exploration.ExplorationStartMenuKeyPressHandler;
import core.gui.screen.content.exploration.painters.ExplorationEntityScriptPainter;
import core.gui.screen.content.exploration.painters.ExplorationFadeInPainter;
import core.gui.screen.content.exploration.painters.ExplorationFadeOutPainter;
import core.gui.screen.content.exploration.painters.ExplorationPainter;
import core.gui.screen.content.exploration.painters.ExplorationStartMenuPainter;
import core.obj.entities.overworld.PlayerOverworldEntity;
import core.obj.entities.player.Player;
import core.obj.maps.Map;
import core.obj.maps.Maps;
import core.obj.maps.autotiles.MapAutoTilesHandler;
import core.obj.maps.entities.MapEntitiesHandler;
import core.obj.maps.links.Link;
import core.obj.maps.scripts.MapScript;
import core.obj.scripts.ScriptExecutor;
import jutils.config.Config;
import jutils.global.Global;
import jutils.threads.Threads;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
public class Exploration extends Content {

	@Getter
	private final List<Map> activeMaps;
	@Getter
	private final java.util.Map<GameStates, Painter<Exploration>> painters;
	@Getter
	private MapEntitiesHandler entityHandler;
	private MapAutoTilesHandler autoTilesHandler;
	private GameStates currentState;
	@Getter
	@Setter
	private boolean onMapChange;
	
	
	public Exploration() throws Exception {
		super();
		
		Log.log("Initializing Player...");
		Player player = new Player("player");
		Global.add("player", player);
		Log.log("Player initialized with name '" + player.getName() + "'");
		
		
		painters = new HashMap<>();
		painters.put(GameStates.EXPLORATION, new ExplorationPainter(this));
		painters.put(GameStates.EXPLORATION_ENTITY_SCRIPT, new ExplorationEntityScriptPainter(this));
		painters.put(GameStates.EXPLORATION_FADE_IN, new ExplorationFadeInPainter(this));
		painters.put(GameStates.EXPLORATION_FADE_OUT, new ExplorationFadeOutPainter(this));
		painters.put(GameStates.EXPLORATION_START_MENU, new ExplorationStartMenuPainter(this));
		
		
		activeMaps = new ArrayList<>();
		Map map = Maps.getMap(Config.getValue("game.active-map"));
		setActiveMap(map, player.getOverworldEntity(), true);
		
		
		currentState = GameStates.current();
		

		GlobalKeyEventHandler keyHandler = GlobalKeyEventHandler.getInstance();
		keyHandler.add(new ExplorationKeyPressHandler(this), GameStates.EXPLORATION);
		keyHandler.add(new ExplorationEntityScriptKeyPressHandler(this), GameStates.EXPLORATION_ENTITY_SCRIPT);
		keyHandler.add(new ExplorationStartMenuKeyPressHandler(this), GameStates.EXPLORATION_START_MENU);
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
			map.getEntities().respawnAll();
			map.getAutoTiles().respawnAll();
		} else {			
			map.getEntities().spawn(pEntity);
		}
		
		activeMaps.clear();
		activeMaps.add(map);
		addNeighbors(map);
		
		MapScript onEnter = map.getScripts().getOnEnter();
		if(onEnter != null) {
			Threads.run(() -> {
				do {
					ScriptExecutor.execute(onEnter, this);
				} while(onEnter.getIndex() != 0);
				
				Thread.currentThread().interrupt();
			});
		}
		
		MusicHandler.playMapMusic(map.getData());
		onMapChange = true;
		
		entityHandler = new MapEntitiesHandler(ContentSettings.tileOriginalSize, map.getEntities(), 2, this);
		autoTilesHandler = new MapAutoTilesHandler(map.getAutoTiles(), 6, this);
	}
	
	public Map getActiveMap() {
		return activeMaps.get(0);
	}
	
	
	@Override
	protected void paintComponent(Graphics2D g) {
		Painter<Exploration> painter = painters.get(currentState);
		
		switch(currentState) {
		case EXPLORATION:
			if(painter != null)
				painter.paint(g);
			break;
		case EXPLORATION_ENTITY_SCRIPT:
			painters.get(GameStates.EXPLORATION).paint(g);
			if(painter != null)
				painter.paint(g);
			break;
		case EXPLORATION_FADE_IN:
			painters.get(GameStates.EXPLORATION).paint(g);
			if(painter != null)
				painter.paint(g);
			break;
		case EXPLORATION_FADE_OUT:
			painters.get(GameStates.EXPLORATION).paint(g);
			if(painter != null)
				painter.paint(g);
			break;
		case EXPLORATION_START_MENU:
			painters.get(GameStates.EXPLORATION).paint(g);
			if(painter != null)
				painter.paint(g);
		default:
			break;
		}
	}
	
	@Override
	public void update() throws Exception {
		currentState = GameStates.current();
		
		switch (currentState) {
		case EXPLORATION:
			entityHandler.update();
			autoTilesHandler.update();
			break;
		case EXPLORATION_ENTITY_SCRIPT:
			break;
		default:
			break;
		}
		
		super.update();
	}
}
