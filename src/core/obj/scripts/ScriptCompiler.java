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
import core.obj.maps.Map;
import core.obj.maps.MapEntities;
import core.obj.scripts.actions.FaceAction;
import core.obj.scripts.actions.FacePlayerAction;
import core.obj.scripts.actions.MoveAction;
import core.obj.scripts.actions.WaitPressAction;
import core.obj.scripts.actions.RotateAction;
import core.obj.scripts.actions.SetStateAction;
import core.obj.scripts.actions.ShowHideAction;
import core.obj.scripts.actions.SoundAction;
import core.obj.scripts.actions.TeleportAction;
import core.obj.scripts.actions.TextAction;
import core.obj.scripts.actions.WaitAction;
import jutils.asserts.Assert;
import jutils.asserts.AssertException;
import jutils.config.Config;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ScriptCompiler {

	private final String WRONG_FORMAT_TAG = "WRONG FORMAT";
	private final String MISSING_ENTITY_TAG = "MISSING ENTITY IN MAP";
	private final int DEFAULT_WAIT = Integer.parseInt(Config.getValue("script-default-wait"));
	private String arg = null;
	private String[] args = null;
	
	// Used in parsing
	private int i;
	private List<String> lines;
	
	
	public Script compile(Map map, OverworldEntity owner, MapEntities entities, File file) throws Exception {
		Script script = new Script(file, owner);
		lines = new ArrayList<>();
		
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
		arg = line.replace("faceplayer ", "");
		
		return new FacePlayerAction(parseEntity(arg, args, owner, entities));
	}
	
	private ScriptAction face(String line, OverworldEntity owner, MapEntities entities) throws AssertException {
		arg = line.replace("face ", "");
		args = arg.split(" ");
		Assert.isTrue(args.length == 2, invalidArguments(arg, WRONG_FORMAT_TAG));
		
		return new FaceAction(parseEntity(line, args[0].split("-"), owner, entities), parseDirection(args[1]));
	}
	
	private ScriptAction rotate(String line, OverworldEntity owner, MapEntities entities) throws AssertException {
		arg = line.replace("rotate ", "");
		args = arg.split(" ");
		Assert.isTrue(args.length == 2, invalidArguments(arg, WRONG_FORMAT_TAG));
		
		return new RotateAction(parseEntity(line, args[0].split("-"), owner, entities), parseRotationValue(args[1]));
	}
	
	private ScriptAction move(String line, OverworldEntity owner, MapEntities entities) throws AssertException {
		arg = line.replace("move ", "");
		args = arg.split(" ");
		Assert.isTrue(args.length == 2, invalidArguments(arg, WRONG_FORMAT_TAG));
		
		return new MoveAction(parseEntity(line, args[0].split("-"), owner, entities), parseDirection(args[1]));
	}
	
	private ScriptAction wait(String line) {
		arg = line.replace("wait ", "");
		
		return new WaitAction(Integer.parseInt(arg));
	}
	
	private ScriptAction text(String line) throws AssertException {
		arg = line.replace("text ", "");
		Assert.isTrue(arg.startsWith("\"") && arg.endsWith("\"") && (arg.length() - arg.replace("\"", "").length() == 2), invalidArguments(arg, WRONG_FORMAT_TAG));
		
		return new TextAction(arg.replace("\"", ""));
	}
	
	private ScriptAction teleport(String line, OverworldEntity owner, MapEntities entities) throws AssertException {
		arg = line.replace("teleport ", "");
		args = arg.split(" ");
		Assert.isTrue(args.length == 2, invalidArguments(arg, WRONG_FORMAT_TAG));
		
		return new TeleportAction(parseEntity(line, args[0].split("-"), owner, entities), entities, parsePosition(args[1]));		
	}
	
	private ScriptAction show(String line, OverworldEntity owner, MapEntities entities) throws AssertException {
		arg = line.replace("show ", "");
		
		return new ShowHideAction(parseEntity(line, args[0].split("-"), owner, entities), true);
	}

	private ScriptAction hide(String line, OverworldEntity owner, MapEntities entities) throws AssertException {
		arg = line.replace("hide ", "");
		
		return new ShowHideAction(parseEntity(line, args[0].split("-"), owner, entities), false);
	}
	
	private ScriptAction state(String line, OverworldEntity owner) {
		arg = line.replace("state ", "");
		
		return new SetStateAction(owner, Integer.parseInt(arg));
	}
	
	private ScriptAction sound(String line) {
		arg = line.replace("sound ", "");
		
		return new SoundAction(parseClip(arg));
	}
	
	/*
	 * Utility
	 */
	
	private OverworldEntity parseEntity(String arg, String[] args, OverworldEntity owner, MapEntities entities) throws AssertException {
		if(arg.equalsIgnoreCase("this")) {
			return owner;
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
}
