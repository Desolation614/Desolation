package dragonkk.rs2rsps.cache;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

import dragonkk.rs2rsps.io.OutStream;
import dragonkk.rs2rsps.util.Misc;



public class RevengeScapeCacheWriter {

	private static Map<Integer, IdxSystemWriter> idxs;
	private static DataSystemWriter data;
	
	public static void main(String[] args) throws FileNotFoundException {
		writeCacheIdxs();
	}
	private static OutStream createTestCacheFile(int fileId) {
		OutStream stream = new OutStream();
		stream.writeString("lol works. FileId: "+fileId);
		return stream;
	}
	public static void writeCacheIdxs() throws FileNotFoundException {
		setIdxs(new HashMap<Integer, IdxSystemWriter>());
		setData(new DataSystemWriter(new RandomAccessFile("./revengeScapeCache/main_file_cache.dat", "rw")));
		getIdxs().put(0, new IdxSystemWriter(new RandomAccessFile("./revengeScapeCache/main_file_cache.idx0", "rw"), 0));
		getIdxs().put(1, new IdxSystemWriter(new RandomAccessFile("./revengeScapeCache/main_file_cache.idx1", "rw"), 1));
		getIdxs().get(0).writeFile(0, createTestCacheFile(0));
		getIdxs().get(1).writeFile(Misc.stringToLong("test"), createTestCacheFile(500));
	}

	public static void setIdxs(Map<Integer, IdxSystemWriter> idxs) {
		RevengeScapeCacheWriter.idxs = idxs;
	}

	public static Map<Integer, IdxSystemWriter> getIdxs() {
		return idxs;
	}

	public static void setData(DataSystemWriter data) {
		RevengeScapeCacheWriter.data = data;
	}

	public static DataSystemWriter getData() {
		return data;
	}
	
	
}
