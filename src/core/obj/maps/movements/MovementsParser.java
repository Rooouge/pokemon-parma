package core.obj.maps.movements;

import java.awt.Dimension;
import java.io.IOException;
import java.util.Arrays;

import org.dom4j.Node;

import core.enums.TileMovements;
import lombok.Getter;

public class MovementsParser {

	private final String[] lines;
	private int currentLine;
	@Getter
	private int[][] movements;
	
	
	public MovementsParser(Node movNode, Dimension size) throws IOException {
		movements = new int[size.height][size.width];
		
		lines = movNode.asXML().split("\n");
		
		for(int i = 0; i < lines.length; i++) {
			currentLine = i;
			parseLine(lines[i]);
			
			i = currentLine;
		}
	}
	
	private void parseLine(String line) throws IOException {
        String[] params = parseParams(line);
        
        if(line.contains("fill"))
        	fill(params);
        else if(line.contains("rows"))
            rows(params);
    }

    private String[] parseParams(String line) {
        int first = line.indexOf("(") + 1;
        int last = line.indexOf(")");

        String params = "";

        for(int i = first; i < last; i++) {
            params += line.substring(i, i+1);
        }

        return params.split(",");
    }

    private void rows(String[] params) throws IOException {
        if(params.length != 2)
            throw new IOException("rows() params != 2   ---   " + Arrays.toString(params));

        int firstRow = Integer.parseInt(params[0]);
        int lastRow = params[1].equals("_h") ? movements.length : (Integer.parseInt(params[1]) + 1);

        currentLine++;
        String line = lines[currentLine];
        
        while(!(line.contains("}"))) {
            for (int r = firstRow; r < lastRow; r++) {
                if (!line.contains("fillRow"))
                    throw new IOException("Invalid instruction in fillRow: " + line);

                String[] fillRowParams = line.substring(line.indexOf('(') + 1, line.indexOf(')')).split(",");
                if (fillRowParams.length != 3)
                    throw new IOException("fillRow() params != 3   ---   " + Arrays.toString(fillRowParams));

                fillRow(r, fillRowParams);
            }
            
            currentLine++;
            line = lines[currentLine];
        }
    }

    private void fillRow(int r, String[] params) {
        int firstColumn = Integer.parseInt(params[0]);
        int lastColumn = params[1].equals("_w") ? movements[0].length : (Integer.parseInt(params[1]) + 1);
        int movement = TileMovements.getFromName(params[2]);

        for(int c = firstColumn; c < lastColumn; c++) {
        	movements[r][c] = movement;
        }
    }
    
    private void fill(String[] params) {
    	int row = Integer.parseInt(params[0]);
    	int column = Integer.parseInt(params[1]);
    	int movement = TileMovements.getFromName(params[2]);
    	
    	movements[row][column] = movement;
    }
    
}
