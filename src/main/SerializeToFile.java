package main;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import game.GameData;

public class SerializeToFile {

    public static void work() {
        try {
            OutputStream file = new FileOutputStream("data.txt");
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutputStream output = new ObjectOutputStream(buffer);
            try {
                output.writeObject(GameData.drawables);
            } finally {
                output.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
