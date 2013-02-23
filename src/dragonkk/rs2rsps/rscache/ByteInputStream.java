package dragonkk.rs2rsps.rscache;

public class ByteInputStream {

    public byte[] buffer;
    public int pos;

    public ByteInputStream(byte[] data) {
        buffer = data;
        pos = 0;
    }

    public int readSpaceSaver() {
		int i_6_ = buffer[pos] & 255;
		if (i_6_ < 128) {
			return readUByte();
		}
		return -32768 + readUShort();
    }
    
    public int readSmart2() {
		int i = 0;
	    int i_33_ = readSmart();
	    while ((i_33_ ^ 0xffffffff) == -32768) {
		i_33_ = readSmart();
		i += 32767;
	    }
	    i += i_33_;
	    return i;
    }
    
    public int readSmart() {
	    int i = 0xff & buffer[pos];
	    if (i >= 128)
		return -32768 + readUShort();
	    return readUByte();
    }

    public int readUShort() {
		pos += 2;
		return (((buffer[pos - 2] & 0xff) << 8) + (buffer[pos - 1] & 0xff));
    }
    public int readUByte() {
		return 0xff & buffer[pos++];
    }
}