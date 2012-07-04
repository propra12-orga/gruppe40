package network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ObjectConverter {

    public static byte[] compressBytes(byte[] input) {
        Deflater deflater = new Deflater();
        deflater.setInput(input);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(input.length);
        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            byteStream.write(buffer, 0, count);
        }
        try {
            byteStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteStream.toByteArray();
    }

    public static byte[] inflateBytes(byte[] input) {
        Inflater inflater = new Inflater();
        inflater.setInput(input);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(input.length);
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            try {
                int count = inflater.inflate(buffer);
                byteStream.write(buffer, 0, count);
            } catch (DataFormatException e) {
                e.printStackTrace();
            }
        }
        try {
            byteStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteStream.toByteArray();
    }

    public static byte[] toByteArray(Object object) {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(object);
            objectStream.flush();
            objectStream.close();
            byteStream.close();
            return compressBytes(byteStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getFromByteArray(byte[] data) {
        data = inflateBytes(data);
        Object object = null;
        try {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
            ObjectInputStream objectStream = new ObjectInputStream(byteStream);
            object = objectStream.readObject();
            objectStream.close();
            byteStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
