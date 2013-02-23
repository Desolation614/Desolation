package dragonkk.rs2rsps.model;

/**
 * @author Dalton Harriman (Palidino/Palidino76)
 */


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.rscache.ByteInputStream;
import dragonkk.rs2rsps.rscache.CacheContainer;
import dragonkk.rs2rsps.rscache.CacheManager;
import dragonkk.rs2rsps.rscache.ObjectDefinitions;
import dragonkk.rs2rsps.rscache.RSInputStream;
import dragonkk.rs2rsps.rscache.XTEADecrypter;
import dragonkk.rs2rsps.util.MapData;
import dragonkk.rs2rsps.util.Misc;
import dragonkk.rs2rsps.util.RSObject;

public class WorldRegion {
    private static ArrayList<RSObject> customObjects = new ArrayList<RSObject>();
    public static List<WorldRegion> regions = new ArrayList<WorldRegion>();
    private static int index;
    private int id;
    private int[][][] clips;
    private RSObject[][][] rsObjects;
    private ArrayList<Player> players;

    public WorldRegion(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    public ArrayList<Player> players() {
        return players;
    }

    public void addPlayer(Player p) {
        if (players == null) {
            players = new ArrayList<Player>();
        }
        if (!players.contains(p)) {
            players.add(p);
        }
    }

    public void removePlayer(Player p) {
        if (players == null) {
            return;
        }
        players.remove(p);
        if (players.isEmpty()) {
            players = null;
        }
    }

    private void addClip(int x, int y, int height, int shift) {
        int regionAbsX = (id >> 8) * 64;
        int regionAbsY = (id & 0xff) * 64;
        if (clips == null) {
            clips = new int[4][][];
        }
        if (clips[height] == null) {
            clips[height] = new int[64][64];
        }
        clips[height][x - regionAbsX][y - regionAbsY] |= shift;
    }

    private void removeClip(int x, int y, int height, int shift) {
        int regionAbsX = (id >> 8) * 64;
        int regionAbsY = (id & 0xff) * 64;
        if (clips == null) {
            return;
        }
        if (clips[height] == null) {
            return;
        }
        clips[height][x - regionAbsX][y - regionAbsY] &= 16777215 - shift;
    }

    private void addRSObject2(int x, int y, int height, RSObject object) {
        int regionAbsX = (id >> 8) * 64;
        int regionAbsY = (id & 0xff) * 64;
        if (rsObjects == null) {
            rsObjects = new RSObject[4][][];
        }
        if (rsObjects[height] == null) {
            rsObjects[height] = new RSObject[64][64];
        }
        RSObject oldObject = rsObjects[height][x - regionAbsX][y - regionAbsY];
        if (oldObject != null && object != null && !ObjectDefinitions.forID(object.getId()).hasActions()) {
            return;
        }
        rsObjects[height][x - regionAbsX][y - regionAbsY] = object;
    }

    private int getClip(int x, int y, int height) {
        int regionAbsX = (id >> 8) * 64;
        int regionAbsY = (id & 0xff) * 64;
        if (clips == null || clips[height] == null) {
            return 0;
        }
        return clips[height][x - regionAbsX][y - regionAbsY];
    }

    public RSObject getRSObject2(int x, int y, int height) {
       
    	int regionAbsX = (id >> 8) * 64;
        int regionAbsY = (id & 0xff) * 64;
        if (rsObjects == null || rsObjects[height] == null) {
            return null;
        }
        return rsObjects[height][x - regionAbsX][y - regionAbsY];
    }

    private static void addClipping(int x, int y, int height, int shift) {
        int regionX = x >> 3;
        int regionY = y >> 3;
        int regionId = ((regionX / 8) << 8) + (regionY / 8);
        for (WorldRegion r : regions) {
            if (r.id() == regionId) {
                r.addClip(x, y, height, shift);
                break;
            }
        }
    }

    private static void removeClipping(int x, int y, int height, int shift) {
        int regionX = x >> 3;
        int regionY = y >> 3;
        int regionId = ((regionX / 8) << 8) + (regionY / 8);
        for (WorldRegion r : regions) {
            if (r.id() == regionId) {
                r.removeClip(x, y, height, shift);
                break;
            }
        }
    }

    private static void addRSObject(int x, int y, int height, RSObject object) {
        int regionX = x >> 3;
        int regionY = y >> 3;
        int regionId = ((regionX / 8) << 8) + (regionY / 8);
        for (WorldRegion r : regions) {
            if (r.id() == regionId) {
                r.addRSObject2(x, y, height, object);
                break;
            }
        }
    }

    private static void addClippingForVariableObject(int x, int y, int height, int type, int direction, boolean flag) {
	if (type == 0) {
	    if (direction == 0) {
                addClipping(x, y, height, 128);
                addClipping(x - 1, y, height, 8);
	    } else if (direction == 1) {
                addClipping(x, y, height, 2);
                addClipping(x, y + 1, height, 32);
	    } else if (direction == 2) {
                addClipping(x, y, height, 8);
                addClipping(x + 1, y, height, 128);
	    } else if (direction == 3) {
                addClipping(x, y, height, 32);
                addClipping(x, y - 1, height, 2);
	    }
	} else if (type == 1 || type == 3) {
	    if (direction == 0) {
                addClipping(x, y, height, 1);
                addClipping(x - 1, y, height, 16);
	    } else if (direction == 1) {
                addClipping(x, y, height, 4);
                addClipping(x + 1, y + 1, height, 64);
	    } else if (direction == 2) {
                addClipping(x, y, height, 16);
                addClipping(x + 1, y - 1, height, 1);
	    } else if (direction == 3) {
                addClipping(x, y, height, 64);
                addClipping(x - 1, y - 1, height, 4);
	    }
	} else if (type == 2) {
	    if (direction == 0) {
                addClipping(x, y, height, 130);
                addClipping(x - 1, y, height, 8);
                addClipping(x, y + 1, height, 32);
	    } else if (direction == 1) {
                addClipping(x, y, height, 10);
                addClipping(x, y + 1, height, 32);
                addClipping(x + 1, y, height, 128);
	    } else if (direction == 2) {
                addClipping(x, y, height, 40);
                addClipping(x + 1, y, height, 128);
                addClipping(x, y - 1, height, 2);
	    } else if (direction == 3) {
                addClipping(x, y, height, 160);
                addClipping(x, y - 1, height, 2);
                addClipping(x - 1, y, height, 8);
	    }
	}
	if (flag) {
	    if (type == 0) {
		if (direction == 0) {
                    addClipping(x, y, height, 65536);
                    addClipping(x - 1, y, height, 4096);
		} else if (direction == 1) {
                    addClipping(x, y, height, 1024);
                    addClipping(x, y + 1, height, 16384);
		} else if (direction == 2) {
                    addClipping(x, y, height, 4096);
                    addClipping(x + 1, y, height, 65536);
		} else if (direction == 3) {
                    addClipping(x, y, height, 16384);
                    addClipping(x, y - 1, height, 1024);
		}
	    }
	    if (type == 1 || type == 3) {
		if (direction == 0) {
                    addClipping(x, y, height, 512);
                    addClipping(x - 1, y + 1, height, 8192);
		} else if (direction == 1) {
                    addClipping(x, y, height, 2048);
                    addClipping(x + 1, y + 1, height, 32768);
		} else if (direction == 2) {
                    addClipping(x, y, height, 8192);
                    addClipping(x + 1, y + 1, height, 512);
		} else if (direction == 3) {
                    addClipping(x, y, height, 32768);
                    addClipping(x - 1, y - 1, height, 2048);
		}
	    } else if (type == 2) {
		if (direction == 0) {
                    addClipping(x, y, height, 66560);
                    addClipping(x - 1, y, height, 4096);
                    addClipping(x, y + 1, height, 16384);
		} else if (direction == 1) {
                    addClipping(x, y, height, 5120);
                    addClipping(x, y + 1, height, 16384);
                    addClipping(x + 1, y, height, 65536);
		} else if (direction == 2) {
                    addClipping(x, y, height, 20480);
                    addClipping(x + 1, y, height, 65536);
                    addClipping(x, y - 1, height, 1024);
		} else if (direction == 3) {
                    addClipping(x, y, height, 81920);
                    addClipping(x, y - 1, height, 1024);
                    addClipping(x - 1, y, height, 4096);
		}
	    }
	}
    }

    private static void removeClippingForVariableObject(int x, int y, int height, int type, int direction, boolean flag) {
	if (type == 0) {
	    if (direction == 0) {
                removeClipping(x, y, height, 128);
                removeClipping(x - 1, y, height, 8);
	    } else if (direction == 1) {
                removeClipping(x, y, height, 2);
                removeClipping(x, y + 1, height, 32);
	    } else if (direction == 2) {
                removeClipping(x, y, height, 8);
                removeClipping(x + 1, y, height, 128);
	    } else if (direction == 3) {
                removeClipping(x, y, height, 32);
                removeClipping(x, y - 1, height, 2);
	    }
	} else if (type == 1 || type == 3) {
	    if (direction == 0) {
                removeClipping(x, y, height, 1);
                removeClipping(x - 1, y, height, 16);
	    } else if (direction == 1) {
                removeClipping(x, y, height, 4);
                removeClipping(x + 1, y + 1, height, 64);
	    } else if (direction == 2) {
                removeClipping(x, y, height, 16);
                removeClipping(x + 1, y - 1, height, 1);
	    } else if (direction == 3) {
                removeClipping(x, y, height, 64);
                removeClipping(x - 1, y - 1, height, 4);
	    }
	} else if (type == 2) {
	    if (direction == 0) {
                removeClipping(x, y, height, 130);
                removeClipping(x - 1, y, height, 8);
                removeClipping(x, y + 1, height, 32);
	    } else if (direction == 1) {
                removeClipping(x, y, height, 10);
                removeClipping(x, y + 1, height, 32);
                removeClipping(x + 1, y, height, 128);
	    } else if (direction == 2) {
                removeClipping(x, y, height, 40);
                removeClipping(x + 1, y, height, 128);
                removeClipping(x, y - 1, height, 2);
	    } else if (direction == 3) {
                removeClipping(x, y, height, 160);
                removeClipping(x, y - 1, height, 2);
                removeClipping(x - 1, y, height, 8);
	    }
	}
	if (flag) {
	    if (type == 0) {
		if (direction == 0) {
                    removeClipping(x, y, height, 65536);
                    removeClipping(x - 1, y, height, 4096);
		} else if (direction == 1) {
                    removeClipping(x, y, height, 1024);
                    removeClipping(x, y + 1, height, 16384);
		} else if (direction == 2) {
                    removeClipping(x, y, height, 4096);
                    removeClipping(x + 1, y, height, 65536);
		} else if (direction == 3) {
                    removeClipping(x, y, height, 16384);
                    removeClipping(x, y - 1, height, 1024);
		}
	    }
	    if (type == 1 || type == 3) {
		if (direction == 0) {
                    removeClipping(x, y, height, 512);
                    removeClipping(x - 1, y + 1, height, 8192);
		} else if (direction == 1) {
                    removeClipping(x, y, height, 2048);
                    removeClipping(x + 1, y + 1, height, 32768);
		} else if (direction == 2) {
                    removeClipping(x, y, height, 8192);
                    removeClipping(x + 1, y + 1, height, 512);
		} else if (direction == 3) {
                    removeClipping(x, y, height, 32768);
                    removeClipping(x - 1, y - 1, height, 2048);
		}
	    } else if (type == 2) {
		if (direction == 0) {
                    removeClipping(x, y, height, 66560);
                    removeClipping(x - 1, y, height, 4096);
                    removeClipping(x, y + 1, height, 16384);
		} else if (direction == 1) {
                    removeClipping(x, y, height, 5120);
                    removeClipping(x, y + 1, height, 16384);
                    removeClipping(x + 1, y, height, 65536);
		} else if (direction == 2) {
                    removeClipping(x, y, height, 20480);
                    removeClipping(x + 1, y, height, 65536);
                    removeClipping(x, y - 1, height, 1024);
		} else if (direction == 3) {
                    removeClipping(x, y, height, 81920);
                    removeClipping(x, y - 1, height, 1024);
                    removeClipping(x - 1, y, height, 4096);
		}
	    }
	}
    }

    private static void addClippingForSolidObject(int x, int y, int height, int xLength, int yLength, boolean flag) {
        int clipping = 256;
        if (flag) {
            clipping += 0x20000;
        }
        for (int i = x; i < x + xLength; i++) {
            for (int i2 = y; i2 < y + yLength; i2++) {
                addClipping(i, i2, height, clipping);
            }
        }
    }

    private static void removeClippingForSolidObject(int x, int y, int height, int xLength, int yLength, boolean flag) {
        int clipping = 256;
        if (flag) {
            clipping += 0x20000;
        }
        for (int i = x; i < x + xLength; i++) {
            for (int i2 = y; i2 < y + yLength; i2++) {
                removeClipping(i, i2, height, clipping);
            }
        }
    }

    private static void addObject(int objectId, int x, int y, int height, int type, int direction, boolean ignoreObjects) {
        if (!ignoreObjects && objectId == -1) {
            removeObject(x, y, height);
        }
        if (objectId == -1) {
            return;
        }
        ObjectDefinitions def = ObjectDefinitions.forID(objectId);
        if (def == null) {
            return;
        }
        int xLength;
        int yLength;
        if (direction != 1 && direction != 3) {
            xLength = def.sizeX;
            yLength = def.sizeY;
        } else {
            xLength = def.sizeY;
            yLength = def.sizeX;
        }
        boolean objectAdded = false;
        if (type == 22) {
            if (def.getNumberOfActions() == 1) {
                if (!ignoreObjects) {
                    removeObject(x, y, height);
                }
                addClipping(x, y, height, 0x200000);
                addRSObject(x, y, height, new RSObject(objectId, x, y, height, type, direction));
                objectAdded = true;
            }
        } else if (type >= 9) {
            if (def.getNumberOfActions() != 0) {
                if (!ignoreObjects) {
                    removeObject(x, y, height);
                }
                addClippingForSolidObject(x, y, height, xLength, yLength, false);//def.solid());
                addRSObject(x, y, height, new RSObject(objectId, x, y, height, type, direction));
                objectAdded = true;
            }
        } else if (type >= 0 && type <= 3) {
            if (def.getNumberOfActions() != 0) {
                if (!ignoreObjects) {
                    removeObject(x, y, height);
                }
                addClippingForVariableObject(x, y, height, type, direction, false);// def.solid());
                addRSObject(x, y, height, new RSObject(objectId, x, y, height, type, direction));
                objectAdded = true;
            }
        }
        if (!objectAdded && def.hasActions()) {
            addRSObject(x, y, height, new RSObject(objectId, x, y, height, type, direction));
        }
    }

    private static void removeObject(int x, int y, int height) {
        RSObject oldObj = getRSObject(x, y, height);
        addRSObject(x, y, height, null);
        if (oldObj != null) {
        	ObjectDefinitions def = ObjectDefinitions.forID(oldObj.getId());
            int xLength;
            int yLength;
            if (oldObj.getRotation() != 1 && oldObj.getRotation() != 3) {
                xLength = def.sizeX;
                yLength = def.sizeY;
            } else {
                xLength = def.sizeY;
                yLength = def.sizeX;
            }
            if (oldObj.getType() == 22) {
                if (def.getNumberOfActions() == 1) {
                    removeClipping(x, y, height, 0x200000);
                }
            } else if (oldObj.getType() >= 9) {
                if (def.getNumberOfActions() != 0) {
                    removeClippingForSolidObject(x, y, height, xLength, yLength, false);//def.solid());
                }
            } else if (oldObj.getType() >= 0 && oldObj.getType() <= 3) {
                if (def.getNumberOfActions() != 0) {
                    removeClippingForVariableObject(x, y, height, oldObj.getType(), oldObj.getRotation(), false);//true);//, def.solid());
                }
            }
        }
    }
    
    
    
    
    
    
    
    
    
    /*private static void loadMap(byte[][] is, int []RegionIds, int P_RegionX, int P_RegionY) {
	    int i_0_ = is.length;
	    for (int i_1_ = 0; i_1_ < i_0_; i_1_++) {
			int[] is_2_ = null;
			byte[] is_3_ = is[i_1_]; //information of region1
			int i_4_ = RegionIds[i_1_] >> -1670060632;
		int i_5_ = 0xff & RegionIds[i_1_];
		int i_6_ = -P_RegionX + i_4_ * 64;
		int i_7_ = -P_RegionY + 64 * i_5_;
		
		if (is_3_ != null) { //TODO
			
		}
		
	    for (int i_9_ = 0; i_9_ < i_0_; i_9_++) {			//TODO
			int i_10_
			    = (-P_RegionX
			       + 64 * (RegionIds[i_9_] >> -2023849624));
			int i_11_ = (-P_RegionY
				     + 64 * (0xff & RegionIds[i_9_]));
			byte[] is_12_ = is[i_9_];
			/*if (is_12_ == null
			    && (Class173.anInt2372 ^ 0xffffffff) > -801) {
			    Class43.method522(-87);
			    for (int i_13_ = 0; i_13_ < i; i_13_++)
				Class209.method2754(64, i_11_, 64, -22467, i_13_,
						    i_10_);
			}
		
	    }
    }*/
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	@SuppressWarnings("unchecked")
	public static void loadRegionObjects(short RegionId, boolean arg0) {
		regions.add(new WorldRegion(RegionId));
		int absX = (RegionId >> 8) * 64;
		int absY = (RegionId & 0xff) * 64;
		ByteInputStream objectmap = null;
		RSInputStream landscape = null;
		byte[] landscapebytes;
		byte[] objectmapbytes;
		if (!arg0) {
			landscapebytes = CacheManager.getByName(5, "m" + ((absX >> 3) / 8)
					+ "_" + ((absY >> 3) / 8));
			objectmapbytes = CacheManager.getByName(5, "l" + ((absX >> 3) / 8)
					+ "_" + ((absY >> 3) / 8));
		} else {
			landscapebytes = CacheManager.getByName(5, "um" + ((absX >> 3) / 8)
					+ "_" + ((absY >> 3) / 8));
			objectmapbytes = CacheManager.getByName(5, "ul" + ((absX >> 3) / 8)
					+ "_" + ((absY >> 3) / 8));
		}
		try {
			int[] xtea_keys = MapData.getMapData().get(RegionId);
			byte[] raw_data_objectmap = null;
			if (xtea_keys != null) {
				if (objectmapbytes != null)
					if (!arg0)
						raw_data_objectmap = XTEADecrypter.decryptXTEA(
								xtea_keys, objectmapbytes, 5,
								objectmapbytes.length);
					else
						raw_data_objectmap = objectmapbytes;
			} else {
				raw_data_objectmap = objectmapbytes;
			}
			if (raw_data_objectmap != null)
				objectmap = new ByteInputStream(new CacheContainer(
						raw_data_objectmap).decompress());
			if (landscapebytes != null)
				landscape = new RSInputStream(new ByteArrayInputStream(
						new CacheContainer(landscapebytes).decompress()));
		} catch (OutOfMemoryError e) {
			return;
		} catch (Exception e) {
			return;
		}

		try {
			byte[][][] someArray = new byte[4][64][64];
			if (landscape != null) {
				for (int z = 0; z < (arg0 ? 1 : 4); z++) {
					for (int localX = 0; localX < 64; localX++) {
						for (int localY = 0; localY < 64; localY++) {
							while (true) {
								int v = landscape.readByte() & 0xff;
								if (v == 0) {
									break;
								} else if (v == 1) {
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
			}
			for (int z = 0; z < (arg0 ? 1 : 4); z++) {
				for (int localX = 0; localX < 64; localX++) {
					for (int localY = 0; localY < 64; localY++) {
						if ((someArray[z][localX][localY] & 1) == 1) {
							int height = z;
							if ((someArray[1][localX][localY] & 2) == 2) {
								height--;
							}
							if (height >= 0 && height <= 3) {
								 addClipping(absX + localX, absY + localY, height, 0x200000);
							}
						}
					}
				}
			}
			if (objectmap != null) {
				int objectId = -1;
				int incr;
				while ((incr = objectmap.readSmart2()) != 0) {
					objectId += incr;
					int location = 0;
					int incr2;
					while ((incr2 = objectmap.readSmart()) != 0) {
						location += incr2 - 1;
						int localX = (location >> 6 & 0x3f);
						int localY = (location & 0x3f);
						int z = location >> 12;
						int objectData = objectmap.readUByte();
						int type = objectData >> 2;
						int rotation = objectData & 0x3;
						if (localX < 0 || localX >= 64 || localY < 0
								|| localY >= 64) {
							continue;
						}
						if (!arg0) {
						if ((someArray[1][localX][localY] & 2) == 2) {
							z--;
						}
						}
						if (z >= 0 && z <= 3) {
							//addObject(objectId, localX + absX, localY + absY, z, type, rotation, true);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public static int getClipping(int x, int y, int height) {
        int regionX = x >> 3;
        int regionY = y >> 3;
        int regionId = ((regionX / 8) << 8) + (regionY / 8);
        for (WorldRegion r : regions) {
            if (r.id() == regionId) {
                return r.getClip(x, y, height);
            }
        }
        return 0x200000;
    }

    public static RSObject getRSObject(int x, int y, int height) {
        int regionX = x >> 3;
        int regionY = y >> 3;
        int regionId = ((regionX / 8) << 8) + (regionY / 8);
        for (WorldRegion r : regions) {
            if (r.id() == regionId) {
                return r.getRSObject2(x, y, height);
            }
        }
        return null;
    }

    public static Player[] getPlayers(int regionX, int regionY) {
        int count = 0;
        for (int x = regionX / 8; x <= (regionX + 12) / 8; x++) {
            for (int y = regionY / 8; y <= (regionY + 12) / 8; y++) {
                WorldRegion r = get((x << 8) + y);
                if (r != null && r.players() != null) {
                    count += r.players().size();
                }
            }
        }
        Player[] players = new Player[count];
        count = 0;
        for (int x = regionX / 8; x <= (regionX + 12) / 8; x++) {
            for (int y = regionY / 8; y <= (regionY + 12) / 8; y++) {
                WorldRegion r = get((x << 8) + y);
                if (r != null && r.players() != null) {
                    for (Player p : r.players()) {
                        players[count++] = p;
                    }
                }
            }
        }
        return players;
    }

    public static int[] getNextStep(int baseX, int baseY, int toX, int toY, int height, int xLength, int yLength) {
        int moveX = 0;
        int moveY = 0;
        if (baseX - toX > 0) {
            moveX--;
        } else if (baseX - toX < 0) {
            moveX++;
        }
        if (baseY - toY > 0) {
            moveY--;
        } else if (baseY - toY < 0) {
            moveY++;
        }
        if (canMove(baseX, baseY, baseX + moveX, baseY + moveY, height, xLength, yLength)) {
            return new int[] { baseX + moveX, baseY + moveY };
        } else if (moveX != 0 && canMove(baseX, baseY, baseX + moveX, baseY, height, xLength, yLength)) {
            return new int[] { baseX + moveX, baseY };
        } else if (moveY != 0 && canMove(baseX, baseY, baseX, baseY + moveY, height, xLength, yLength)) {
            return new int[] { baseX, baseY + moveY };
        }
        return new int[] { baseX, baseY };
    }

    public static boolean canMove(int startX, int startY, int endX, int endY, int height, int xLength, int yLength) {
        int diffX = endX - startX;
        int diffY = endY - startY;
        int max = Math.max(Math.abs(diffX), Math.abs(diffY));
        for (int ii = 0; ii < max; ii++) {
            int currentX = endX - diffX;
            int currentY = endY - diffY;
            for (int i = 0; i < xLength; i++) {
                for (int i2 = 0; i2 < yLength; i2++) {
                    if (diffX < 0 && diffY < 0) {
                        if ((getClipping(currentX + i - 1, currentY + i2 - 1, height) & 0x128010e) != 0 || (getClipping(currentX + i - 1, currentY + i2, height) & 0x1280108) != 0
                                || (getClipping(currentX + i, currentY + i2 - 1, height) & 0x1280102) != 0) {
                            return false;
                        }
                    } else if (diffX > 0 && diffY > 0) {
                        if ((getClipping(currentX + i + 1, currentY + i2 + 1, height) & 0x12801e0) != 0 || (getClipping(currentX + i + 1, currentY + i2, height) & 0x1280180) != 0
                                || (getClipping(currentX + i, currentY + i2 + 1, height) & 0x1280120) != 0) {
                            return false;
                        }
                    } else if (diffX < 0 && diffY > 0) {
                        if ((getClipping(currentX + i - 1, currentY + i2 + 1, height) & 0x1280138) != 0 || (getClipping(currentX + i - 1, currentY + i2, height) & 0x1280108) != 0
                                || (getClipping(currentX + i, currentY + i2 + 1, height) & 0x1280120) != 0) {
                            return false;
                        }
                    } else if (diffX > 0 && diffY < 0) {
                        if ((getClipping(currentX + i + 1, currentY + i2 - 1, height) & 0x1280183) != 0 || (getClipping(currentX + i + 1, currentY + i2, height) & 0x1280180) != 0
                                || (getClipping(currentX + i, currentY + i2 - 1, height) & 0x1280102) != 0) {
                            return false;
                        }
                    } else if (diffX > 0 && diffY == 0) {
                        if ((getClipping(currentX + i + 1, currentY + i2, height) & 0x1280180) != 0) {
                            return false;
                        }
                    } else if (diffX < 0 && diffY == 0) {
                        if ((getClipping(currentX + i - 1, currentY + i2, height) & 0x1280108) != 0) {
                            return false;
                        }
                    } else if (diffX == 0 && diffY > 0) {
                        if ((getClipping(currentX + i, currentY + i2 + 1, height) & 0x1280120) != 0) {
                            return false;
                        }
                    } else if (diffX == 0 && diffY < 0) {
                        if ((getClipping(currentX + i, currentY + i2 - 1, height) & 0x1280102) != 0) {
                            return false;
                        }
                    }
                }
            }
            if (diffX < 0) {
                diffX++;
            } else if (diffX > 0) {
                diffX--;
            }
            if (diffY < 0) {
                diffY++;
            } else if (diffY > 0) {
                diffY--;
            }
        }
        return true;
    }

    public static WorldRegion get(int regionId) {
        for (WorldRegion r : regions) {
            if (r.id() == regionId) {
                return r;
            }
        }
        return null;
    }
    public static boolean withinDistanceIgnoreHeight(Player p, RSObject object, int distance) {
    	return Misc.getDistance(p.getLocation().getLocalX(), p.getLocation().getLocalY(), object.getX(), object.getY()) <= 104;
    }
    public static boolean withinDistance(Player p, int newX, int newY, int distance) {
    	return Misc.getDistance(p.getLocation().getLocalX(), p.getLocation().getLocalY(), newX, newY) <= 104;
    }
    public static void displayObjects(Player p) {
        for (RSObject object : customObjects) {
            if (withinDistanceIgnoreHeight(p, object, 104)) {
            //    p.getActionSender().sendObject(object.id(), object.x(), object.y(), object.type(), object.direction());
            }
        }
    }

    /**
     * Returns the contents of the file in a byte array
     * @param file File this method should read
     * @return byte[] Returns a byte[] array of the contents of the file
     */
    private static byte[] getBytesFromFile(File file) throws IOException {

        InputStream is = new FileInputStream(file);
      //  System.out.println("\nDEBUG: FileInputStream is " + file);

        // Get the size of the file
        long length = file.length();
       // System.out.println("DEBUG: Length of " + file + " is " + length + "\n");

        /*
         * You cannot create an array using a long type. It needs to be an int
         * type. Before converting to an int type, check to ensure that file is
         * not loarger than Integer.MAX_VALUE;
         */
        if (length > Integer.MAX_VALUE) {
            System.out.println("File is too large to process");
            return null;
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while ( (offset < bytes.length)
                &&
                ( (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) ) {

            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;

    }

    public static void findRoute(Player p, int destX, int destY, boolean moveNear, int xLength, int yLength) {
        if (destX == p.getLocation().getLocalX() && destY == p.getLocation().getLocalY() && !moveNear || !withinDistance(p, destX, destY, 20)) {
        	System.out.println("return 1");
            return;
        }
        destX = destX - 8 * p.getLocation().getRegionX();
        destY = destY - 8 * p.getLocation().getRegionY();
        int[][] via = new int[104][104];
        int[][] cost = new int[104][104];
        LinkedList<Integer> tileQueueX = new LinkedList<Integer>();
        LinkedList<Integer> tileQueueY = new LinkedList<Integer>();
        for (int xx = 0; xx < 104; xx++) {
            for (int yy = 0; yy < 104; yy++) {
                cost[xx][yy] = 99999999;
            }
        }
        int curX = p.getLocation().getLocalX();
        int curY = p.getLocation().getLocalY();
        via[curX][curY] = 99;
        cost[curX][curY] = 0;
        int head = 0;
        int tail = 0;
        tileQueueX.add(curX);
        tileQueueY.add(curY);
        boolean foundPath = false;
        int pathLength = 4000;
        while (tail != tileQueueX.size() && tileQueueX.size() < pathLength) {
            curX = tileQueueX.get(tail);
            curY = tileQueueY.get(tail);
            int curAbsX = p.getLocation().getRegionX() * 8 + curX;
            int curAbsY = p.getLocation().getRegionY() * 8 + curY;
            if (curX == destX && curY == destY) {
                foundPath = true;
                break;
            }
            /*if (xLength != 0 && yLength != 0 && method422(curAbsX, curAbsY, p.getLocation().getZ(), absDestX, absDestY, xLength, yLength)) {
                foundPath = true;
                break;
            }*/
            tail = (tail + 1) % pathLength;
            int thisCost = cost[curX][curY] + 1;
            if (curY > 0 && via[curX][curY - 1] == 0 && (WorldRegion.getClipping(curAbsX, curAbsY - 1, p.getLocation().getZ()) & 0x1280102) == 0) {
                tileQueueX.add(curX);
                tileQueueY.add(curY - 1);
                via[curX][curY - 1] = 1;
                cost[curX][curY - 1] = thisCost;
            }
            if (curX > 0 && via[curX - 1][curY] == 0 && (WorldRegion.getClipping(curAbsX - 1, curAbsY, p.getLocation().getZ()) & 0x1280108) == 0) {
                tileQueueX.add(curX - 1);
                tileQueueY.add(curY);
                via[curX - 1][curY] = 2;
                cost[curX - 1][curY] = thisCost;
            }
            if (curY < 104 - 1 && via[curX][curY + 1] == 0 && (WorldRegion.getClipping(curAbsX, curAbsY + 1, p.getLocation().getZ()) & 0x1280120) == 0) {
                tileQueueX.add(curX);
                tileQueueY.add(curY + 1);
                via[curX][curY + 1] = 4;
                cost[curX][curY + 1] = thisCost;
            }
            if (curX < 104 - 1 && via[curX + 1][curY] == 0 && (WorldRegion.getClipping(curAbsX + 1, curAbsY, p.getLocation().getZ()) & 0x1280180) == 0) {
                tileQueueX.add(curX + 1);
                tileQueueY.add(curY);
                via[curX + 1][curY] = 8;
                cost[curX + 1][curY] = thisCost;
            }
            if (curX > 0 && curY > 0 && via[curX - 1][curY - 1] == 0
                    && (WorldRegion.getClipping(curAbsX - 1, curAbsY - 1, p.getLocation().getZ()) & 0x128010e) == 0
                    && (WorldRegion.getClipping(curAbsX - 1, curAbsY, p.getLocation().getZ()) & 0x1280108) == 0
                    && (WorldRegion.getClipping(curAbsX, curAbsY - 1, p.getLocation().getZ()) & 0x1280102) == 0) {
                tileQueueX.add(curX - 1);
                tileQueueY.add(curY - 1);
                via[curX - 1][curY - 1] = 3;
                cost[curX - 1][curY - 1] = thisCost;
            }
            if (curX > 0 && curY < 104 - 1 && via[curX - 1][curY + 1] == 0
                    && (WorldRegion.getClipping(curAbsX - 1, curAbsY + 1, p.getLocation().getZ()) & 0x1280138) == 0
                    && (WorldRegion.getClipping(curAbsX - 1, curAbsY, p.getLocation().getZ()) & 0x1280108) == 0
                    && (WorldRegion.getClipping(curAbsX, curAbsY + 1, p.getLocation().getZ()) & 0x1280120) == 0) {
                tileQueueX.add(curX - 1);
                tileQueueY.add(curY + 1);
                via[curX - 1][curY + 1] = 6;
                cost[curX - 1][curY + 1] = thisCost;
            }
            if (curX < 104 - 1 && curY > 0 && via[curX + 1][curY - 1] == 0
                    && (WorldRegion.getClipping(curAbsX + 1, curAbsY - 1, p.getLocation().getZ()) & 0x1280183) == 0
                    && (WorldRegion.getClipping(curAbsX + 1, curAbsY, p.getLocation().getZ()) & 0x1280180) == 0
                    && (WorldRegion.getClipping(curAbsX, curAbsY - 1, p.getLocation().getZ()) & 0x1280102) == 0) {
                tileQueueX.add(curX + 1);
                tileQueueY.add(curY - 1);
                via[curX + 1][curY - 1] = 9;
                cost[curX + 1][curY - 1] = thisCost;
            }
            if (curX < 104 - 1 && curY < 104 - 1 && via[curX + 1][curY + 1] == 0
                    && (WorldRegion.getClipping(curAbsX + 1, curAbsY + 1, p.getLocation().getZ()) & 0x12801e0) == 0
                    && (WorldRegion.getClipping(curAbsX + 1, curAbsY, p.getLocation().getZ()) & 0x1280180) == 0
                    && (WorldRegion.getClipping(curAbsX, curAbsY + 1, p.getLocation().getZ()) & 0x1280120) == 0) {
                tileQueueX.add(curX + 1);
                tileQueueY.add(curY + 1);
                via[curX + 1][curY + 1] = 12;
                cost[curX + 1][curY + 1] = thisCost;
            }
        }
        if (!foundPath) {
            if (moveNear) {
                int i_223_ = 1000;
                int thisCost = 100;
                int i_225_ = 10;
                for (int x = destX - i_225_; x <= destX + i_225_; x++) {
                    for (int y = destY - i_225_; y <= destY + i_225_; y++) {
                        if (x >= 0 && y >= 0 && x < 104 && y < 104 && cost[x][y] < 100) {
                            int i_228_ = 0;
                            if (x < destX)
                                i_228_ = destX - x;
                            else if (x > destX + xLength - 1)
                                i_228_ = x - (destX + xLength - 1);
                            int i_229_ = 0;
                            if (y < destY)
                                i_229_ = destY - y;
                            else if (y > destY + yLength - 1)
                                i_229_ = y - (destY + yLength - 1);
                            int i_230_ = i_228_ * i_228_ + i_229_ * i_229_;
                            if (i_230_ < i_223_ || (i_230_ == i_223_ && (cost[x][y] < thisCost))) {
                                i_223_ = i_230_;
                                thisCost = cost[x][y];
                                curX = x;
                                curY = y;
                            }
                        }
                    }
                }
                if (i_223_ == 1000)
                    return;
            } else {
                return;
            }
        }
        tail = 0;
        tileQueueX.set(tail, curX);
        tileQueueY.set(tail++, curY);
        int l5;
        for (int j5 = l5 = via[curX][curY]; curX != p.getLocation().getLocalX() || curY != p.getLocation().getLocalY(); j5 = via[curX][curY]) {
            if (j5 != l5) {
                l5 = j5;
                tileQueueX.set(tail, curX);
                tileQueueY.set(tail++, curY);
            }
            if ((j5 & 2) != 0) {
                curX++;
            } else if ((j5 & 8) != 0) {
                curX--;
            }
            if ((j5 & 1) != 0) {
                curY++;
            } else if ((j5 & 4) != 0) {
                curY--;
            }
        }
        p.getWalk().reset();
        int size = tail--;
        p.getWalk().addToWalkingQueue(tileQueueX.get(tail), tileQueueY.get(tail));
        for (int i = 1; i < size; i++) {
            tail--;
            p.getWalk().addToWalkingQueue(tileQueueX.get(tail), tileQueueY.get(tail));
            //lastPosition.setPosition(tileQueueX.get(tail), tileQueueY.get(tail));
        }
        /*clear(); //resets actual walking
        int size = tail--;
        addToWalking(p.getLocation().getRegionX() * 8 + tileQueueX.get(tail), p.getLocation().getRegionY() * 8 + tileQueueY.get(tail));
        for (int i = 1; i < size; i++) {
            tail--;
            addToWalking(p.getLocation().getRegionX() * 8 + tileQueueX.get(tail), p.getLocation().getRegionY() * 8 + tileQueueY.get(tail));
            lastPosition.setPosition(tileQueueX.get(tail), tileQueueY.get(tail));
        }*/
    }

    }
