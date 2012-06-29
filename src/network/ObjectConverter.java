package network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectConverter {

    public static byte[] toByteArray(Object object) {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(object);
            objectStream.flush();
            objectStream.close();
            byteStream.close();
            return byteStream.toByteArray();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Object getFromByteArray(byte[] data) {
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
