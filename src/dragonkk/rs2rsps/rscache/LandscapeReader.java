package dragonkk.rs2rsps.rscache;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

import dragonkk.rs2rsps.util.MapData;
import dragonkk.rs2rsps.util.RSFlag;
import dragonkk.rs2rsps.util.RSObject;

public class LandscapeReader {

	public static void read(int id, HashMap<String, RSObject> objects, HashMap<String, RSFlag> flags) {
		int absX = (id >> 8) * 64;
		int absY = (id & 0xff) * 64;
		ByteInputStream objectmap = null;
		RSInputStream landscape = null;
		byte[] landscapebytes = CacheManager.getByName(5, "m"+((absX >> 3) / 8)+"_"+((absY >> 3) / 8));
		byte[] objectmapbytes = CacheManager.getByName(5, "l"+((absX >> 3) / 8)+"_"+((absY >> 3) / 8));
		try {
			int[] xtea_keys = MapData.getMapData().get((short)(((((absX >> 3) / 8) << 8) + ((absY >> 3) / 8))));
			byte[] raw_data_objectmap;
			if (xtea_keys != null) {
				raw_data_objectmap = XTEADecrypter.decryptXTEA(xtea_keys, objectmapbytes, 5, objectmapbytes.length);
			} else {
				raw_data_objectmap = objectmapbytes;
			}
			objectmap = new ByteInputStream(new CacheContainer(raw_data_objectmap).decompress());
			landscape = new RSInputStream(new ByteArrayInputStream(new CacheContainer(landscapebytes).decompress()));
		} catch (Exception e) {
			return;
		}
		byte[][][] someArray = new byte[4][64][64];
		try {
			for (int z = 0; z < 4; z++) {
				for (int localX = 0; localX < 64; localX++) {
					for (int localY = 0; localY < 64; localY++) {
						while (true) {
							int v = landscape.readByte() & 0xff;
							if (v == 1) {
								landscape.readByte();
								break;
							} else if (v <= 49) {
								landscape.readByte();
							} else if (v <= 81) {
								someArray[z][localX][localY] = (byte) (v - 49);
							}
						}
					}
				}
			}
			for (int z = 0; z < 4; z++) {
				for (int localX = 0; localX < 64; localX++) {
					for (int localY = 0; localY < 64; localY++) {
						if ((someArray[z][localX][localY] & 1) == 1) {
							int height = z;
							if ((someArray[1][localX][localY] & 2) == 2) {
								height--;
							}
							if (height >= 0 && height <= 3) {
								flags.put(localX+"_"+localY, new RSFlag(absX + localX, absY + localY, height, 0x200000));
							}
						}
					}
				}
			}
			int incr;
			while ((incr = objectmap.readSpaceSaver()) != 0) {
				id += incr;
				int location = 0;
				int incr2;
				while ((incr2 = objectmap.readSpaceSaver()) != 0) {
					location += incr2 - 1;
					int localX = (location >> 6 & 0x3f);
					int localY = (location & 0x3f);
					int z = location >> 12;
					int objectData = objectmap.readUByte() & 0xff;
					int type = objectData >> 2;
					int rotation = objectData & 0x3;
					if (localX < 0 || localX >= 64 || localY < 0 || localY >= 64) {
						continue;
					}
					if ((someArray[1][localX][localY] & 2) == 2) {
						z--;
					}
					if (z >= 0 && z <= 3) {
						objects.put(localX+"_"+localY ,new RSObject(id, localX + absX, localY + absY, z, type, rotation));
					}
				}
			}
		} catch (Exception e) {
			return;
		}
	}
}