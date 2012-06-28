package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MapCreator {
	public static void main(String[] args) throws IOException {
		
		// THIS IS JUST FOR TESTING - This is only pushed so that everybody else can add his wishes and ideas.
		// New class + connection to MapEditor.java will be done this weekend (30.6-1.7)

		// Stuff that will be provided by the map-editor
		int map_w = 4;
		int map_h = 4;
		// int[][] elements = new int[4][4];
		//

		// Random 4x4 map for testing
		String[][] elements = { 
				{ "x", "x", "x", "x" }, 
				{ "x", "0", "0", "x" },
				{ "x", "0", "0", "x" }, 
				{ "x", "x", "x", "x" }, };

		File file = new File("TESTING.xml");
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		// Do not change - XML start
		out.write("<?xml version=\"1.0\"?>");
		out.newLine();
		out.write("<Map>");
		out.newLine();
		out.write("<Width>" + map_w + "</Width>");
		out.newLine();
		out.write("<Height>" + map_h + "</Height>");
		out.newLine();
		//

		// *******//Loaded Elements From map-editor -  "dynamic" data that changes everytime
		for (int m = 0; m < map_h; m++) {
			out.write("<Line_"+map_h+">");
			out.newLine();

			for (int n = 0; n < map_w; n++) {
				String type = elements[n][m];
				// Some stupid cases I still need to think about
				// Add some ideas as comments on github 
				

				if (type.equals("x")) {
					out.write("<Element>IndestructibleWall</Element>");
					out.newLine();

				}
				
				if (type.equals("0")) {
					out.write("<Element>EmptyField</Element>");
					out.newLine();

				}

			}
			out.write("</Line_"+map_h+">");
			out.newLine();
		}

		//*******//	
		out.write("</Map>");//do not change! End of xml
		out.close();

	}

}
