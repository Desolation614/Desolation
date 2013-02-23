package dragonkk.rs2rsps.rscache;

public class XTEADecrypter {

    public static byte[] decryptXTEA(int[] keys, byte[] data, int offset, int length) {
        int qword_count = (length - offset) / 8;
        XTEAStream x = new XTEAStream(data);
        x.pos = offset;
        for (int qword_pos = 0; qword_pos < qword_count; qword_pos++) {
            int dword_1 = x.readDWord();
            int dword_2 = x.readDWord();
            int const_1 = -957401312;
            int const_2 = -1640531527;
            int run_count = 32;
            while ((run_count-- ^ 0xffffffff) < -1) {
				dword_2 -= ((dword_1 >>> -1563092443 ^ dword_1 << 611091524) + dword_1 ^ const_1 + keys[const_1 >>> -1002502837 & 0x56c00003]);
				const_1 -= const_2;
				dword_1 -= ((dword_2 >>> 1337206757 ^ dword_2 << 363118692) - -dword_2 ^ const_1 + keys[const_1 & 0x3]);
            }
            x.pos -= 8;
            x.writeDWord(dword_1);
            x.writeDWord(dword_2);
        }
        return x.buffer;
    }

    private static class XTEAStream {

        public byte[] buffer;
        public int pos;

        public XTEAStream(byte[] data){
            buffer = data;
            pos = 0;
        }

        public void writeDWord(int i_29_) {
            buffer[pos++] = (byte) (i_29_ >> 544537784);
            buffer[pos++] = (byte) (i_29_ >> 322362640);
            buffer[pos++] = (byte) (i_29_ >> 680567848);
            buffer[pos++] = (byte) i_29_;
        }

        public int readDWord() {
            pos += 4;
            return ((buffer[pos - 1] & 0xff) + ((buffer[pos - 3] << 16 & 0xff0000) + (((buffer[pos - 4] & 0xff) << 24) + (buffer[pos - 2] << 8 & 0xff00))));
        }
    }
}