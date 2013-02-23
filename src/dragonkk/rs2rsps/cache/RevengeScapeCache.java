package dragonkk.rs2rsps.cache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

import dragonkk.rs2rsps.io.InStream;
import dragonkk.rs2rsps.util.Misc;



public class RevengeScapeCache {

	private static Map<Integer, IdxSystem> idxs;
	private static DataSystem data;
	
	public static void main(String[] args) {
		loadCacheFiles();
		test();
	}
	
	public static void loadCacheFiles() {
		setIdxs(new HashMap<Integer, IdxSystem>());
		File[] cacheFiles = new File("./revengeScapeCache/").listFiles();
		for(File cacheFile : cacheFiles) {
			String name = cacheFile.getName();
			try {
			if(name.equals("main_file_cache.dat")) {
				setData(new DataSystem(new RandomAccessFile(cacheFile, "r")));
			}else if(name.contains("main_file_cache.idx")) {
				getIdxs().put(Integer.valueOf(name.replace("main_file_cache.idx", "")) , new IdxSystem(new RandomAccessFile(cacheFile, "r")));
			}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void test() {
		InStream in = getIdxs().get(0).getData(0);
		System.out.println(in.readString());
		InStream in2 = getIdxs().get(1).getData(Misc.stringToLong("test"));
		System.out.println(in2.readString());
		//System.out.println(getIdxs().get(0).getType());
	}

	public static void setIdxs(Map<Integer, IdxSystem> idxs) {
		RevengeScapeCache.idxs = idxs;
	}

	public static Map<Integer, IdxSystem> getIdxs() {
		return idxs;
	}

	public static void setData(DataSystem data) {
		RevengeScapeCache.data = data;
	}

	public static DataSystem getData() {
		return data;
	}
	
	
}
