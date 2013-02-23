package dragonkk.rs2rsps.rscache;

public class BitwiseByteBuffer {

    private int bitPosition;

    public int outOffset;

    public byte[] outBuffer = new byte[1];

    private static int[] bitMaskOut = new int[32];

    static {
        for (int i = 0; i < 32; i++) {
            bitMaskOut[i] = (1 << i) - 1;
        }
    }

    public void expandOutBuffer() {
        byte[] oldBuffer = outBuffer;
        outBuffer = new byte[oldBuffer.length + 1000];
        System.arraycopy(oldBuffer, 0, outBuffer, 0, oldBuffer.length);
    }

    public void initBitAccess() {
        bitPosition = outOffset * 8;
    }

    public void finishBitAccess() {
        outOffset = (bitPosition + 7) / 8;
    }

    public void addBit(int bit, int pos) {
        if (pos >= outBuffer.length) {
            expandOutBuffer();
        }
        outBuffer[pos] &= ~ bit;
    }

    public void placeBit(int bit, int pos) {
        if (pos >= outBuffer.length) {
            expandOutBuffer();
        }
        outBuffer[pos] |= bit;
    }

    public void writeBits(int numBits, int value) {
        int bytePos = bitPosition >> 3;
        int bitOffset = 8 - (bitPosition & 7);
        bitPosition += numBits;
        for (; numBits > bitOffset; bitOffset = 8) {
            addBit(bitMaskOut[bitOffset], bytePos);
            placeBit(((value >> (numBits - bitOffset)) & bitMaskOut[bitOffset]), bytePos++);
            numBits -= bitOffset;
        }
        if (numBits == bitOffset) {
            addBit(bitMaskOut[bitOffset], bytePos);
            placeBit((value & bitMaskOut[bitOffset]), bytePos);
        } else {
            addBit((bitMaskOut[numBits] << (bitOffset - numBits)), bytePos);
            placeBit((value & bitMaskOut[numBits]) << (bitOffset - numBits), bytePos);
        }
    }
}