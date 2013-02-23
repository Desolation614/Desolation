package dragonkk.rs2rsps.io;



public final class InStream {
    public int offset = 0;
    private int length = 0;
    private byte[] buffer;
    public int opcode = 0;

    public InStream(int capacity) {
        buffer = new byte[capacity];
    }

    public InStream(byte[] buffer) {
        this.buffer = buffer;
        this.length = buffer.length;
    }
    

    public void checkCapacity(int length) {
        if (offset + length >= buffer.length) {
            byte[] newBuffer = new byte[(offset + length) * 2];
            System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
            buffer = newBuffer;
        }
    }
    
    public int read24BitInt() {
    return (readUnsignedByte() << 16) + (readUnsignedByte() << 8) + (readUnsignedByte());
  }
    

    public void skip(int length) {
        offset += length;
    }

    public int offset() {
        return offset;
    }

    public int length() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int remaining() {
        return offset < length ? length - offset : 0;
    }

    public byte[] buffer() {
        return buffer;
    }

    public void addBytes(byte[] b, int offset, int length) {
        checkCapacity(length - offset);
        System.arraycopy(b, offset, buffer, this.offset, length);
        this.length += length - offset;
    }

    public int readPacket() {
        return readUnsignedByte();
    }

    public int readByte() {
        return remaining() > 0 ? buffer[offset++] : 0;
    }

    public int readUnsignedByte() {
        return readByte() & 0xff;
    }

    public int readByte128() {
        return (byte) (readByte() - 128);
    }

    public int readByteC() {
        return (byte) -readByte();
    }

    public int read128Byte() {
        return (byte) (128 - readByte());
    }

    public int readUnsignedByte128() {
        return readUnsignedByte() - 128 & 0xff;
    }

    public int readUnsignedByteC() {
        return -readUnsignedByte() & 0xff;
    }

    public int readUnsigned128Byte() {
        return 128 - readUnsignedByte() & 0xff;
    }

    public int readShortLE() {
        int i = readUnsignedByte() + (readUnsignedByte() << 8);
        if (i > 32767) {
            i -= 0x10000;
        }
        return i;
    }

    public int readShort128() {
        int i = (readUnsignedByte() << 8) + (readByte() - 128 & 0xff);
        if (i > 32767) {
            i -= 0x10000;
        }
        return i;
    }

    public int readShortLE128() {
        int i = (readByte() - 128 & 0xff) + (readUnsignedByte() << 8);
        if (i > 32767) {
            i -= 0x10000;
        }
        return i;
    }
	
    public int read128ShortLE() {
        int i = (128 - readByte() & 0xff) + (readUnsignedByte() << 8);
        if (i > 32767) {
            i -= 0x10000;
        }
        return i;
    }

    public int readShort() {
        int i = (readUnsignedByte() << 8) + readUnsignedByte();
        if (i > 32767) {
            i -= 0x10000;
        }
        return i;
    }
    
    
    public int readUnsignedShortLE() {
        return readUnsignedByte() + (readUnsignedByte() << 8);
    }

    public int readUnsignedShort() {
        return (readUnsignedByte() << 8) + readUnsignedByte();
    }

    public int readUnsignedShort128() {
        return (readUnsignedByte() << 8) + (readByte() - 128 & 0xff);
    }

    public int readUnsignedShortLE128() {
        return (readByte() - 128 & 0xff) + (readUnsignedByte() << 8);
    }

    public int readInt() {
        return (readUnsignedByte() << 24) + (readUnsignedByte() << 16) + (readUnsignedByte() << 8) + readUnsignedByte();
    }

    public int readIntV1() {
        return (readUnsignedByte() << 8) + readUnsignedByte() + (readUnsignedByte() << 24) + (readUnsignedByte() << 16);
    }

    public int readIntV2() {
        return (readUnsignedByte() << 16) + (readUnsignedByte() << 24) + readUnsignedByte() + (readUnsignedByte() << 8);
    }

    public int readIntLE() {
        return readUnsignedByte() + (readUnsignedByte() << 8) + (readUnsignedByte() << 16) + (readUnsignedByte() << 24);
    }

    public long readLong() {
        long l = readInt() & 0xffffffffL;
        long l1 = readInt() & 0xffffffffL;
        return (l << 32) + l1;
    }

    public String readString() {
        String s = "";
        int b;
        while ((b = readByte()) != 0) {
            s += (char) b;
        }
        return s;
    }

    public String readJagString() {
        readByte();
        String s = "";
        int b;
        while ((b = readByte()) != 0) {
            s += (char) b;
        }
        return s;
    }

    public String readRS2String() {
        int start = offset;
        while (buffer[offset++] != 0) ;
        return new String(buffer, start, offset - start - 1);
    }
    /*public int readSmart() {
         if (readByte() >= 128) {
             return readShort();
         } else {
             return readByte();
         }
     }*/
}