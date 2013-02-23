package dragonkk.rs2rsps.util;

public class Constants {

	public static byte[] idx255_File255 = {};

	public final static boolean ISMEMBERWORLD = true;
	public final static short REVISION = 614;

	public final static byte DISCONNECT = -1;
	public final static byte GET_CONNECTION_ID = 0;
	public final static byte LOGIN_START = 1;
	public final static byte LOGIN_CYPTION = 2;
	public final static byte CHECK_ACC_NAME = 3;
	public final static byte CHECK_ACC_COUNTRY = 4;
	public final static byte MAKE_ACC = 5;
	public final static byte REMOVE_ID = 100;
	public final static byte UPDATESERVER_PART1 = 5;
	public final static byte UPDATESERVER_PART2 = 6;
	public final static byte UPDATESERVER_CACHE = 8;
	public static final byte LOGIN_OK = 2;
	public static final byte INVALID_PASSWORD = 3;
	public static final byte BANNED = 4;
	public static final byte ALREADY_ONLINE = 5;
	public static final byte WORLD_FULL = 7;
	public static final byte TRY_AGAIN = 11;

	public static final short MAX_AMT_OF_PLAYERS = 2048;
	public static final short MAX_AMT_OF_NPCS = 2048;

	public static final byte LOBBY_PM_CHAT_MESSAGE = 0;
	public static final byte LOBBY_CLAN_CHAT_MESSAGE = 11;
	public static final byte COMMANDS_MESSAGE = 99;

	public static final int LOTTERY_AMOUNT = 500;
	
	public static final int AVALIABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

	public static final String[] NAUGHTY_WORDS = { ".8k",

	};

	public static final String[] BAD_TAG_WORDS = { "owner", "staff", "mod",
			"admin", "developer"};
	
	public static final String[] SUPER_ADMIN = { "Fagex", "Jagex"};

}
