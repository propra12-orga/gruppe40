package map;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MapCreator {

    public static void saveMap(Map map, String path) {
        int width = map.getWidth();
        int height = map.getHeight();

        try {
            File file = new File("TESTING.xml");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            // Do not change - XML start
            out.write("<?xml version=\"1.0\"?>");
            out.newLine();
            out.write("<Map>");
            out.newLine();
            out.write("<Width>" + width + "</Width>");
            out.newLine();
            out.write("<Height>" + height + "</Height>");
            out.newLine();

            for (int m = 0; m < height; m++) {
                out.write("<Line_" + m + ">");
                out.newLine();

                for (int n = 0; n < width; n++) {
                    String type = map.getField(n, m).getClass().getSimpleName();

                    out.write("<Element>" + type + "</Element>");
                    out.newLine();

                }
                out.write("</Line_" + m + ">");
                out.newLine();
            }

            // *******//
            out.write("</Map>");// do not change! End of xml
            out.close();

        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
