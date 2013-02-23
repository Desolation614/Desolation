package dragonkk.rs2rsps.rscache.bzip;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BZ2Compressor {

    public static byte[] compress(byte[] in) throws IOException {
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        DataOutputStream ins = new DataOutputStream(new CBZip2OutputStream(s));
		ins.write(in);
		ins.close();
        ins.flush();
        ins.close();
        return s.toByteArray();
    }
}