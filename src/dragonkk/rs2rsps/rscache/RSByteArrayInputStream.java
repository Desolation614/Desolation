package dragonkk.rs2rsps.rscache;

import java.io.ByteArrayInputStream;

public class RSByteArrayInputStream extends ByteArrayInputStream {

    public RSByteArrayInputStream(byte[] buf) {
        super(buf);
    }

    public RSByteArrayInputStream(byte[] buf, int offset, int length) {
        super(buf, offset, length);
    }
    public void seek(int i){
        pos = i;
    }

    public int getPos() {
        return pos;
    }
}