package dragonkk.rs2rsps.rscache;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import dragonkk.rs2rsps.util.CacheConstants;

public class NpcDefinitions implements Serializable{

    private static HashMap<Integer, NpcDefinitions> npcDefs = new HashMap<Integer, NpcDefinitions>();

    public byte aByte3439;
    public byte aByte3440;
    public int size = 1;
    public int anInt3442 = -1;
    public int anInt3444;
    public boolean aBoolean3445;
    public int anInt3447;
    public int anInt3448;
    public int anInt3449;
    public int anInt3450;
    public static int anInt3451;
    public static int anInt3453;
    public int anInt3454;
    public boolean aBoolean3456;
    public String[] aStringArray3457;
    public byte aByte3458;
    public int anInt3459;
    public byte aByte3460;
    public int[] anIntArray3461;
    public int anInt3462;
    public int[][] anIntArrayArray3463;
    public short aShort3464;
    public int anInt3465;
    public int anInt3466;
    public byte aByte3467;
    public int anInt3469;
    public int anInt3470;
    public boolean aBoolean3471;
    public short aShort3472;
    public int[][] anIntArrayArray3473;
    public short[] aShortArray3474;
    public int anInt3476;
    public boolean aBoolean3477;
    public int anInt3479;
    public boolean aBoolean3480;
    public byte[] aByteArray3481;
    public int anInt3482;
    public short[] aShortArray3483;
    public int anInt3484;
    public int anInt3485;
    public byte aByte3486;
    public byte aByte3488;
    public int anInt3489;
    public int anInt3490;
    public int anInt3491;
    public boolean aBoolean3492;
    public byte aByte3493;
    public int[] anIntArray3495;
    public int anInt3497;
    public int id;
    public boolean aBoolean3500;
    public int anInt3502;
    public short[] aShortArray3503;
    public String name;
    public byte aByte3505;
    public int[] anIntArray3506;
    public int anInt3507;
    public int[] anIntArray3508;
    public int anInt3509;
    public int anInt3510;
    public short[] aShortArray3511;

    private static int GetContainerId(int i_0_) {
	return i_0_ >>> 134238215;

    }

    public static NpcDefinitions forID(int npcID) {
	NpcDefinitions npcDef = npcDefs.get(npcID);
	if (npcDef != null)
	    return npcDef;
	byte[] is = new byte[0];
	try {
	    is = (CacheManager.getData(CacheConstants.NPCDEF_IDX_ID, GetContainerId(npcID), npcID & 0x7f));
	} catch (Exception e) {
	    System.out.println("Could not grab Npc " + npcID);
	}
	npcDef = new NpcDefinitions();
	npcDef.id = npcID;
	if (is != null) {
	    try {
		npcDef.readValueLoop(new RSInputStream(new ByteArrayInputStream(is)));
	    } catch (IOException e) {
		System.out.println("Could not load Npc " + npcID);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	npcDef.method2703();
	npcDefs.put(npcID, npcDef);
	return npcDef;
    }

    private void readValueLoop(RSInputStream stream) throws Exception {
	try {
	    for (;;) {
		int i = stream.readByte();
		if ((~i) == -1)
		    break;
		readValues(i, stream);
	    }
	} catch (RuntimeException runtimeexception) {

	}
    }

    private void method2703() {
	if ((~aByte3460) == 0) {
	}
	if (anIntArray3495 == null)
	    anIntArray3495 = new int[0];
    }

    private void readValues(int opcode, RSInputStream arg2) throws Exception {
	if ((~opcode) == -2) {
	    int i = arg2.readUnsignedByte();
	    anIntArray3495 = new int[i];
	    for (int i_7_ = 0; (~i_7_) > (~i); i_7_++) {
		anIntArray3495[i_7_] = arg2.readUnsignedShort();
		if ((~anIntArray3495[i_7_]) == -65536)
		    anIntArray3495[i_7_] = -1;
	    }
	} else if (opcode != 2) {
	    if ((~opcode) != -13) {
		if (opcode < 30 || (~opcode) <= -36) {
		    if (opcode != 40) {
			if ((~opcode) != -42) {
			    if ((~opcode) != -43) {
				if (opcode == 60) {
				    int i = arg2.readUnsignedByte();
				    anIntArray3506 = new int[i];
				    for (int i_8_ = 0; ((~i_8_) > (~i)); i_8_++)
					anIntArray3506[i_8_] = arg2.readUnsignedShort();
				} else if (opcode != 93) {
				    if ((~opcode) != -96) {
					if (opcode != 97) {
					    if (opcode != 98) {
						if (opcode != 99) {
						    if ((~opcode) == -101)
							anInt3497 = (arg2.readByte());
						    else if (opcode == 101)
							anInt3476 = ((arg2.readByte()) * 5);
						    else if (opcode == 102)
							anInt3442 = (arg2.readUnsignedShort());
						    else if (opcode == 103)
							anInt3509 = (arg2.readUnsignedShort());
						    else if (opcode != 106 && ((~opcode) != -119)) {
							if ((~opcode) == -108)
							    aBoolean3500 = false;
							else if (opcode != 109) {
							    if ((~opcode) == -112)
								aBoolean3471 = false;
							    else if ((~opcode) != -114) {
								if (opcode != 114) {
								    if (opcode != 115) {
									if ((~opcode) != -120) {
									    if ((~opcode) == -122) {
										anIntArrayArray3473 = new int[anIntArray3495.length][];
										int i = arg2.readUnsignedByte();
										for (int i_9_ = 0; (~i) < (~i_9_); i_9_++) {
										    int i_10_ = arg2.readUnsignedByte();
										    int[] is = anIntArrayArray3473[i_10_] = new int[3];
										    is[0] = arg2.readByte();
										    is[1] = arg2.readByte();
										    is[2] = arg2.readByte();
										}
									    } else if (opcode == 122)
										anInt3450 = arg2.readUnsignedShort();
									    else if ((~opcode) != -124) {
										if (opcode != 125) {
										    if ((~opcode) == -128)
											anInt3454 = arg2.readUnsignedShort();
										    else if (opcode == 128)
											arg2.readUnsignedByte();
										    else if (opcode != 134) {
											if ((~opcode) == -136) {
											    anInt3484 = arg2.readUnsignedByte();
											    anInt3482 = arg2.readUnsignedShort();
											} else if ((~opcode) == -137) {
											    anInt3470 = arg2.readUnsignedByte();
											    anInt3448 = arg2.readUnsignedShort();
											} else if (opcode != 137) {
											    if ((~opcode) != -139) {
												if (opcode != 139) {
												    if (opcode == 140)
													anInt3479 = arg2.readUnsignedByte();
												    else if ((~opcode) != -142) {
													if ((~opcode) == -143)
													    anInt3510 = arg2.readUnsignedShort();
													else if ((~opcode) == -144)
													    aBoolean3456 = true;
													else if (opcode >= 150 && (~opcode) > -156) {
													    aStringArray3457[-150 + opcode] = arg2.readCString();
													} else if (opcode != 155) {
													    if (opcode == 158)
														aByte3460 = (byte) 1;
													    else if (opcode != 159) {
														if ((~opcode) == -161) {
														    int i = arg2.readUnsignedByte();
														    anIntArray3508 = new int[i];
														    for (int i_11_ = 0; (~i_11_) > (~i); i_11_++)
															anIntArray3508[i_11_] = arg2.readUnsignedShort();
														} else if ((~opcode) == -250) {
														    int i = arg2.readUnsignedByte();
														    for (int i_13_ = 0; (~i_13_) > (~i); i_13_++) {
															boolean bool = arg2.readUnsignedByte() == 1;
															arg2.read24BitInt();
															if (!bool)
															    arg2.readInt();
															else
															    arg2.readCString();
														    }
														}
													    } else
														aByte3460 = (byte) 0;
													} else {
													    aByte3440 = arg2.readByte();
													    aByte3505 = arg2.readByte();
													    aByte3486 = arg2.readByte();
													    aByte3493 = arg2.readByte();
													}
												    } else
													aBoolean3480 = true;
												} else
												    anInt3449 = arg2.readUnsignedShort();
											    } else
												anInt3466 = arg2.readUnsignedShort();
											} else
											    anInt3485 = arg2.readUnsignedShort();
										    } else {
											anInt3507 = arg2.readUnsignedShort();
											if (anInt3507 == 65535)
											    anInt3507 = -1;
											anInt3459 = arg2.readUnsignedShort();
											if ((~anInt3459) == -65536)
											    anInt3459 = -1;
											anInt3462 = arg2.readUnsignedShort();
											if ((~anInt3462) == -65536)
											    anInt3462 = -1;
											anInt3447 = arg2.readUnsignedShort();
											if ((~anInt3447) == -65536)
											    anInt3447 = -1;
											anInt3469 = arg2.readUnsignedByte();
										    }
										} else
										    aByte3458 = arg2.readByte();
									    } else
										anInt3489 = arg2.readUnsignedShort();
									} else
									    aByte3488 = arg2.readByte();
								    } else {
									arg2.readUnsignedByte();
									arg2.readUnsignedByte();
								    }
								} else {
								    aByte3439 = arg2.readByte();
								    aByte3467 = arg2.readByte();
								}
							    } else {
								aShort3472 = (short) arg2.readUnsignedShort();
								aShort3464 = (short) arg2.readUnsignedShort();
							    }
							} else
							    aBoolean3477 = false;
						    } else {
							anInt3502 = (arg2.readUnsignedShort());
							if (anInt3502 == 65535)
							    anInt3502 = -1;
							anInt3465 = (arg2.readUnsignedShort());
							if (anInt3465 == 65535)
							    anInt3465 = -1;
							int i = -1;
							if (opcode == 118) {
							    i = (arg2.readUnsignedShort());
							    if (i == 65535)
								i = -1;
							}
							int i_15_ = (arg2.readUnsignedByte());
							anIntArray3461 = (new int[(i_15_ + 2)]);
							for (int i_16_ = 0; (i_16_ <= i_15_); i_16_++) {
							    anIntArray3461[i_16_] = (arg2.readUnsignedShort());
							    if ((anIntArray3461[i_16_]) == 65535)
								anIntArray3461[i_16_] = -1;
							}
							anIntArray3461[i_15_ - -1] = i;
						    }
						} else
						    aBoolean3445 = true;
					    } else
						anInt3490 = (arg2.readUnsignedShort());
					} else
					    anInt3444 = (arg2.readUnsignedShort());
				    } else
					anInt3491 = arg2.readUnsignedShort();
				} else
				    aBoolean3492 = false;
			    } else {
				int i = arg2.readUnsignedByte();
				aByteArray3481 = new byte[i];
				for (int i_17_ = 0; i_17_ < i; i_17_++)
				    aByteArray3481[i_17_] = arg2.readByte();
			    }
			} else {
			    int i = arg2.readUnsignedByte();
			    aShortArray3503 = new short[i];
			    aShortArray3483 = new short[i];
			    for (int i_18_ = 0; i > i_18_; i_18_++) {
				aShortArray3503[i_18_] = (short) arg2.readUnsignedShort();
				aShortArray3483[i_18_] = (short) arg2.readUnsignedShort();
			    }
			}
		    } else {
			int i = arg2.readUnsignedByte();
			aShortArray3511 = new short[i];
			aShortArray3474 = new short[i];
			for (int i_19_ = 0; (~i) < (~i_19_); i_19_++) {
			    aShortArray3474[i_19_] = (short) arg2.readUnsignedShort();
			    aShortArray3511[i_19_] = (short) arg2.readUnsignedShort();
			}
		    }
		} else
		    aStringArray3457[opcode - 30] = arg2.readCString();
	    } else
		size = arg2.readUnsignedByte();
	} else
	    name = arg2.readCString();
    }

    private NpcDefinitions() {
	aByte3439 = (byte) -96;
	anInt3449 = -1;
	anInt3450 = -1;
	anInt3454 = -1;
	aStringArray3457 = new String[5];
	anInt3444 = 128;
	aBoolean3456 = false;
	aByte3460 = (byte) -1;
	aBoolean3445 = false;
	anInt3476 = 0;
	anInt3479 = 255;
	anInt3482 = -1;
	anInt3485 = -1;
	aShort3472 = (short) 0;
	aBoolean3471 = true;
	anInt3484 = -1;
	anInt3470 = -1;
	anInt3448 = -1;
	anInt3490 = 128;
	anInt3465 = -1;
	anInt3491 = -1;
	aBoolean3480 = false;
	anInt3462 = -1;
	anInt3469 = 0;
	aByte3458 = (byte) 4;
	anInt3447 = -1;
	aByte3488 = (byte) 0;
	anInt3497 = 0;
	anInt3459 = -1;
	aByte3493 = (byte) 0;
	aByte3467 = (byte) -16;
	anInt3502 = -1;
	aBoolean3477 = true;
	anInt3466 = -1;
	aBoolean3492 = true;
	name = "null";
	anInt3507 = -1;
	aBoolean3500 = true;
	anInt3510 = -1;
	anInt3509 = 32;
	anInt3489 = -1;
	aShort3464 = (short) 0;
    }

}
