package xml_parser;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import map.MapParser;

import org.xml.sax.SAXException;

/**
 * XML should look like this: <?xml version="1.0"?> 
 * <Map>
 * <Width>2</Width>
 * <Height>2</Height>
 * <Line_1> 
 * <Element>NormalField</Element>
 * <Element>NormalField</Element> 
 * </Line_1> 
 * <Line_2>
 * <Element>IndestructibleField</Element>
 * <Element>IndestructibleField</Element> 
 * </Line_2> 
 * </Map> 
 * 
 * Map generating code should be changed to: 
 * - New Constructor with arraylist<string>,width,height 
 * - If possible implement code idea by keiner
 */


public class Example_parsercode {
	/**
	 * @param args
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException {

		ArrayList<String> MapArray = null;

		MapParser testing = new MapParser("map1.xml");
		testing.parse();
		MapArray = testing.getMapElements();
		System.out.println("Width: " + testing.get_width() + "   " + "Height: "
				+ testing.get_height());

		System.out.println("MapElements: " + MapArray.get(0) + " "
				+ MapArray.get(1) + " " + MapArray.get(2) + " "
				+ MapArray.get(3) + " ");

	}

}
