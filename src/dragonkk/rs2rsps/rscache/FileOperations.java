package dragonkk.rs2rsps.rscache;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileOperations {

    public static byte[] ReadFile(String s) {
        File f= new File(s);
        byte data[] = new byte[(int)f.length()];
        try {
            RandomAccessFile ff = new RandomAccessFile(f,"r");
            ff.readFully(data);
        } catch (IOException e) {
            return null;
        }
        return data;
    }
}