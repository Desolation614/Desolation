package dragonkk.rs2rsps.rscache;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

import dragonkk.rs2rsps.util.CacheConstants;

public class ObjectDefinitions {

    private static HashMap<Integer, ObjectDefinitions> objDefs = new HashMap<Integer, ObjectDefinitions>();

    public static int anInt1959;
    public boolean aBoolean1960;
    public short aShort1961 = -1;
    public byte[] aByteArray1962;
    public byte aByte1963 = 0;
    public int anInt1964;
    public int anInt1965;
    public boolean aBoolean1966;
    public int[] anIntArray1967;
    public boolean aBoolean1968;
    public static int anInt1970;
    public boolean aBoolean1971;
    public int anInt1972;
    public int anInt1973;
    public static int anInt1974;
    public int anInt1975;
    public int anInt1976;
    public int anInt1977;
    public boolean aBoolean1978;
    public int anInt1979;
    public int anInt1980;
    public short[] aShortArray1981;
    public int anInt1982;
    public int anInt1983;
    public static int anInt1984;
    public short[] aShortArray1985;
    public int anInt1986;
    public int sizeX;
    public int sizeY;
    public int anInt1989;
    public int anInt1990;
    public static int anInt1991;
    public static String[] aRSStringArray1992;
    public static int anInt1993;
    public int[] anIntArray1994;
    public int[] anIntArray1995;
    public static int[] anIntArray1996;
    public boolean aBoolean1997;
    public int anInt1998;
    public int anInt1999;
    public int anInt2000;
    public static int anInt2001;
    public int anInt2002;
    public int anInt2003;
    public boolean aBoolean2004;
    public boolean aBoolean2005;
    public String[] actions;
    public static int anInt2007 = 0;
    public boolean aBoolean2008;
    public static int anInt2009;
    public int[] anIntArray2010;
    public static int anInt2011 = 0;
    public static int anInt2012;
    public int anInt2013;
    public static int anInt2014;
    public static int anInt2015;
    public boolean aBoolean2017;
    public boolean aBoolean2018;
    public String name;
    public int anInt2022;
    public short[] aShortArray2023;
    public static int anInt2024;
    public int anInt2025;
    public short[] aShortArray2026;
    public boolean aBoolean2027;
    public static int anInt2028;
    public static int anInt2029;
    public boolean aBoolean2030;
    public int id;

    private void method1706(int i) {
        anInt1974++;
        if ((~anInt1983) == 0) {
            anInt1983 = 0;
            if (anIntArray1967 != null && (anIntArray2010 == null || anIntArray2010[0] == 10))
                anInt1983 = 1;
            for (int i_16_ = 0; (~i_16_) > -6; i_16_++) {
                if (actions[i_16_] != null) {
                    anInt1983 = 1;
                    break;
                }
            }
        }
        if ((~anInt1972) == 0)
            anInt1972 = anInt2022 != 0 ? 1 : 0;
        if (i != -129)
            sizeY = -45;
    }

    private void readValues(int i, RSInputStream in, int opcode) throws IOException {
        if (opcode == 1) {
            int i_25_ = in.readByte();
            if ((~i_25_) < -1) {
                anIntArray2010 = new int[i_25_];
                anIntArray1967 = new int[i_25_];
                for (int i_26_ = 0; (~i_25_) < (~i_26_); i_26_++) {
                    anIntArray1967[i_26_] = in.readShort();
                    anIntArray2010[i_26_] = in.readByte();
                }

            }
        } else if ((~opcode) == -3)
            name = in.readCString();
        else if ((~opcode) != -6) {
            if (opcode != 14) {
                if ((~opcode) == -16)
                    sizeX = in.readByte();
                else if ((~opcode) != -18) {
                    if (opcode == 18)
                        aBoolean2030 = false;
                    else if (opcode == 19)
                        anInt1983 = in.readByte();
                    else if (opcode != 21) {
                        if ((~opcode) != -23) {
                            if ((~opcode) != -24) {
                                if ((~opcode) != -25) {
                                    if ((~opcode) != -28) {
                                        if (opcode != 28) {
                                            if ((~opcode) != -30) {
                                                if (opcode != 39) {
                                                    if (opcode >= 30 && opcode < 35) {
                                                        actions[-30 + opcode] = (in.readCString());
                                                        if (actions[-30 + opcode].equals("null"))
                                                            actions[opcode - 30] = null;
                                                    } else if ((~opcode) != -41) {
                                                        if ((~opcode) != -42) {
                                                            if ((~opcode) != -43) {
                                                                if ((~opcode) != -61) {
                                                                    if (opcode != 62) {
                                                                        if (opcode == 64)
                                                                            aBoolean2027 = false;
                                                                        else if ((~opcode) == -66)
                                                                            anInt1977 = in.readShort();
                                                                        else if ((~opcode) != -67) {
                                                                            if ((~opcode) == -68)
                                                                                anInt2003 = in.readShort();
                                                                            else if (opcode == 68)
                                                                                anInt1973 = in.readShort();
                                                                            else if ((~opcode) != -70) {
                                                                                if (opcode != 70) {
                                                                                    if (opcode != 71) {
                                                                                        if ((~opcode) == -73)
                                                                                            anInt1986 = in.readSShort();
                                                                                        else if (opcode != 73) {
                                                                                            if ((~opcode) != -75) {
                                                                                                if ((~opcode) != -76) {
                                                                                                    if ((~opcode) == -78 || (~opcode) == -93) {
                                                                                                        anInt1976 = in.readShort();
                                                                                                        if ((~anInt1976) == -65536)
                                                                                                            anInt1976 = -1;
                                                                                                        int i_27_ = -1;
                                                                                                        anInt1998 = in.readShort();
                                                                                                        if (anInt1998 == 65535)
                                                                                                            anInt1998 = -1;
                                                                                                        if ((~opcode) == -93) {
                                                                                                            i_27_ = in.readShort();
                                                                                                            if (i_27_ == 65535)
                                                                                                                i_27_ = -1;
                                                                                                        }
                                                                                                        int i_28_ = in.readByte();
                                                                                                        anIntArray1994 = new int[i_28_ - -2];
                                                                                                        for (int i_29_ = 0; i_28_ >= i_29_; i_29_++) {
                                                                                                            anIntArray1994[i_29_] = in.readShort();
                                                                                                            if ((~anIntArray1994[i_29_]) == -65536)
                                                                                                                anIntArray1994[i_29_] = -1;
                                                                                                        }
                                                                                                        anIntArray1994[1 + i_28_] = i_27_;
                                                                                                    } else if (opcode != 78) {
                                                                                                        if (opcode == 79) {
                                                                                                            anInt1964 = in.readShort();
                                                                                                            anInt2000 = in.readShort();
                                                                                                            anInt1975 = in.readByte();
                                                                                                            int i_30_ = in.readByte();
                                                                                                            anIntArray1995 = new int[i_30_];
                                                                                                            for (int i_31_ = 0; i_30_ > i_31_; i_31_++)
                                                                                                                anIntArray1995[i_31_] = in.readShort();
                                                                                                        } else if (opcode != 81) {
                                                                                                            if ((~opcode) != -83) {
                                                                                                                if ((~opcode) == -89)
                                                                                                                    aBoolean2018 = false;
                                                                                                                else if (opcode != 89) {
                                                                                                                    if ((~opcode) != -91) {
                                                                                                                        if (opcode != 91) {
                                                                                                                            if ((~opcode) == -94) {
                                                                                                                                aByte1963 = (byte) 3;
                                                                                                                                aShort1961 = in.readShort();
                                                                                                                            } else if (opcode != 94) {
                                                                                                                                if ((~opcode) == -96)
                                                                                                                                    aByte1963 = (byte) 5;
                                                                                                                                else if (opcode != 96) {
                                                                                                                                    if (opcode == 97)
                                                                                                                                        aBoolean2017 = true;
                                                                                                                                    else if ((~opcode) == -250) {
                                                                                                                                        int i_32_ = in.readByte();
                                                                                                                                        for (int i_34_ = 0; i_32_ > i_34_; i_34_++) {
                                                                                                                                            boolean bool = (~in.readByte()) == -2;
                                                                                                                                            in.read24BitInt();
                                                                                                                                            if (bool)
                                                                                                                                                in.readCString();
                                                                                                                                            else
                                                                                                                                                in.readInt();
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                } else
                                                                                                                                    aBoolean2005 = true;
                                                                                                                            } else
                                                                                                                                aByte1963 = (byte) 4;
                                                                                                                        } else
                                                                                                                            aBoolean1978 = true;
                                                                                                                    } else
                                                                                                                        aBoolean1960 = true;
                                                                                                                } else
                                                                                                                    aBoolean1997 = false;
                                                                                                            }
                                                                                                        } else {
                                                                                                            aByte1963 = (byte) 2;
                                                                                                            aShort1961 = (short) (256 * in.readByte());
                                                                                                        }
                                                                                                    } else {
                                                                                                        anInt1982 = in.readShort();
                                                                                                        anInt1975 = in.readByte();
                                                                                                    }
                                                                                                } else
                                                                                                    anInt1972 = in.readByte();
                                                                                            } else
                                                                                                aBoolean1968 = true;
                                                                                        } else
                                                                                            aBoolean2004 = true;
                                                                                    } else
                                                                                        anInt1989 = in.readSShort();
                                                                                } else
                                                                                    anInt1965 = in.readSShort();
                                                                            } else
                                                                                anInt1990 = in.readByte();
                                                                        } else
                                                                            anInt1999 = in.readShort();
                                                                    } else
                                                                        aBoolean1971 = true;
                                                                } else
                                                                    anInt1980 = (in.readShort());
                                                            } else {
                                                                int i_36_ = (in.readByte());
                                                                aByteArray1962 = (new byte[i_36_]);
                                                                for (int i_37_ = 0; (i_36_ > i_37_); i_37_++)
                                                                    aByteArray1962[i_37_] = (in.readByte());
                                                            }
                                                        } else {
                                                            int i_38_ = (in.readByte());
                                                            aShortArray1985 = (new short[i_38_]);
                                                            aShortArray2023 = (new short[i_38_]);
                                                            for (int i_39_ = 0; ((~i_39_) > (~i_38_)); i_39_++) {
                                                                aShortArray2023[i_39_] = in.readShort();
                                                                aShortArray1985[i_39_] = in.readShort();
                                                            }
                                                        }
                                                    } else {
                                                        int i_40_ = (in.readByte());
                                                        aShortArray1981 = new short[i_40_];
                                                        aShortArray2026 = new short[i_40_];
                                                        for (int i_41_ = 0; i_40_ > i_41_; i_41_++) {
                                                            aShortArray2026
                                                                    [i_41_] = in.readShort();
                                                            aShortArray1981
                                                                    [i_41_] = in.readShort();
                                                        }
                                                    }
                                                } else
                                                    anInt1979 = 5 * (in.readByte());
                                            } else
                                                anInt2013 = in.readByte();
                                        } else
                                            anInt2025 = (in.readByte());
                                    } else
                                        anInt2022 = 1;
                                } else {
                                    anInt2002 = in.readShort();
                                    if ((~anInt2002) == -65536)
                                        anInt2002 = -1;
                                }
                            } else
                                aBoolean2008 = true;
                        } else
                            aBoolean1966 = true;
                    } else
                        aByte1963 = (byte) 1;
                } else {
                    anInt2022 = 0;
                    aBoolean2030 = false;
                }
            } else
                sizeY = in.readByte();
        } else {
            int i_42_ = in.readByte();
            if ((~i_42_) < -1) {
                anIntArray1967 = new int[i_42_];
                anIntArray2010 = null;
                for (int i_43_ = 0; i_42_ > i_43_; i_43_++)
                    anIntArray1967[i_43_] = in.readShort();

            }
        }
        anInt2015++;
    }

    private void readValueLoop(RSInputStream stream) throws IOException {
        for (; ;) {
            int opcode = stream.readByte();
            if (opcode == 0)
                break;
            readValues(-2129513334, stream, opcode);
        }
        anInt2009++;
    }


    private ObjectDefinitions() {
        aBoolean1960 = false;
        anInt1977 = 128;
        anInt1979 = 0;
        anInt1980 = -1;
        aBoolean1968 = false;
        anInt1965 = 0;
        anInt1972 = -1;
        aBoolean1971 = false;
        anInt1986 = 0;
        anInt1999 = 128;
        anInt1989 = 0;
        anInt1976 = -1;
        anInt2000 = 0;
        anInt1982 = -1;
        aBoolean1997 = true;
        aBoolean2004 = false;
        anInt2013 = 0;
        sizeY = 1;
        aBoolean2008 = false;
        anInt1975 = 0;
        anInt1964 = 0;
        aBoolean1966 = false;
        anInt1998 = -1;
        sizeX = 1;
        anInt1973 = -1;
        aBoolean1978 = false;
        anInt1983 = -1;
        actions = new String[5];
        anInt2002 = -1;
        name = "";
        aBoolean2005 = false;
        anInt2003 = 128;
        anInt2022 = 2;
        aBoolean2017 = false;
        aBoolean2018 = true;
        anInt2025 = 16;
        aBoolean2027 = true;
        anInt1990 = 0;
        aBoolean2030 = true;
    }

    private static int GetContainerId(int i_0_) {
        return i_0_ >>> 8;
    }

    public static ObjectDefinitions forID(int objectID) {
        ObjectDefinitions objectDef = objDefs.get(objectID);
        if (objectDef != null)
            return objectDef;
        byte[] is = new byte[0];
        try {
            is = (CacheManager.getData(CacheConstants.OBJECTDEF_IDX_ID, GetContainerId(objectID), 0xff & objectID));
        } catch (Exception e) {
            System.out.println("Could not grab object " + objectID);
        }
        objectDef = new ObjectDefinitions();
        objectDef.id = objectID;
        if (is != null) {
            try {
                objectDef.readValueLoop(new RSInputStream(new ByteArrayInputStream(is)));
            } catch (IOException e) {
                System.out.println("Could not load object " + objectID);
            }
        }
        objectDef.method1706(-129);
        if (objectDef.aBoolean1978)
            objectDef.actions = null;
        if (objectDef.aBoolean1968) {
            objectDef.anInt2022 = 0;
            objectDef.aBoolean2030 = false;
        }
        objDefs.put(objectID, objectDef);
        return objectDef;
    }
    
    public boolean hasActions() {
    	if(actions == null)
    		return false;
    	for(int i = 0; i < actions.length; i++) {
    		if(actions[i] != null && !actions[i].equals("null") && !actions[i].equals(""))
    			return true;
    	}
		return false;
    }
    
    public int getNumberOfActions() {
    	if(actions == null)
    		return 0;
    	int amt = 0;
    	for(int i = 0; i < actions.length; i++) {
    		if(actions[i] != null && !actions[i].equals("null") && !actions[i].equals(""))
    			amt++;
    	}
    	return amt;
    }
}

