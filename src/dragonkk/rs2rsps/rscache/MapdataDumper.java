package dragonkk.rs2rsps.rscache;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import dragonkk.rs2rsps.util.MapData;

public class MapdataDumper {

	public static void main(String[] args) {
		new Cache();
		new MapData();
		for (int id = 0; id < 20000; id++) {
			try {
				checkMapData(id);
			} catch (OutOfMemoryError e) {
				continue;
			}
		}
		// getXteas(7503);
	}

	public static void getXteas(int id) {
		if (MapData.getMapData().get((short) id) != null) {
			System.out.println("Mapdata already exists");
			return;
		}
		int absX = (id >> 8) * 64;
		int absY = (id & 0xff) * 64;
		byte[] objectmapbytes = CacheManager.getByName(5, "l"
				+ ((absX >> 3) / 8) + "_" + ((absY >> 3) / 8));
		if (objectmapbytes == null) {
			System.out.println("Missing landScapeFile: " + "l"
					+ ((absX >> 3) / 8) + "_" + ((absY >> 3) / 8));
			return;
		}
		try {
			int[] xtea_keys = new int[] { 0, 0, 0, 0 };
			new ByteInputStream(new CacheContainer(XTEADecrypter.decryptXTEA(
					xtea_keys, objectmapbytes, 5, objectmapbytes.length))
					.decompress());
			System.out.println("No xteas needed");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} catch (Error e) {
		}
		long startTime = System.currentTimeMillis();
		long trys = 0;
		for (int x1 = Integer.MIN_VALUE; x1 < Integer.MAX_VALUE; x1 *= 256) {
			for (int x2 = Integer.MIN_VALUE; x2 < Integer.MAX_VALUE; x2 *= 256) {
				for (int x3 = Integer.MIN_VALUE; x3 < Integer.MAX_VALUE; x3 *= 256) {
					for (int x4 = Integer.MIN_VALUE; x4 < Integer.MAX_VALUE; x4 *= 256) {
						int[] xtea_keys = new int[] { x1, x2, x3, x4 };
						try {
							new ByteInputStream(new CacheContainer(
									XTEADecrypter.decryptXTEA(xtea_keys,
											objectmapbytes, 5,
											objectmapbytes.length))
									.decompress());
						} catch (Exception e) {
							e.printStackTrace();
							return;
						} catch (Error e) {
							// e.printStackTrace();
							trys++;
							continue;
						}
						try {
							File file = new File("./mapdata/workingkeys/" + id
									+ ".txt");
							if (file.exists())
								return;
							BufferedWriter bf = new BufferedWriter(
									new FileWriter(file));
							bf.write(String.valueOf(xtea_keys[0]));
							bf.newLine();
							bf.flush();
							bf.write(String.valueOf(xtea_keys[1]));
							bf.newLine();
							bf.flush();
							bf.write(String.valueOf(xtea_keys[2]));
							bf.newLine();
							bf.flush();
							bf.write(String.valueOf(xtea_keys[3]));
							bf.newLine();
							bf.flush();
							bf.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

	}

	public static void checkMapData(int id) {
		int[] xtea_keys = MapData.getMapData().get((short) id);
		if (xtea_keys == null)
			return;
		int absX = (id >> 8) * 64;
		int absY = (id & 0xff) * 64;
		System.out.println("id: "+id+" absX: "+absX+" absY: "+absY);
		try {
			ByteInputStream objectmap = null;
			byte[] objectmapbytes = CacheManager.getByName(5, "l"
					+ ((absX >> 3) / 8) + "_" + ((absY >> 3) / 8));
			if (objectmapbytes == null) {
				System.out.println("Erm region doesnt exist weird.");
				return;
			}
			byte[] raw_data_objectmap;
			raw_data_objectmap = XTEADecrypter.decryptXTEA(xtea_keys,
					objectmapbytes, 5, objectmapbytes.length);
			objectmap = new ByteInputStream(new CacheContainer(
					raw_data_objectmap).decompress());
			if (objectmap == null) {
				System.out.println("Erm objectmap doesnt exist weird. "+id);
				return;
			}
		} catch (Exception e) {
			System.out.println("exception: "+id);
			return;
		} catch (Error e) {
			System.out.println("error: "+id);
			return;
		}
		try {
			File file = new File("./mapdata/workingkeys/" + id + ".txt");
			if (file.exists())
				return;
			BufferedWriter bf = new BufferedWriter(new FileWriter(file));
			bf.write(String.valueOf(xtea_keys[0]));
			bf.newLine();
			bf.flush();
			bf.write(String.valueOf(xtea_keys[1]));
			bf.newLine();
			bf.flush();
			bf.write(String.valueOf(xtea_keys[2]));
			bf.newLine();
			bf.flush();
			bf.write(String.valueOf(xtea_keys[3]));
			bf.newLine();
			bf.flush();
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
