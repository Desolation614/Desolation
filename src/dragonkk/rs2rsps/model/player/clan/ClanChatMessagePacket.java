package dragonkk.rs2rsps.model.player.clan;

import java.util.Random;

import dragonkk.rs2rsps.io.OutStream;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.Misc;

public class ClanChatMessagePacket {

	public static int messageCounter = 1;
	public static final Random r = new Random();
	public static int id = 0;
	
	public static void sendClanChatMessage(Player from, Player pl, String roomName, String user, String message) {
		int messageCounter = getNextUniqueId();
		OutStream bldr = new OutStream();
		bldr.writePacketVarByte(64);
		bldr.writeByte(0);
		bldr.writeString(Misc.formatPlayerNameForDisplay((from.getDisplayName())));
		bldr.writeLong(Misc.stringToLong(roomName));
		bldr.writeShort(r.nextInt());
		byte[] bytes = new byte[256];
		bytes[0] = (byte) message.length();
		int len = 1 + Misc.huffmanCompress(message, bytes, 1);
		bldr.writeMediumInt(messageCounter);
		bldr.writeByte((byte) from.getRights());
		bldr.writeBytes(bytes, 0, len);
		bldr.endPacketVarByte();
		if (pl != null)
			pl.getConnection().write(bldr);
		else 
			from.getConnection().write(bldr);
		//TODO
	}

	public static int getNextUniqueId() {
		if (messageCounter >= 16000000) {
			messageCounter = 0;
		}
		return messageCounter++;
	}

}
