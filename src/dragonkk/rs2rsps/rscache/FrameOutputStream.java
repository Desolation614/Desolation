package dragonkk.rs2rsps.rscache;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FrameOutputStream extends RSOutputStream{

    private int id;

    public FrameOutputStream(int id) {
       super(null);
        this.id = id;
        out = new ByteArrayOutputStream();
    }

    public int getId(){
        return id;
    }

    public void writeShortLE(int id) throws IOException {
        write(id & 0xff);
        write(id << 8);
    }
}