package dragonkk.rs2rsps.rscache;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import dragonkk.rs2rsps.util.CacheConstants;



public class InterfaceDefinitions {

public int id;
public int childid;

public Object[] anObjectArray2296;
public int anInt2297;
public int anInt2298;
public int[] anIntArray2299;
public int anInt2300;
public int anInt2301;
public Object[] anObjectArray2302;
public int anInt2303;
public static int anInt2304;
public int anInt2305;
public boolean positionType;
public static int anInt2307;
public int rotatez = 0;
public static int anInt2309;
public int[] anIntArray2310;
public byte aByte2311;
public int textalignment;
public Object[] mouseMovedScript;
public int anInt2314;
public int[] anIntArray2315;
public Object[] skillChangedScript;
public byte[] aByteArray2317;
public Object[] mouseClickedScript;
public int anInt2319;
public static int anInt2320;
public int anInt2321;
public int anInt2322;
public int[] anIntArray2323;
public int anInt2324;
public int mask;
public int[][] anIntArrayArray2327;
public Object[] mouseDraggedScript;
public String aString2329;
public String aString2330;
public Object[] mouseDraggedOverScript;
public int anInt2332;
public int anInt2333;
public String aString2334;
public int anInt2335;
public Object[] anObjectArray2336;
public int[] anIntArray2337;
public int anInt2338;
public static int anInt2339;
public int anInt2340;
public byte heightOffset2;
public boolean horizontalFlip;
public int anInt2343;
public Object[] anObjectArray2344;
public static int anInt2346;
public int anInt2347;
public Object[] anObjectArray2348;
public int anInt2349;
public int anInt2350;
public Object[] leftClickArgs;
public Object[] anObjectArray2352;
public boolean aBoolean2353;
public static int anInt2354;
public boolean scripted;
public byte widthOffset2;
public String disabledText;
public static int anInt2358;
public int mediaIDDisabled;
public int[] anIntArray2360;
public int anInt2361;
public Object[] anObjectArray2362;
public String[] aStringArray2363;
public int anInt2364;
public int anInt2365;
public boolean shaded;
public boolean filled;
public boolean aBoolean2368;
public int shadeColor;
public static int anInt2370;
public Object[] mouseLeftScript;
public static int anInt2372;
public String aString2373;
public int anInt2374;
public int font;
public static int anInt2376;
public int disabledImage;
public static int anInt2378 = 0;
public int[] anIntArray2379;
public boolean aBoolean2380;
public int anInt2381;
public int anInt2382;
public short aShort2383;
public int[] anIntArray2384;
public String[] aStringArray2385;
public int anInt2386;
public static int anInt2387;
public int[] anIntArray2388;
public int anInt2389;
public int anInt2390;
public String unknownString;
public static int anInt2392;
public boolean aBoolean2393;
public int anInt2394;
public Object[] mouseReleasedScript;
public int anInt2396;
public int height3dArea;
public Object[] anObjectArray2399;
public int[] anIntArray2400;
public boolean aBoolean2401;
public Object[] mouseEnterScript;
public int zoom;
public boolean isHidden;
public Object[] leftClickParentARGS;
public static int anInt2406;
public int[] anIntArray2407;
public Object[] anObjectArray2408;
public int anInt2409;
public Object[] inventoryChangedScript;
public int anInt2411;
public int anInt2412;
public boolean aBoolean2413;
public int anInt2414;
public int anInt2415;
public int mediaTypeDisabled;
public byte[] aByteArray2417;
public int[] anIntArray2418;
public boolean verticalFlip;
public short aShort2420;
public int anInt2421;
public boolean loadmethod;
public int width3dArea;
public int anInt2424;
public Object[] anObjectArray2426;
public int anInt2427;
public static long aLong2428;
public boolean aBoolean2429;
public static int anInt2430;
public int[] anIntArray2431;
public int anInt2432;
public int rotation;
public boolean aBoolean2434;
public int anInt2435;
public boolean aBoolean2436;
public int anInt2437;
public int anInt2438;
public Object[] scriptArguments;
public int anInt2440;
public int anInt2441;
public int anInt2442;
public int disabledAnim;
public int scrollMaxH;
public int anInt2445;
public Object[] anObjectArray2446;
public Object[] anObjectArray2447;
public int anInt2448;
public int[] anIntArray2449;
public int anInt2450;
public int anInt2451;
public int[] anIntArray2452;
public int anInt2453;
public Object[] configResetScript;
public int idDWORD;
public int parentId;
public int anInt2457;
public int anInt2458;
public int area3dY;
public static int anInt2460;
public int rotatex;
public Object[] anObjectArray2462;
public String selectedActionName;
public Object[] anObjectArray2464;
public Object[] activeProcessScript;
public static int anInt2466;
public int disabledColor;
public static int anInt2468;
public byte aByte2469;
public int type;
public int anInt2471;
public int[] anIntArray2472;
public String aString2473;
public int anInt2474;
public Object[] anObjectArray2475;
public boolean rendertype;
public int anInt2477;
public int[] anIntArray2478;
public int scrollMaxV;
public int area3dX;
public int anInt2481;
public int rotatey;
public Object[] anObjectArray2483;
public int anInt2484;


	private static InterfaceDefinitions [][] InterfaceDefs;
	

	public static InterfaceDefinitions[] forID(int interfaceID) {
		if (Cache.getAmountOfInterfaces() <= interfaceID)
			interfaceID = Cache.getAmountOfInterfaces()-1;
		if (InterfaceDefs == null)
			InterfaceDefs = new InterfaceDefinitions[Cache.getAmountOfInterfaces()][];
		
		if (InterfaceDefs[interfaceID] != null)
			return InterfaceDefs[interfaceID];
		int NumberOfChilds = CacheManager.getRealContainerChildCount(CacheConstants.INTERFACEDEF_IDX_ID, interfaceID);
		InterfaceDefs[interfaceID] = new InterfaceDefinitions[NumberOfChilds];
			
		for (int ChildId = 0; ChildId < NumberOfChilds; ChildId++) {
		byte[] is = new byte[0];
		try {
			is = (CacheManager.getData(CacheConstants.INTERFACEDEF_IDX_ID, interfaceID, ChildId));
		} catch (Exception e) {
			System.out.println("Could not grab Interface "+interfaceID);
		}
		
		InterfaceDefinitions interfaceChild = new InterfaceDefinitions();
		interfaceChild.id = interfaceID;
		interfaceChild.childid = ChildId;
		interfaceChild.idDWORD = ChildId + (interfaceID << 1597590704);
		if (is != null) {
			try {
				if (is[0] != -1)
					interfaceChild.readOldFormat(new RSInputStream(new ByteArrayInputStream(is)));
				else
					interfaceChild.readNewFormat(new RSInputStream(new ByteArrayInputStream(is)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		InterfaceDefs[interfaceID][ChildId] = interfaceChild;
		}
		return InterfaceDefs[interfaceID];
	}
	
	public static InterfaceDefinitions forID(int interfaceID, int ChildID) {
		if (Cache.getAmountOfInterfaces() <= interfaceID)
			interfaceID = Cache.getAmountOfInterfaces()-1;
		if (InterfaceDefs == null)
			InterfaceDefs = new InterfaceDefinitions[Cache.getAmountOfInterfaces()][];
		
		if (InterfaceDefs[interfaceID] != null) {
			if (InterfaceDefs[interfaceID].length <= ChildID)
				return InterfaceDefs[interfaceID][InterfaceDefs[interfaceID].length-1];
			else
				return InterfaceDefs[interfaceID][ChildID];
		}
		int NumberOfChilds = CacheManager.getRealContainerChildCount(CacheConstants.INTERFACEDEF_IDX_ID, interfaceID);
		InterfaceDefs[interfaceID] = new InterfaceDefinitions[NumberOfChilds];
			
		for (int ChildId = 0; ChildId < NumberOfChilds; ChildId++) {
		byte[] is = new byte[0];
		try {
			is = (CacheManager.getData(CacheConstants.INTERFACEDEF_IDX_ID, interfaceID, ChildId));
		} catch (Exception e) {
			System.out.println("Could not grab Interface "+interfaceID);
		}
		
		InterfaceDefinitions interfaceChild = new InterfaceDefinitions();
		interfaceChild.id = interfaceID;
		interfaceChild.childid = ChildId;
		interfaceChild.idDWORD = ChildId + (interfaceID << 1597590704);
		if (is != null) {
			try {
				if (is[0] != -1)
					interfaceChild.readOldFormat(new RSInputStream(new ByteArrayInputStream(is)));
				else
					interfaceChild.readNewFormat(new RSInputStream(new ByteArrayInputStream(is)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		InterfaceDefs[interfaceID][ChildId] = interfaceChild;
		}
		if (InterfaceDefs[interfaceID].length <= ChildID)
			return InterfaceDefs[interfaceID][InterfaceDefs[interfaceID].length-1];
		else
			return InterfaceDefs[interfaceID][ChildID];
	}
	
    public void readNewFormat(RSInputStream stream) throws IOException {
        stream.skip(1);
    	anInt2392++;
    	scripted = true;
    	type = stream.readUnsignedByte();
    	if ((type & 0x80 ^ 0xffffffff) != -1) {
    	    type &= 0x7f;
    	    aString2473 = stream.readCString();
    	}
    	anInt2441 = stream.readUnsignedShort();
    	anInt2445 = stream.readShort();
    	anInt2432 = stream.readShort();
    	anInt2440 = stream.readUnsignedShort();
    	anInt2322 = stream.readUnsignedShort();
    	widthOffset2 = stream.readByte();
    	heightOffset2 = stream.readByte();
    	aByte2469 = stream.readByte();
    	aByte2311 = stream.readByte();
    	parentId = stream.readUnsignedShort();
    	if (parentId == 65535)
    		parentId = -1;
    	else
    	    parentId = (idDWORD & ~0xffff) + parentId;
    	isHidden = stream.readUnsignedByte() == 1;
    	if (type == 0) {
    	    scrollMaxH = stream.readUnsignedShort();
    	    scrollMaxV = stream.readUnsignedShort();
    	    aBoolean2429 = stream.readUnsignedByte() == 1;//Got to do with script execution,is only used when mouse is on this interface

    	}
    	if (type == 5) {
    	    disabledImage = stream.readInt();
    	    anInt2381 = stream.readUnsignedShort();//Something that got to do with behavior, not used at drawing
    	    int i = stream.readUnsignedByte();
    	    aBoolean2434 = (i & 0x1 ^ 0xffffffff) != -1;//Something that got to do with behavior, not used at drawing
    	    loadmethod = (0x2 & i ^ 0xffffffff) != -1;
    	    shadeColor = stream.readUnsignedByte();
    	    rotation = stream.readUnsignedByte();
    	    mask = stream.readInt();
    	    verticalFlip = stream.readUnsignedByte() == 1;
    	    horizontalFlip = stream.readUnsignedByte() == 1;
    	    disabledColor = stream.readInt();//new integer
    	}
    	if (type == 6) {
    	    mediaTypeDisabled = 1;
    	    mediaIDDisabled = stream.readUnsignedShort();
    	    if (mediaIDDisabled == 65535)
    		mediaIDDisabled = -1;
    	    area3dX = stream.readShort();
    	    area3dY = stream.readShort();
    	    rotatex = stream.readUnsignedShort();
    	    rotatey = stream.readUnsignedShort();
    	    rotatez = stream.readUnsignedShort();
    	    zoom = stream.readUnsignedShort();
    	    disabledAnim = stream.readUnsignedShort();
    	    if (disabledAnim == 65535)
    		disabledAnim = -1;
    	    rendertype = stream.readUnsignedByte() == 1;
    	    aShort2383 = (short) stream.readUnsignedShort();
    	    aShort2420 = (short) stream.readUnsignedShort();
    	    aBoolean2368 = stream.readUnsignedByte() == 1;
    	    if (widthOffset2 != 0)
    		width3dArea = stream.readUnsignedShort();
    	    if (heightOffset2 != 0)
    		height3dArea = stream.readUnsignedShort();
    	}
    	if (type == 4) {
    	    font = stream.readUnsignedShort();
    	    if (font == 65535)
    		font = -1;
    	    disabledText = stream.readCString();
    	    //System.out.println("disabled Text: "+disabledText);
    	    anInt2364 = stream.readUnsignedByte();
    	    textalignment = stream.readUnsignedByte();
    	    anInt2297 = stream.readUnsignedByte();
    	    shaded = stream.readUnsignedByte() == 1;
    	    disabledColor = stream.readInt();
    	}
    	if (type == 3) {
    	    disabledColor = stream.readInt();
    	    filled = stream.readUnsignedByte() == 1;
    	    shadeColor = stream.readUnsignedByte();
    	}
    	if (type == 9) {
    	    anInt2471 = stream.readUnsignedByte();
    	    disabledColor = stream.readInt();
    	    positionType = stream.readUnsignedByte() == 1;
    	}
    	int optionMask = stream.read24BitInt();
    	int i_28_ = stream.readUnsignedByte();
    	if (i_28_ != 0) {
    	    anIntArray2449 = new int[10];
    	    aByteArray2417 = new byte[10];
    	    aByteArray2317 = new byte[10];
    	    for (/**/; i_28_ != 0;
    		 i_28_ = stream.readUnsignedByte()) {
    		int i_29_ = -1 + (i_28_ >> 360744868);
    		i_28_ = i_28_ << -456693784 | stream.readUnsignedByte();
    		i_28_ &= 0xfff;
    		if ((i_28_ ^ 0xffffffff) != -4096)
    		    anIntArray2449[i_29_] = i_28_;
    		else
    		    anIntArray2449[i_29_] = -1;
    		aByteArray2317[i_29_] = stream.readByte();
    		if ((aByteArray2317[i_29_] ^ 0xffffffff) != -1)
    		    aBoolean2401 = true;
    		aByteArray2417[i_29_] = stream.readByte();
    	    }
    	}
    	unknownString = stream.readCString();//Probaly name, its always ""
    	int menuActionCount = stream.readUnsignedByte();
    	int i_31_ = menuActionCount & 0xf;
    	if ((i_31_ ^ 0xffffffff) < -1) {
    	    aStringArray2385 = new String[i_31_];
    	    for (int actionIDX = 0; i_31_ > actionIDX; actionIDX++) {
    		aStringArray2385[actionIDX] = stream.readCString();
    	    }
    	    
    	}
    	int i_33_ = menuActionCount >> -686838332;
    	if ((i_33_ ^ 0xffffffff) < -1) {
    	    int i_34_ = stream.readUnsignedByte();
    	    anIntArray2315 = new int[1 + i_34_];
    	    for (int i_35_ = 0; i_35_ < anIntArray2315.length; i_35_++)
    		anIntArray2315[i_35_] = -1;
    	    anIntArray2315[i_34_] = stream.readUnsignedShort();
    	}
    	if ((i_33_ ^ 0xffffffff) < -2) {
    	    int i_36_ = stream.readUnsignedByte();
    	    anIntArray2315[i_36_] = stream.readUnsignedShort();
    	}
    	aString2330 = stream.readCString();
    	if (aString2330.equals(""))
    	    aString2330 = null;
    	anInt2335 = stream.readUnsignedByte();
    	anInt2319 = stream.readUnsignedByte();
    	aBoolean2436 = stream.readUnsignedByte() == 1;
    	selectedActionName = stream.readCString();
    	int i_37_ = -1;
    	if ((method2412(optionMask) ^ 0xffffffff) != -1) {
    	    i_37_ = stream.readUnsignedShort();
    	    if ((i_37_ ^ 0xffffffff) == -65536)
    		i_37_ = -1;
    	    anInt2303 = stream.readUnsignedShort();
    	    if (anInt2303 == 65535)
    		anInt2303 = -1;
    	    anInt2374 = stream.readUnsignedShort();
    	    if (anInt2374 == 65535)
    		anInt2374 = -1;
    	}
    	//aClass131_Sub37_2398 = new Class131_Sub37(optionMask, i_37_); //TODO forgot first one ops
    	anObjectArray2426 = readObjectArray(stream,  65535 ^ ~0xffff); //selectedActionName
    	anObjectArray2462 = readObjectArray(stream, -1);
    	mouseEnterScript = readObjectArray(stream, -1);
    	mouseLeftScript = readObjectArray(stream,  65535 ^ ~0xffff);
    	anObjectArray2408 = readObjectArray(stream,  65535 ^ ~0xffff);
    	scriptArguments = readObjectArray(stream, -1);
    	configResetScript = readObjectArray(stream, -1);
    	inventoryChangedScript = readObjectArray(stream, -1);
    	skillChangedScript = readObjectArray(stream, -1);
    	activeProcessScript = readObjectArray(stream, -1);
    	anObjectArray2446 = readObjectArray(stream, -1);
    	mouseMovedScript = readObjectArray(stream, -1);
    	mouseClickedScript = readObjectArray(stream, -1);
    	mouseDraggedScript = readObjectArray(stream, -1);
    	mouseReleasedScript = readObjectArray(stream, -1);
    	mouseDraggedOverScript = readObjectArray(stream, -1);
    	leftClickParentARGS = readObjectArray(stream, -1);
    	leftClickArgs = readObjectArray(stream, -1);
    	anObjectArray2302 = readObjectArray(stream, -1);
    	anObjectArray2296 = readObjectArray(stream, -1);
    	anIntArray2452 = readIntegerArray(stream);
    	anIntArray2472 = readIntegerArray(stream);
    	anIntArray2360 = readIntegerArray(stream);
    	anIntArray2388 = readIntegerArray(stream);
    	anIntArray2299 = readIntegerArray(stream);
        }
	
    public int[] readIntegerArray(RSInputStream arg0) throws IOException {
    	    int i = arg0.readUnsignedByte();
    	    if (i == 0)
    		return null;
    	    int[] is = new int[i];
    	    for (int i_39_ = 0; i > i_39_; i_39_++)
    		is[i_39_] = arg0.readInt();
    	    return is;
        }
    
    public Object[] readObjectArray(RSInputStream arg0, int arg1) throws IOException {
    	    int i = arg0.readUnsignedByte();
    	    if (i == arg1)
    		return null;
    	    Object[] objects = new Object[i];
    	    for (int i_40_ = 0; (i ^ 0xffffffff) < (i_40_ ^ 0xffffffff);
    		 i_40_++) {
    		int i_41_ = arg0.readUnsignedByte();
    		if ((i_41_ ^ 0xffffffff) == -1)
    		    objects[i_40_] = new Integer(arg0.readInt());
    		else if (i_41_ == 1) {
    		    objects[i_40_] = arg0.readCString();
    		}
    	    }
    	    aBoolean2353 = true;
    	    return objects;
        }
    
    
    public static int method2412(int arg0) {
    	    return 0x7f & arg0 >> -809958741;
        }
    
    public void readOldFormat(RSInputStream in) throws IOException {
    	    scripted = false;
    	    anInt2320++;
    	    type = in.readUnsignedByte();
    	    anInt2324 = in.readUnsignedByte();
    	    anInt2441 = in.readUnsignedShort();
    	    anInt2445 = in.readShort();
    	    anInt2432 = in.readShort();
    	    anInt2440 = in.readUnsignedShort();
    	    anInt2322 = in.readUnsignedShort();
    	    heightOffset2 = (byte) 0;
    	    widthOffset2 = (byte) 0;
    	    aByte2311 = (byte) 0;
    	    aByte2469 = (byte) 0;
    	    shadeColor = in.readUnsignedByte();
    	    parentId = in.readUnsignedShort();
    	    if ((parentId ^ 0xffffffff) == -65536)
    		parentId = -1;
    	    else
    		parentId = parentId + (idDWORD & ~0xffff);
    	    anInt2448 = in.readUnsignedShort();
    	    if (true) {
    		if ((anInt2448 ^ 0xffffffff) == -65536)
    		    anInt2448 = -1;
    		int i = in.readUnsignedByte();
    		if ((i ^ 0xffffffff) < -1) {
    		    anIntArray2407 = new int[i];
    		    anIntArray2384 = new int[i];
    		    for (int i_0_ = 0; i > i_0_; i_0_++) {
    			anIntArray2384[i_0_] = in.readUnsignedByte();
    			anIntArray2407[i_0_] = in.readUnsignedShort();
    		    }
    		}
    		int i_1_ = in.readUnsignedByte();
    		if ((i_1_ ^ 0xffffffff) < -1) {
    		    anIntArrayArray2327 = new int[i_1_][];
    		    for (int i_2_ = 0;
    			 (i_1_ ^ 0xffffffff) < (i_2_ ^ 0xffffffff); i_2_++) {
    			int i_3_ = in.readUnsignedShort();
    			anIntArrayArray2327[i_2_] = new int[i_3_];
    			for (int i_4_ = 0;
    			     (i_3_ ^ 0xffffffff) < (i_4_ ^ 0xffffffff);
    			     i_4_++) {
    			    anIntArrayArray2327[i_2_][i_4_]
    				= in.readUnsignedShort();
    			    if ((anIntArrayArray2327[i_2_][i_4_] ^ 0xffffffff)
    				== -65536)
    				anIntArrayArray2327[i_2_][i_4_] = -1;
    			}
    		    }
    		}
    		if ((type ^ 0xffffffff) == -1) {
    		    scrollMaxV = in.readUnsignedShort();
    		    isHidden = in.readUnsignedByte() == 1;
    		}
    		if (type == 1) {
    		    in.readUnsignedShort();
    		    in.readUnsignedByte();
    		}
    		int i_5_ = 0;
    		if ((type ^ 0xffffffff) == -3) {
    		    anIntArray2400 = new int[anInt2322 * anInt2440];
    		    heightOffset2 = (byte) 3;
    		    anIntArray2418 = new int[anInt2322 * anInt2440];
    		    widthOffset2 = (byte) 3;
    		    int i_6_ = in.readUnsignedByte();
    		    if (i_6_ == 1)
    			i_5_ |= 0x10000000;
    		    int i_7_ = in.readUnsignedByte();
    		    if (i_7_ == 1)
    			i_5_ |= 0x40000000;
    		    int i_8_ = in.readUnsignedByte();
    		    in.readUnsignedByte();
    		    if ((i_8_ ^ 0xffffffff) == -2)
    			i_5_ |= ~0x7fffffff;
    		    anInt2332 = in.readUnsignedByte();
    		    anInt2414 = in.readUnsignedByte();
    		    anIntArray2337 = new int[20];
    		    anIntArray2323 = new int[20];
    		    anIntArray2431 = new int[20];
    		    for (int i_9_ = 0; i_9_ < 20; i_9_++) {
    			int i_10_ = in.readUnsignedByte();
    			if ((i_10_ ^ 0xffffffff) != -2)
    			    anIntArray2431[i_9_] = -1;
    			else {
    			    anIntArray2323[i_9_] = in.readShort();
    			    anIntArray2337[i_9_] = in.readShort();
    			    anIntArray2431[i_9_] = in.readInt();
    			}
    		    }
    		    aStringArray2363 = new String[5];
    		    for (int i_11_ = 0; i_11_ < 5; i_11_++) {
    			String string = in.readCString();
    			if ((string.length() ^ 0xffffffff) < -1) {
    			    aStringArray2363[i_11_] = string;
    			    i_5_ |= 1 << 23 + i_11_;
    			}
    		    }
    		}
    		if ((type ^ 0xffffffff) == -4)
    		    filled = (in.readUnsignedByte() ^ 0xffffffff) == -2;
    		if ((type ^ 0xffffffff) == -5 || type == 1) {
    		    textalignment = in.readUnsignedByte();
    		    anInt2297 = in.readUnsignedByte();
    		    anInt2364 = in.readUnsignedByte();
    		    font = in.readUnsignedShort();
    		    if ((font ^ 0xffffffff) == -65536)
    			font = -1;
    		    shaded = in.readUnsignedByte() == 1;
    		}
    		if ((type ^ 0xffffffff) == -5) {
    		    disabledText = in.readCString();
    		    aString2334 = in.readCString();
    		}
    		if (type == 1 || (type ^ 0xffffffff) == -4
    		    || type == 4)
    		    disabledColor = in.readInt();
    		if (type == 3 || type == 4) {
    		    anInt2424 = in.readInt();
    		    anInt2451 = in.readInt();
    		    anInt2477 = in.readInt();
    		}
    		if ((type ^ 0xffffffff) == -6) {
    		    disabledImage = in.readInt();
    		    anInt2349 = in.readInt();
    		}
    		if ((type ^ 0xffffffff) == -7) {
    		    mediaTypeDisabled = 1;
    		    mediaIDDisabled = in.readUnsignedShort();
    		    anInt2301 = 1;
    		    if (mediaIDDisabled == 65535)
    			mediaIDDisabled = -1;
    		    anInt2386 = in.readUnsignedShort();
    		    if ((anInt2386 ^ 0xffffffff) == -65536)
    			anInt2386 = -1;
    		    disabledAnim = in.readUnsignedShort();
    		    if (disabledAnim == 65535)
    			disabledAnim = -1;
    		    anInt2298 = in.readUnsignedShort();
    		    if (anInt2298 == 65535)
    			anInt2298 = -1;
    		    zoom = in.readUnsignedShort();
    		    rotatex = in.readUnsignedShort();
    		    rotatey = in.readUnsignedShort();
    		}
    		if ((type ^ 0xffffffff) == -8) {
    		    heightOffset2 = (byte) 3;
    		    anIntArray2418 = new int[anInt2440 * anInt2322];
    		    widthOffset2 = (byte) 3;
    		    anIntArray2400 = new int[anInt2440 * anInt2322];
    		    textalignment = in.readUnsignedByte();
    		    font = in.readUnsignedShort();
    		    if (font == 65535)
    			font = -1;
    		    shaded = in.readUnsignedByte() == 1;
    		    disabledColor = in.readInt();
    		    anInt2332 = in.readShort();
    		    anInt2414 = in.readShort();
    		    int i_12_ = in.readUnsignedByte();
    		    if ((i_12_ ^ 0xffffffff) == -2)
    			i_5_ |= 0x40000000;
    		    aStringArray2363 = new String[5];
    		    for (int i_13_ = 0; i_13_ < 5; i_13_++) {
    			String string = in.readCString();
    			if (string.length() > 0) {
    			    aStringArray2363[i_13_] = string;
    			    i_5_ |= 1 << i_13_ + 23;
    			}
    		    }
    		}
    		if ((type ^ 0xffffffff) == -9)
    		    disabledText = in.readCString();
    		if (anInt2324 == 2 || (type ^ 0xffffffff) == -3) {
    		    selectedActionName = in.readCString();
    		    aString2373 = in.readCString();
    		    int i_14_ = 0x3f & in.readUnsignedShort();
    		    i_5_ |= i_14_ << -116905845;
    		}
    		if ((anInt2324 ^ 0xffffffff) == -2
    		    || (anInt2324 ^ 0xffffffff) == -5 || anInt2324 == 5
    		    || anInt2324 == 6) {
    		    aString2329 = in.readCString();
    		    if ((aString2329.length() ^ 0xffffffff) == -1) {
    			if ((anInt2324 ^ 0xffffffff) == -2)
    			    aString2329 = "Ok";
    			if ((anInt2324 ^ 0xffffffff) == -5)
    			    aString2329 = "Select";
    			if ((anInt2324 ^ 0xffffffff) == -6)
    			    aString2329 = "Select";
    			if ((anInt2324 ^ 0xffffffff) == -7)
    			    aString2329 = "Continue";
    		    }
    		}
    		if (anInt2324 == 1 || anInt2324 == 4
    		    || (anInt2324 ^ 0xffffffff) == -6)
    		    i_5_ |= 0x400000;
    		if ((anInt2324 ^ 0xffffffff) == -7)
    		    i_5_ |= 0x1;
    		//aClass131_Sub37_2398 = new Class131_Sub37(i_5_, -1); //TODO
    	    }
        }

}
