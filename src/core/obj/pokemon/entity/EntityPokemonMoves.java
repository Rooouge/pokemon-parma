package core.obj.pokemon.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import core.files.XMLHandler;
import core.obj.pokemon.moves.Move;
import core.obj.pokemon.moves.MoveAtLevel;
import core.obj.pokemon.moves.MoveParser;
import core.obj.pokemon.pokedex.Pokedex;
import jutils.log.Log;

@SuppressWarnings("serial")
public class EntityPokemonMoves extends ArrayList<Move> {

	private static final int MAX = 4;
	
	/*
	 * Gets the last 4 moves for the specified pokemon at the specified level
	 */
	public void generateMovesForWildPokemon(int id, int level) throws Exception {
		Log.info("Generating moves for wild pok�mon " + Pokedex.instance().baseData(id).getName() + " at level " + level);
		try {
			File xml = XMLHandler.getFile("pokemon", "moves/learnsets");
			Document doc = new SAXReader().read(xml);
			Element root = doc.getRootElement();
			
			Node pkmn = root.selectSingleNode("pokemon[@id='" + id + "']");
			Node lvUp = pkmn.selectSingleNode("levelup");
			List<Node> moves = lvUp.selectNodes("move");
			
			List<MoveAtLevel> moveAtLevels = new ArrayList<>();
			for(Node m : moves) {
//				System.out.println(" - " + m.asXML());
				moveAtLevels.add(new MoveAtLevel(m.getText(), Integer.parseInt(m.valueOf("@atlevel"))));
			}
			
			// Retrieve xml of all Moves
			File movesXml = XMLHandler.getFile("pokemon", "moves/moves");
			Document movesDoc = new SAXReader().read(movesXml);
			Element movesRoot = movesDoc.getRootElement();
			
			for(int i = moveAtLevels.size(); i > 0 && !isFull(); i--) {
				MoveAtLevel m = moveAtLevels.get(i-1);
				
				if(m.getLevel() > level)
					continue;

				Log.info("Creating move '" + m.getName() + "'");
				Node moveNode = movesRoot.selectSingleNode("move[@name='" + m.getName() + "']");
				String category = moveNode.valueOf("@category");
				Move move;
				
				switch (category) {
				case "Physical":
					move = MoveParser.parsePhysical(moveNode);
					if(!alreadyKnows(move))
						add(move);
					break;
				case "Special":
					move = MoveParser.parseSpecial(moveNode);
					if(!alreadyKnows(move))
						add(move);
					break;
				case "Status":
					move = MoveParser.parseStatus(moveNode);
					if(!alreadyKnows(move))
						add(move);
					break;
				default:
					throw new Exception("Failed to recognize move category: '" + category + "'");
				}
			}
		} catch (Exception e) {
			Log.error("Failed to parse moves for wild pokemon");
			throw e;
		}
	}
	
	
	@Override
	public boolean add(Move e) {
		if(size() >= MAX)
			return false;
		
		return super.add(e);
	}
	
	
	@Override
	public boolean contains(Object o) {
		if(o instanceof Move) {
			Move om = (Move) o;
			
			for(Move m : this) {
				if(m.getName().equalsIgnoreCase(om.getName()))
					return true;
			}
			
			return false;
		}
		
		return super.contains(o);
	}
	
	public boolean isFull() {
		return size() >= MAX;
	}
	
	public boolean alreadyKnows(Move move) {
		return contains(move);
	}

}
