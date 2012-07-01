package map;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Korr
 * 
 */
public class MapHandler extends DefaultHandler {

	private String Element_test;
	private String Element;
	private ArrayList<String> MapElements;
	private int width;
	private int height;

	/**
	 * 
	 */
	public MapHandler() {
		this.width = 0;
		this.height = 0;
		this.Element_test = null;
		MapElements = new ArrayList<String>();
	}

	public void startDocument() {
		// System.out.println("Dokumentverarbeitung gestartet." + "\n");

	}

	public void endDocument() {
		// System.out.println("Dokumentverarbeitung beendet.");
		// System.out.println("Gespeicherte Inhalte:");
		// System.out.println("Width: " + this.width);
		// System.out.println("Height: " + this.height);
		// System.out.println("MapElements: " + MapElements.get(0) + " " +
		// MapElements.get(1) + " " + MapElements.get(2) + " " +
		// MapElements.get(3) + " ");
	}

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) {

		if (qName.equals("Width"))
			this.Element_test = "Width";
		if (qName.equals("Height"))
			this.Element_test = "Height";
		if (qName.equals("Element"))
			this.Element_test = "Element";

		// System.out.print(qName + " ");

	}

	@Override
	public void endElement(String uri, String localName, String qName) {

		if (Element_test.equals("Width")) {
			this.width = Integer.parseInt(Element);
		}

		if (Element_test.equals("Height")) {
			this.height = Integer.parseInt(Element);
		}
		if (Element_test.equals("Element")) {
			addValue(Element);
		}
		this.Element_test = "";

	}

	public void characters(char ch[], int start, int length) {
		this.Element = new String(ch, start, length);
		// System.out.print(new String(ch, start, length));
	}

	private void addValue(String value) {
		if (value != null) {
			this.MapElements.add(value);
			// System.out.println(Element);

		}
	}

	public ArrayList<String> getMapElements() {
		return this.MapElements;
	}

	public int get_width() {
		return this.width;
	}

	public int get_height() {
		return this.height;
	}
}
