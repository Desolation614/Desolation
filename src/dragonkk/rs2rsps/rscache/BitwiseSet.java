package dragonkk.rs2rsps.rscache;

public class BitwiseSet {

    private RSOutputStream basestream;
    private FrameOutputStream framestream;
    private BitwiseByteBuffer bitstream;

    public BitwiseSet(RSOutputStream r, FrameOutputStream f, BitwiseByteBuffer b) {
        basestream = r;
        framestream = f;
        bitstream = b;
    }

    public RSOutputStream getBasestream() {
        return basestream;
    }

    public void setBasestream(RSOutputStream basestream) {
        this.basestream = basestream;
    }

    public FrameOutputStream getFramestream() {
        return framestream;
    }

    public void setFramestream(FrameOutputStream framestream) {
        this.framestream = framestream;
    }

    public BitwiseByteBuffer getBitstream() {
        return bitstream;
    }

    public void setBitstream(BitwiseByteBuffer bitstream) {
        this.bitstream = bitstream;
    }
}