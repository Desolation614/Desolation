package dragonkk.rs2rsps.rscache;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class RSOutputStream extends DataOutputStream {

    public RSOutputStream(OutputStream out) {
        super(out);
    }

    public RSOutputStream createNewStream(){
        return new RSOutputStream(new ByteArrayOutputStream());
    }

    public FrameOutputStream createFrame(int id){
        return new FrameOutputStream(id);
    }

    public void endFrameVarSizeShort(FrameOutputStream f) throws IOException {
        byte[] data = f.toByteArray();
        write(f.getId());
        writeShort(data.length);
        write(data,0,data.length);
    }

    public byte[] toByteArray() throws IOException {
        flush();
        byte[] b= ((ByteArrayOutputStream)out).toByteArray();
        ((ByteArrayOutputStream)out).reset();
        return b;
    }

    public void endFrameVarSizeByte(FrameOutputStream f) throws IOException {
        byte[] data = f.toByteArray();
        write(f.getId());
        writeByte(data.length);
        write(data,0,data.length);
    }

    public void endFrame(FrameOutputStream f) throws IOException {
        byte[] data = f.toByteArray();
        write(f.getId());
        write(data,0,data.length);
    }

    public void writeShortLEA(int i) throws IOException {
        writeByte(i + 128);
        writeByte(i >> 8);
    }

    public void writeShortA(int i) throws IOException {
        writeByte(i >> 8);
        writeByte(i + 128);
    }

    public void writeByteC(int i) throws IOException {
        writeByte(-i);
    }

    public BitwiseByteBuffer createBitBuffer(){
        BitwiseByteBuffer b = new BitwiseByteBuffer();
        b.initBitAccess();
        return b;
    }

    public void writeBitBuffer(BitwiseByteBuffer b) throws IOException {
        b.finishBitAccess();
        write(b.outBuffer,0,b.outOffset);
    }

    public void writeByteA(int i) throws IOException {
        writeByte(i + 128);
    }

    public void writeByteS(int i) throws IOException {
        writeByte(128 - i);
    }

    public void writeDWord_v1(int i) throws IOException {
        writeByte(i >> 8);
        writeByte(i);
        writeByte(i >> 24);
        writeByte(i >> 16);
    }

    public void writeCString(String s) throws IOException {
        this.writeBytes(s);
        this.writeByte(0);
    }
}