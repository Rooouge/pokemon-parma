package core.obj.scripts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Clip;

import core.enums.Directions;
import core.exceptions.ParsingActionException;
import core.files.SoundsHandler;
import core.gui.GridPosition;
import core.obj.entities.overworld.OverworldEntity;
import core.obj.entities.player.Player;
import core.obj.maps.Map;
import core.obj.maps.entities.MapEntities;
import core.obj.maps.scripts.MapScript;
import core.obj.maps.scripts.MapScriptTypes;
import core.obj.scripts.actions.FaceAction;
import core.obj.scripts.actions.FacePlayerAction;
import core.obj.scripts.actions.MoveAction;
import core.obj.scripts.actions.MoveCameraAction;
import core.obj.scripts.actions.RotateAction;
import core.obj.scripts.actions.SetStateAction;
import core.obj.scripts.actions.ShowHideAction;
import core.obj.scripts.actions.SoundAction;
import core.obj.scripts.actions.TeleportAction;
import core.obj.scripts.actions.TextAction;
import core.obj.scripts.actions.WaitAction;
import core.obj.scripts.actions.WaitPressAction;
import jutils.asserts.Assert;
import jutils.asserts.AssertException;
import jutils.config.Config;
import jutils.global.Global;
import lombok.Data;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ScriptCompiler {

	private final String WRONG_FORMAT_TAG = "WRONG FORMAT";
	private final String MISSING_ENTITY_TAG = "MISSING ENTITY IN MAP";
	private final int DEFAULT_WAIT = Integer.parseInt(Config.getValue("script-default-wait"));
	
	// Used in parsing
	private int i;
	
	
	public Script compile(Map map, OverworldEntity owner, MapEntities entities, File file) throws Exception {
		Script script = new Script(file, owner);
		List<String> lines = new ArrayList<>();
		
		/*
		 * Reading
		 */
		try (
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		) {
			String line = null;
			
			while((line = br.readLine()) != null) {
				if(line.startsWith("//") || line.trim().isEmpty())
					continue;
				lines.add(line.trim());
			}
		}
		
		/*
		 * Parsing
		 */
		for(i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			ScriptAction action = parse(script, file, line, map, owner, entities);
			
			if(action == null)
				throw new ParsingActionException(file.getAbsolutePath(), i, line);
			
			script.add(action);
		}
		
		script.add(new WaitAction(DEFAULT_WAIT));

		
//		System.out.println(script);
		return script;
	}
	
	public MapScript compile(Map map, MapScriptTypes type, String[] lines) throws Exception {
		MapScript script = new MapScript(map, type);
		
		for(i = 0; i < lines.length; i++) {
			String line = lines[i].trim();
			
			if(line.isEmpty())
				continue;
			
			ScriptAction action = parse(script, null, line, map, null, map.getEntities());
			
			if(action == null)
				throw new ParsingActionException("XML: " + map.getData().getRegistryName(), i, line);
			
			script.add(action);
		}
		
		return script;
	}
	
	private ScriptAction parse(Script script, File file, String line, Map map, OverworldEntity owner, MapEntities entities) throws AssertException, ParsingActionException {
		/*
		 * Funtions
		 */
		
		// faceplayer
		if(line.startsWith("faceplayer"))
			return faceplayer(line, owner, entities);
		// face
		if(line.startsWith("face"))
			return face(line, owner, entities);
		// rotate
		if(line.startsWith("rotate"))
			return rotate(line, owner, entities);
		// movecamera
		if(line.startsWith("movecamera"))
			return moveCamera(line);
		// move
		if(line.startsWith("move"))
			return move(line, owner, entities);
		// waitpress
		if(line.equalsIgnoreCase("waitpress"))
			return new WaitPressAction();
		// wait
		if(line.startsWith("wait"))
			return wait(line);
		// text
		if(line.startsWith("text"))
			return text(line);
		// teleport
		if(line.startsWith("teleport"))
			return teleport(line, owner, entities);
		// show
		if(line.startsWith("show"))
			return show(line, owner, entities);
		// hide
		if(line.startsWith("hide"))
			return hide(line, owner, entities);
		// state
		if(line.startsWith("state"))
			return state(line, owner);
		// sound
		if(line.startsWith("sound"))
			return sound(line);
		
		return null;
	}
	
	
	private String invalidArguments(String arg, String tag) {
		return "Invalid arguments: '" + arg + "' [" + tag + "]";
	}
	
	/*
	 * Functions
	 */
	
	private ScriptAction faceplayer(String line, OverworldEntity owner, MapEntities entities) throws AssertException {
		String arg = line.replace("faceplayer ", "");
		
		return new FacePlayerAction(parseEntity(arg, null, owner, entities));
	}
	
	private ScriptAction face(String line, OverworldEntity owner, MapEntities entities) throws AssertException {
		return new FaceAction(parseEntitiesAndDirections(line, line.replace("face ", "").split(";"), owner, entities));
	}
	
	private ScriptAction rotate(String line, OverworldEntity owner, MapEntities entities) throws AssertException {
		return new RotateAction(parseEntitiesAndRotation(line, line.replace("rotate ", "").split(";"), owner, entities));
	}
	
	private ScriptAction move(String line, OverworldEntity owner, MapEntities entities) throws AssertException {
		return new MoveAction(parseEntitiesAndDirections(line, line.replace("move ", "").split(";"), owner, entities));
	}
	
	private ScriptAction moveCamera(String line) {
		String arg = line.replace("movecamera ", "");
		
		return new MoveCameraAction(parseDirection(arg));
	}
	
	private ScriptAction wait(String line) {
		String arg = line.replace("wait ", "");
		
		return new WaitAction(Integer.parseInt(arg));
	}
	
	private ScriptAction text(String line) throws AssertException {
		String arg = line.replace("text ", "");
		Assert.isTrue(arg.startsWith("\"") && arg.endsWith("\"") && (arg.length() - arg.replace("\"", "").length() == 2), invalidArguments(arg, WRONG_FORMAT_TAG));
		
		return new TextAction(arg.replace("\"", ""));
	}
	
	private ScriptAction teleport(String line, OverworldEntity owner, MapEntities entities) throws AssertException {
		return new TeleportAction(parseEntitiesAndPositions(line, line.replace("teleport ", "").split(";"), owner, entities), entities);
	}
	
	private ScriptAction show(String line, OverworldEntity owner, MapEntities entities) throws AssertException {
		String arg = line.replace("show ", "");
		
		return new ShowHideAction(parseEntity(line, arg, owner, entities), true);
	}

	private ScriptAction hide(String line, OverworldEntity owner, MapEntities entities) throws AssertException {
		String arg = line.replace("hide ", "");
		
		return new ShowHideAction(parseEntity(line, arg, owner, entities), false);
	}
	
	private ScriptAction state(String line, OverworldEntity owner) {
		String arg = line.replace("state ", "");
		
		return new SetStateAction(owner, Integer.parseInt(arg));
	}
	
	private ScriptAction sound(String line) {
		String arg = line.replace("sound ", "");
		
		return new SoundAction(parseClip(arg));
	}
	
	/*
	 * Utility
	 */
	
	private List<EntityDirection> parseEntitiesAndDirections(String line, String[] args, OverworldEntity owner, MapEntities entities) throws AssertException {
		List<EntityDirection> movements = new ArrayList<>();
		for(String subArg : args) {
			String[] subArgs = subArg.split("_");
			Assert.isTrue(subArgs.length == 2, invalidArguments(subArg, WRONG_FORMAT_TAG));
			
			movements.add(new EntityDirection(parseEntity(line, subArgs[0], owner, entities), parseDirection(subArgs[1])));
		}
		
		return movements;
	}
	
	private List<EntityRotation> parseEntitiesAndRotation(String line, String[] args, OverworldEntity owner, MapEntities entities) throws AssertException {
		List<EntityRotation> rotations = new ArrayList<>();
		for(String subArg : args) {
			String[] subArgs = subArg.split("_");
			Assert.isTrue(subArgs.length == 2, invalidArguments(subArg, WRONG_FORMAT_TAG));
			
			rotations.add(new EntityRotation(parseEntity(line, subArgs[0], owner, entities), parseRotationValue(subArgs[1])));
		}
		
		return rotations;
	}
	
	private List<EntityPosition> parseEntitiesAndPositions(String line, String[] args, OverworldEntity owner, MapEntities entities) throws AssertException {
		List<EntityPosition> positions = new ArrayList<>();
		for(String subArg : args) {
			String[] subArgs = subArg.split("_");
			Assert.isTrue(subArgs.length == 2, invalidArguments(subArg, WRONG_FORMAT_TAG));
			
			positions.add(new EntityPosition(parseEntity(line, subArgs[0], owner, entities), parsePosition(subArgs[1])));
		}
		
		return positions;
	}
	
	
	private OverworldEntity parseEntity(String arg, String argStr, OverworldEntity owner, MapEntities entities) throws AssertException {
		String[] args;
		if(argStr == null)
			args = arg.split("-");
		else
			args = argStr.split("-");
		
		if(args[0].equalsIgnoreCase("this")) {
			return owner;
		} else if(args[0].equalsIgnoreCase("player")) {
			return Global.get("player", Player.class).getOverworldEntity();
		}
		
		Assert.isTrue(args.length == 2, invalidArguments(arg, WRONG_FORMAT_TAG));
		String eName = args[0];
		int eVariant = Integer.parseInt(args[1]);
		
		OverworldEntity target = entities.getFromEntityIdentifiers(eName, eVariant);
		Assert.notNull(target, invalidArguments(arg, MISSING_ENTITY_TAG));
		
		return target;
	}
	
	private Directions parseDirection(String arg) {
		return Directions.getFromName(arg);
	}
	
	private GridPosition parsePosition(String arg) throws AssertException {
		String[] pArgs = arg.split(",");
		Assert.isTrue(pArgs.length == 2, invalidArguments(arg, WRONG_FORMAT_TAG));
		
		int r = Integer.parseInt(pArgs[0]);
		int c = Integer.parseInt(pArgs[1]);
		
		return new GridPosition(r, c);
	}
	
	private int parseRotationValue(String arg) throws AssertException {
		int value = Integer.parseInt(arg);
		Assert.isTrue(value == 90 || value == 180 || value == 270, invalidArguments(arg, "Invalid rotation value (Accepted only 90/180/270"));
		
		return value;
	}
	
	private Clip parseClip(String arg) {
		arg = arg.trim().toLowerCase();
		return SoundsHandler.get(arg);
	}
	
	/*
	 * private class
	 */
	
	@Data
	public class EntityDirection {
		
		private final OverworldEntity entity;
		private final Directions dir;
		
	}
	
	@Data
	public class EntityRotation {
		
		private final OverworldEntity entity;
		private final int rotationValue;
		
	}
	
	@Data
	public class EntityPosition {
		
		private final OverworldEntity entity;
		private final GridPosition pos;
		
	}
}
