package map;
     
    import java.io.IOException;
    import java.util.ArrayList;
     
    import javax.xml.parsers.ParserConfigurationException;
    import javax.xml.parsers.SAXParser;
    import javax.xml.parsers.SAXParserFactory;
     

    import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
     
    public class MapParser {
     
            private ArrayList<String> MapArray;
            private String map;
            private int width;
            private int height;
     
            /**
             * @param map
             */
            public MapParser(String map) {
                    this.map = map;
            }
     
            /**
             * @throws IOException
             * @throws SAXException
             * @throws ParserConfigurationException
             */
            public void parse(){
                try{
                    SAXParserFactory testing = SAXParserFactory.newInstance();
                    SAXParser saxParser = testing.newSAXParser();
                    XMLReader reader = saxParser.getXMLReader();
                    MapHandler handler = new MapHandler();
                    reader.setContentHandler(handler);
                    //Resource loader is able to locate resource by relative path and can give absolute path
                    reader.parse(this.getClass().getClassLoader().getResource("map/" + this.map + ".xml").getPath());
                    this.MapArray = handler.getMapElements();
                    this.width = handler.get_width();
                    this.height = handler.get_height();
                }
                catch (ParserConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    catch (SAXException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
           
    catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
     
            }
     
            public ArrayList<String> getMapElements() {
                    return this.MapArray;
            }
     
            public int get_width() {
                    return this.width;
            }
     
            public int get_height() {
                    return this.height;
            }
     
    }
