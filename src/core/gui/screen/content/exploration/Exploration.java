package core.gui.screen.content.exploration;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import core.Log;
import core.enums.GameStates;
import core.files.MusicHandler;
import core.gui.interfaces.Painter;
import core.gui.screen.content.Content;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.exploration.keyhandlers.ExplorationEntityScriptKeyHandler;
import core.gui.screen.content.exploration.keyhandlers.ExplorationKeyHandler;
import core.gui.screen.content.exploration.keyhandlers.ExplorationStartMenuKeyHandler;
import core.gui.screen.content.exploration.painters.ExplorationEntityScriptPainter;
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
public class Exploration extends Content<Exploration> {

	@Getter
	private final List<Map> activeMaps;
	@Getter
	private MapEntitiesHandler entityHandler;
	private MapAutoTilesHandler autoTilesHandler;
	@Getter
	@Setter
	private boolean onMapChange;
	
	
	public Exploration() throws Exception {
		super(true, -1);
		
		Log.log("Initializing Player...");
		Player player = new Player("player");
		Global.add("player", player);
		Log.log("Player initialized with name '" + player.getName() + "'");

		initPainters();
		initKeyHandlers();
		
		activeMaps = new ArrayList<>();
		Map map = Maps.getMap(Config.getValue("game.active-map"));
		setActiveMap(map, player.getOverworldEntity(), true);
	}
	
	
	@Override
	protected void initPainters() {		
		painters.put(GameStates.EXPLORATION, new ExplorationPainter(this));
		painters.put(GameStates.EXPLORATION_ENTITY_SCRIPT, new ExplorationEntityScriptPainter(this));
		painters.put(GameStates.EXPLORATION_START_MENU, new ExplorationStartMenuPainter(this));
	}
	
	@Override
	protected void initKeyHandlers() {
		keyHandler.add(new ExplorationKeyHandler(this), GameStates.EXPLORATION);
		keyHandler.add(new ExplorationEntityScriptKeyHandler(this), GameStates.EXPLORATION_ENTITY_SCRIPT);
		keyHandler.add(new ExplorationStartMenuKeyHandler(this), GameStates.EXPLORATION_START_MENU);
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
			MusicHandler.stopMapMusic(aMap);
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
		
		MusicHandler.playMapMusic(map);
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
		case FADE_IN:
			painters.get(GameStates.EXPLORATION).paint(g);
			if(painter != null)
				painter.paint(g);
			break;
		case FADE_OUT:
			painters.get(GameStates.EXPLORATION).paint(g);
			if(painter != null)
				painter.paint(g);
			break;
		case EXPLORATION_START_MENU:
			painters.get(GameStates.EXPLORATION).paint(g);
			if(painter != null)
				painter.paint(g);
		case EXPLORATION_WILD:
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
