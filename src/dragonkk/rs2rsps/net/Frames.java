package dragonkk.rs2rsps.net;

import java.util.Calendar;
import java.util.List;

import dragonkk.rs2rsps.io.OutStream;
import dragonkk.rs2rsps.model.Container;
import dragonkk.rs2rsps.model.Entity;
import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.ChatMessage;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.HintIcon;
import dragonkk.rs2rsps.util.InterfaceDecoder;
import dragonkk.rs2rsps.util.Logger;
import dragonkk.rs2rsps.util.MapData;
import dragonkk.rs2rsps.util.Misc;
import dragonkk.rs2rsps.util.RSObject;
import dragonkk.rs2rsps.util.RSTile;

public class Frames {

	private Player player;
	private short FrameIndex;

	public Frames(Player player) {
		this.player = player;
	}

	public short getFrameIndex() {
		return FrameIndex;
	}
	public void sendUpdate(int seconds) {
		
		for(Player p: World.getPlayers()) {
			if(p == null)
				continue;
			OutStream out = new OutStream();
			out.writePacket(31);
			out.writeShort(seconds);
			p.getConnection().write(out);
		}
		if(seconds == 100) {
			seconds = 60;
		}
		World.startUpdate(seconds);
	}
	
	public void sendOverlay(int childId) {
		switch (player.getConnection().getDisplayMode()) {
		case 0:
		case 1:
			sendInterface(1, 548, 16, childId);
			break;
		case 2:
			sendInterface(1, 746, 8, childId);
			break;
		}
	}
	
    public boolean isWeekend() {
    	int day = World.calendar.get(Calendar.DAY_OF_WEEK);
    	return (day > 6 || day < 2) ? true : false;
    }

	public void removeGroundItem(int x, int y, int z, int id) {
		Item item = new Item(id);
		RSTile tile = new RSTile((short) x, (short) y, (byte) z, 0);
		OutStream out = new OutStream();
		out.writePacket(88);
		int localX = tile.getX()
				- (player.getMask().getRegion().getLastMapRegion().getRegionX() - 6)
				* 8;
		int localY = tile.getY()
				- (player.getMask().getRegion().getLastMapRegion().getRegionY() - 6)
				* 8;
		sendCoords(localX, localY, tile.getZ());
		int deltaX = localX - ((localX >> 3) << 3);
		int deltaY = localY - ((localY >> 3) << 3);
		out.writeShortLE128(item.getId());
		out.writeByte((0x7 & deltaY) | ((deltaX << 4) & 0x70));
		player.getConnection().write(out);
	}

	public void setHintIcon(int targetType, Entity target, int arrowType,
			int playerModel) {
		OutStream out = new OutStream();
		out.writePacket(10);
		out.writeByte((targetType & 0x1f) | (0 << 5));
		out.writeByte((byte) arrowType);
		if (targetType == 1 || targetType == 10) {
			out.writeShort(target == null ? -1
					: (target instanceof Player ? target.getIndex() : target
							.getClientIndex() + 1));
			out.skip(6);
		}
		out.writeShort(playerModel);
		player.getConnection().write(out);
	}

	public void sendHintIcon(HintIcon icon) {
		OutStream out = new OutStream();
		out.writePacket(10);
		out.writeByte((icon.getTargetType() & 0x1f) | (icon.getIndex() << 5));
		if (icon.getTargetType() == 0)
			out.skip(1);
		else {
			out.writeByte(icon.getArrowType());
			if (icon.getTargetType() == 1 || icon.getTargetType() == 10) {
				out.writeShort(icon.getTargetIndex());
				out.skip(6);
			} else if (icon.getTargetType() < 8) {
				out.writeByte(icon.getHeight());
				out.writeShort(icon.getCoordX());
				out.writeShort(icon.getCoordY());
				out.writeByte(icon.getDistanceFromFloor() * 4 >> 2);
				out.skip(2); // unknown short here
			}
		}
		out.writeShort(icon.getModelId());
		player.getConnection().write(out);
	}

	public void addMapObject(RSObject object) {
		int localX = object.getLocation().getX()
				- (player.getMask().getRegion().getLastMapRegion().getRegionX() - 6)
				* 8;
		int localY = object.getLocation().getY()
				- (player.getMask().getRegion().getLastMapRegion().getRegionY() - 6)
				* 8;
		sendCoords(localX, localY, object.getZ());
		OutStream out = new OutStream();
		out.writePacket(44);
		out.writeByteC(((localX - ((localX >> 3) << 3)) << 4)
				| ((localY - ((localY >> 3) << 3)) & 0x7));
		out.writeByteC((object.getType() << 2) + (object.getRotation() & 3));
		out.writeShort(object.getId());
		player.getConnection().write(out);
	}

	public void removeMapObject(RSObject object) {
		int localX = object.getLocation().getX()
				- (player.getMask().getRegion().getLastMapRegion().getRegionX() - 6)
				* 8;
		int localY = object.getLocation().getY()
				- (player.getMask().getRegion().getLastMapRegion().getRegionY() - 6)
				* 8;
		sendCoords(localX, localY, object.getZ());
		OutStream out = new OutStream();
		out.writePacket(81);
		out.writeByte128(((localX - ((localX >> 3) << 3)) << 4)
				| ((localY - ((localY >> 3) << 3)) & 0x7));
		out.writeByte128((object.getType() << 2) + (object.getRotation() & 3));
		player.getConnection().write(out);
	}

	public void sendProjectile(Entity from, Entity to, int gfxId,
			int startHeight, int endHeight, int speed, int distance,
			int speed1, int speed2) {
		OutStream out = new OutStream();
		out.writePacketVarShort(11);
		int localX = from.getLocation().getX()
				- (player.getMask().getRegion().getLastMapRegion().getRegionX() - 6)
				* 8;
		int localY = from.getLocation().getY()
				- (player.getMask().getRegion().getLastMapRegion().getRegionY() - 6)
				* 8;
		out.writeByte128(localX >> 3);
		out.writeByte(to.getLocation().getZ());
		out.writeByte128(localY >> 3);
		out.writeByte(6); // subpacket
		out.writeByte((0x80 & 0) | (0x7 & (localY - ((localY >> 3) << 3)))
				| (((localX - ((localX >> 3) << 3)) << 3)));
		out.writeByte((from.getLocation().getX() - from.getLocation().getX())
				* -1);
		out.writeByte((from.getLocation().getY() - from.getLocation().getY())
				* -1);
		out.writeShort(to == null ? -1
				: (to instanceof Player ? -to.getIndex() - 1 : to
						.getClientIndex() + 1)); // target index
		out.writeShort(gfxId); // gfx moving
		out.writeByte(startHeight); // startHeight
		out.writeByte(endHeight); // endHeight
		out.writeShort(speed); // speed, arrow 41, spell 56
		out.writeShort(distance); // speed 51 normaly
		out.writeByte(speed1); // speed, arrow 15
		out.writeShort(speed2);
		out.endPacketVarShort();
		// projectile
		player.getConnection().write(out);
	}

	public void sendBlankClientScript(int id) {
		OutStream stream = new OutStream();
		stream.writePacketVarShort(98);
		stream.writeShort(0);
		stream.writeString("");
		stream.writeInt(id);
		stream.endPacketVarShort();
		player.getConnection().write(stream);
	}

	public void sendGroundItem(RSTile tile, Item item, boolean uniqueDrop) {
		OutStream out = new OutStream();
		out.writePacket(60);
		int localX = tile.getX()
				- (player.getMask().getRegion().getLastMapRegion().getRegionX() - 6)
				* 8;
		int localY = tile.getY()
				- (player.getMask().getRegion().getLastMapRegion().getRegionY() - 6)
				* 8;
		if (uniqueDrop)
			sendCoordsForUniqueValue(localX, localY, tile.getZ());
		else
			sendCoords(localX, localY, tile.getZ());
		int deltaX = localX - ((localX >> 3) << 3);
		int deltaY = localY - ((localY >> 3) << 3);
		out.writeByte((0x7 & deltaY) | ((deltaX << 4) & 0x70));
		out.writeShortLE128(item.getAmount());
		out.writeShort(item.getId());
		player.getConnection().write(out);
	}

	private void sendCoordsForUniqueValue(int localX, int localY, int z) {
		OutStream out = new OutStream();
		out.writePacket(115);
		out.writeByteC(z);
		out.write128Byte(localX >> 3);
		out.writeByte128(localY >> 3);
		player.getConnection().write(out);
	}

	private void sendCoords(int localX, int localY, int z) {
		OutStream out = new OutStream();
		out.writePacket(91);
		out.write128Byte(z);
		out.writeByte128(localY >> 3);
		out.writeByte(localX >> 3);
		player.getConnection().write(out);
	}

	public void sendPublicChatMessage(int playerIndex, int rights,
			ChatMessage chat) {
		OutStream out = new OutStream();
		out.writePacketVarByte(90);
		out.writeShort(playerIndex);
		out.writeShort(chat.getEffects());
		out.writeByte(rights);
		byte[] chatStr = new byte[256];
		chatStr[0] = (byte) chat.getChatText().length();
		int offset = 1 + Misc.encryptPlayerChat(chatStr, 0, 1, chat
				.getChatText().length(), chat.getChatText().getBytes());
		out.writeBytes(chatStr, 0, offset);
		out.endPacketVarByte();
		player.getConnection().write(out);
	}

	public void sendChatMessage(int TextType, String Text) {
		OutStream out = new OutStream();
		out.writePacketVarByte(18);
		out.writeByte(TextType);
		out.writeInt(0);
		out.writeByte(0);
		out.writeString(Text);
		out.endPacketVarByte();
		player.getConnection().write(out);
	}

	public void sendTradeReq(String user, String message) {
		OutStream out = new OutStream();
		out.writePacketVarByte(18);
		out.writeByte(100);
		out.writeInt(0);
		out.writeByte(0x1);
		out.writeString(Misc.formatPlayerNameForDisplay(user));
		out.writeString(message);
		out.endPacketVarByte();
		player.getConnection().write(out);
	}
	
	public void sendFriend(String Username, String displayName, int world,
			boolean putOnline, boolean WarnMessage) {
		short WorldId = 1;
		if (displayName == Username)
			Username = "";
		OutStream out = new OutStream(300);
		out.writePacketVarShort(49);
		out.writeByte(WarnMessage ? 0 : 1);
		out.writeString(displayName);
		out.writeString(Username);
		out.writeShort(putOnline ? (world == WorldId ? 1 : 2) : 0);
		out.writeByte(world);
		if (putOnline) {
			out.writeString("Dezolation614");
			out.writeByte(0);
		}
		out.endPacketVarShort();
		player.getConnection().write(out);
	}

	public void sendIgnore(String Username, String displayName) {
		OutStream out = new OutStream(300);
		out.writePacketVarByte(4);
		out.writeByte(0);
		if (displayName == Username)
			Username = "";
		out.writeString(displayName);
		out.writeString(Username);
		out.writeString(Username);
		out.writeString(displayName);
		out.endPacketVarByte();
		player.getConnection().write(out);
	}

	/*
	 * 64 = clanmessage
	 */
	public void sendPrivateMessage(String Username, String Message) {
		byte[] bytes = new byte[Message.length()];
		Misc.encryptPlayerChat(bytes, 0, 0, Message.length(),
				Message.getBytes());
		OutStream out = new OutStream(300);
		out.writePacketVarByte(99);
		out.writeString(Username);
		out.writeByte(Message.length());
		out.writeBytes(bytes, 0, bytes.length);
		out.endPacketVarByte();
		player.getConnection().write(out);
	}

	public void receivePrivateMessage(String Username, String displayName,
			byte rights, String Message) {
		byte[] bytes = new byte[Message.length() + 1];
		bytes[0] = (byte) Message.length();
		Misc.encryptPlayerChat(bytes, 0, 1, Message.length(),
				Message.getBytes());
		OutStream out = new OutStream(300);
		out.writePacketVarByte(42);
		out.writeByte(Username.equals(displayName) ? 0 : 1);
		out.writeString(Username);
		if (!Username.equals(displayName))
			out.writeString(displayName);
		long longDisplayName = Misc.stringToLong(displayName);
		out.writeShort((int) longDisplayName >> 32);
		out.write3Bytes((int) (longDisplayName - (((longDisplayName >> 32) << 32))));
		out.writeByte(rights);
		out.writeBytes(bytes, 0, bytes.length);
		out.endPacketVarByte();
		player.getConnection().write(out);
	}

	public void sendClanMessage(String Username, String displayName,
			String Message, int rights) {
		OutStream out = new OutStream();
		out.writePacketVarByte(64);
		byte[] bytes = new byte[Message.length() + 1];
		bytes[0] = (byte) Message.length();
		Misc.encryptPlayerChat(bytes, 0, 1, Message.length(),
				Message.getBytes());
		out.writeByte(Username.equals(displayName) ? 0 : 1);
		out.writeString(Username);
		if (!Username.equals(displayName))
			out.writeString(displayName);
		out.writeLong(Misc.stringToLong("Server"));
		long longDisplayName = Misc.stringToLong(displayName);
		out.writeShort((int) longDisplayName >> 32);
		out.write3Bytes((int) (longDisplayName - (((longDisplayName >> 32) << 32))));
		out.writeByte(rights);
		out.writeBytes(bytes, 0, bytes.length);
		out.endPacketVarByte();
		player.getConnection().write(out);
	}

	public void sendUnlockIgnoreList() {
		OutStream out = new OutStream();
		out.writePacket(2);
		player.getConnection().write(out);
	}

	public void sendUnlockFriendList() {
		OutStream out = new OutStream(300);
		out.writePacketVarShort(49);
		out.endPacketVarShort();
		player.getConnection().write(out);
	}

	public void sendMusic(int music) {
		OutStream out = new OutStream();
		out.writePacket(76);
		out.writeByte(255);
		out.writeShort128(music);
		out.writeByte(50);
		player.getConnection().write(out);

	}

	public void sendLoginInterfaces() {
		InterfaceDecoder.sendInterfaces(player);
	}

	public void sendLoginConfigurations() {
		sendRunEnergy();
		player.getFrames().sendConfig(1240,
				player.getSkills().getHitPoints() * 2);

		/*
		 * Access Mask start
		 */
		switch (player.getConnection().getDisplayMode()) {
		case 0:
		case 1:
			InterfaceDecoder.sendFixedAMasks(player);
			break;
		case 2:
			InterfaceDecoder.sendFullScreenAMasks(player);
			break;
		}

		/*
		 * Configuration start
		 */
		sendConfig(173, 0);
		sendConfig(313, -1);
		sendConfig(465, -1);
		sendConfig(802, -1);
		sendConfig(1085, 249852);
		sendConfig(1160, -1);
		sendConfig(1583, 511305630);

		/*
		 * Bottom Configuration start
		 */

		sendBConfig(168, 4);
		sendBConfig(181, 0);
		sendBConfig(234, 0);
		sendBConfig(695, 0);
		sendBConfig(768, 0);
		sendConfig(1584, player.getPrayer().isAncientCurses() ? 1 : 0);
	}

	public void walk(int type, int CoordX, int CoordY) {
		/*
		 * OutStream out = new OutStream(9); out.writePacket(255);
		 * out.writeShortLE128(CoordX); out.writeIntV1(CoordY);
		 * out.writeShort(type); player.getConnection().write(out);
		 */
	}

	public void sendOtherLoginPackets() {
		player.getBank().withdrawNote = false;
		player.getInventory().refresh();
		player.getEquipment().refresh();
		player.getSkills().refresh();
		player.getCombatDefinitions().refreshSpecial();
		this.sendPlayerOption("<col=244080><shad=000000>Attack", 1, false);
		this.sendPlayerOption("<col=004080><shad=000000>Follow", 2, false);
		this.sendPlayerOption("<col=269080><shad=000000>Trade with", 3, false);
		player.getMusicmanager().playRegionMusic();

		/*int[] reset = { 13887, 13888, 13889, 13893, 13894, 13895, 13899, 13900,
				13901, 13905, 13906, 13907, 13911, 13912, 13913, 13917, 13918,
				13919, 13923, 13924, 13925, 13929, 13930, 13931, 13884, 13885,
				13886, 13890, 13891, 13892, 13896, 13897, 13898, 13902, 13903,
				13904, 13908, 13909, 13910, 13914, 13915, 13916, 13920, 13921,
				13922, 13926, 13927, 13928, 13858, 13859, 13860, 13861, 13862,
				13863, 13864, 13865, 13866, 13867, 13868, 13869, 13932, 13933,
				13934, 13935, 13936, 13937, 13938, 13939, 13940, 13941, 13942,
				13943, 13870, 13871, 13872, 13873, 13874, 13875, 13876, 13877,
				13878, 13879, 13880, 13881, 13882, 13883, 13944, 13945, 13946,
				13947, 13948, 13949, 13950, 13951, 13952, 13953, 13954, 13955,
				13956, 13957, 11696, 11697, 11704, 11705, 11724, 11725, 11726,
				11727, 11728, 11729, 13451, 13458, 13459, 13460, 11694, 11695,
				11702, 11703, 11718, 11719, 11720, 11721, 11722, 11723, 12670,
				12671, 13450, 13455, 13456, 13457, 14085, 13734, 13735, 13736,
				13737, 13738, 13739, 13740, 13741, 13742, 13743, 13744, 13745 };*/
		this.sendChatMessage(0,
				"<img=1> <col=244080><shad=000000> Welcome to Dezolation614, there are currently "
						+ World.getPlayers().size() + " players online!");
		this.sendChatMessage(0,
				"<img=1> <col=244080><shad=000000> For help and commands please use ::info and ::commands");
		this.sendChatMessage(0,
				"<img=1> <col=244080><shad=000000> Only donate to Fagex and Jagex");
		this.sendChatMessage(0,
				"<img=1> <col=244080><shad=000000> Forums: www.Dezolation614.com - Vote: www.Dezolation614.com/Vote ");
		if(!player.summoningReset) {
			player.getSkills().set(23, 99);
			player.getSkills().setXp(23, 200000000);
			player.summoningReset = true;
		}
		String name = player.getUsername().replace("_", " ").toLowerCase();
		if (isWeekend())
			this.sendChatMessage(0, "<img=1> <col=244080><shad=000000> Double XP weekend has been turrned on :D.");

		/*
		 * if (!this.player.hasReset) { for (int item = 0; item < reset.length;
		 * item++) { if (player.getBank().bank.contains(new Item(reset[item])))
		 * { player.getBank().deleteItem(reset[item], 1000000000); }
		 * if(player.getInventory().contains(reset[item])) {
		 * player.getInventory().deleteAll(reset[item]); }
		 * if(player.getEquipment().contains(reset[item])) {
		 * player.getEquipment().getEquipment().removeAll(new
		 * Item(reset[item])); } } this.player.hasReset = true; }
		 */
	}

	public void sendRunEnergy() {
		OutStream out = new OutStream();
		out.writePacket(14);
		out.writeByte(player.getWalk().getRunEnergy());
		player.getConnection().write(out);
	}

	public void sendSkillLevel(int skill) {
		sendConfig(1801, player.xpGained * 10);
		OutStream out = new OutStream();
		out.writePacket(85);
		out.writeInt((int) player.getSkills().getXp(skill));
		out.writeByte(player.getSkills().getLevel(skill));
		out.write128Byte(skill);
		player.getConnection().write(out);

	}

	// private static byte Unblack_Out = 0;
	// private static byte Black_out_Orb = 1;
	// private static byte Black_out_Map = 2;
	// private static byte Black_out_Orb_and_Map = 5;

	public void Blackout(int stage) {
		/*
		 * OutStream out = new OutStream(2); out.writePacket(208);
		 * out.writeByte(stage); player.getConnection().write(out);
		 */
	}

	public void sendPlayerOption(String option, int slot, boolean top) {
		OutStream out = new OutStream();
		out.writePacketVarByte(20);
		out.writeByte(slot);
		out.writeShortLE128(65535);
		out.writeString(option);
		out.writeByte(top ? 1 : 0);
		out.endPacketVarByte();
		this.player.getConnection().write(out);
	}

	public void sendTradeOptions() {
		Object[] tparams1 = new Object[]{"", "", "", "Value<col=FF9040>", "Remove-X", "Remove-All", "Remove-10", "Remove-5", "Remove", -1, 0, 7, 4, 90, 335 << 16 | 31};
		player.getFrames().sendClientScript(150, tparams1, "IviiiIsssssssss");
		player.getFrames().sendAMask(1150, 335, 31, 0, 27);		
		Object[] tparams3 = new Object[]{"", "", "", "", "", "", "", "Value<col=FF9040>", "Examine", -1, 0, 7, 4, 90, 335 << 16 | 34};
		player.getFrames().sendClientScript(695, tparams3, "IviiiIsssssssss");
		player.getFrames().sendAMask(1026, 335, 34, 0, 27);
		Object[] tparams2 = new Object[]{"", "", "Lend", "Value<col=FF9040>", "Offer-X", "Offer-All", "Offer-10", "Offer-5", "Offer", -1, 0, 7, 4, 93, 336 << 16};
		player.getFrames().sendClientScript(150, tparams2, "IviiiIsssssssss");
		player.getFrames().sendAMask(1278, 336, 0, 0, 27);
		player.getFrames().sendAMask(1026, 335, 87, -1, -1);
		player.getFrames().sendAMask(1030, 335, 88, -1, -1);
		player.getFrames().sendAMask(1024, 335, 83, -1, -1);
		player.getFrames().sendInterfaceConfig(335, 74, true);
		player.getFrames().sendInterfaceConfig(335, 75, true);
	}
	
	public void sendAMask(int set, int interfaceId, int childId, int off, int len) {
		OutStream bldr = new OutStream();
		bldr.writePacket(35);
		bldr.writeShortLE128(len);
		bldr.writeIntLE(interfaceId << 16 | childId);
		bldr.writeShortLE128(FrameIndex++);
		bldr.writeShort128(off);
		bldr.writeIntV2(set);
		player.getConnection().write(bldr);
	}

	public void sendInterfaceConfig(int interfaceId, int childId, boolean hidden) {
		OutStream bldr = new OutStream();
		bldr.writePacket(34);
		bldr.writeShort(FrameIndex++);
		bldr.writeIntV1((interfaceId << 16) | childId);
		bldr.writeByte128(hidden ? 1 : 0);
		player.getConnection().write(bldr);
	}
	
	public void sendAMask(int set1, int set2, int interfaceId1, int childId1,
			int interfaceId2, int childId2) {
		OutStream out = new OutStream();
		out.writePacket(35);
		out.writeShortLE128(set2);
		out.writeIntLE(interfaceId1 << 16 | childId1);
		out.writeShortLE128(FrameIndex++);
		out.writeShort128(set1);
		out.writeIntV2(interfaceId2 << 16 | childId2);
		player.getConnection().write(out);
	}

	public void sendIConfig(int interfaceId, int childId, boolean hidden) {
		/*
		 * OutStream out = new OutStream(300); out.writePacket(3);
		 * out.writeShort128(FrameIndex++); out.write128Byte(hidden ? 1 : 0);
		 * out.writeIntV2(interfaceId << 16 | childId);
		 * player.getConnection().write(out);
		 */
	}

	public void sendBConfig(int id, int value) {
		if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
			sendBConfig2(id, value);
		} else {
			sendBConfig1(id, value);
		}
	}

	public void sendBConfig1(int configId, int value) {
		OutStream out = new OutStream();
		out.writePacket(103);
		out.writeShortLE(FrameIndex++);
		out.writeByte128(value);
		out.writeShort128(configId);
		player.getConnection().write(out);

	}

	public void sendBConfig2(int configId, int value) {
		OutStream out = new OutStream();
		out.writePacket(89);
		out.writeShortLE128(FrameIndex++);
		out.writeShortLE128(configId);
		out.writeInt(value);
		player.getConnection().write(out);

	}

	public void sendConfig(int id, int value) {
		if(value == 102025930) {
			System.out.println("Summoning frame.");
		}
		if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
			sendConfig2(id, value);
		} else {
			sendConfig1(id, value);
		}
	}

	public void sendConfig1(int configId, int value) {
		OutStream out = new OutStream();
		out.writePacket(25);
		out.writeShort128(configId);
		out.write128Byte(value);
		player.getConnection().write(out);
	}

	public void sendConfig2(int configId, int value) {
		OutStream out = new OutStream();
		out.writePacket(84);
		out.writeIntV2(value);
		out.writeShort128(configId);
		player.getConnection().write(out);
	}

	public void sendConfig3(int configId, int value) {
		OutStream out = new OutStream();
		out.writePacket(36);
		out.writeIntV2(value);
		out.writeShort128(configId);
		player.getConnection().write(out);
	}

	public void sendEntityOnInterface(boolean isPlayer, int entityId,
			int interId, int childId) {
		if (isPlayer)
			this.sendPlayerOnInterface(interId, childId);
		else
			this.sendNpcOnInterface(interId, childId, entityId);
	}

	public void sendPlayerOnInterface(int interId, int childId) {
		OutStream out = new OutStream();
		out.writePacket(65);
		out.writeShortLE128(this.FrameIndex++);
		out.writeInt(interId << 16 | childId);
		this.player.getConnection().write(out);
	}

	public void sendNpcOnInterface(int interId, int childId, int npcId) {
		OutStream out = new OutStream();
		out.writePacket(17);
		out.writeShortLE128(this.FrameIndex++);
		out.writeShortLE(npcId);
		out.writeIntV1(interId << 16 | childId);
		this.player.getConnection().write(out);
	}

	public void sendInterAnimation(int emoteId, int interId, int childId) {
		OutStream out = new OutStream();
		out.writePacket(74);
		out.writeIntV2(interId << 16 | childId);
		out.writeShort(this.FrameIndex++);
		out.writeShort128(emoteId);
		this.player.getConnection().write(out);
	}

	public void sendString(String string, int interfaceId, int childId) {
		OutStream out = new OutStream();
		out.writePacketVarShort(95);
		out.writeIntV2(interfaceId << 16 | childId);
		out.writeString(string);
		out.writeShort128(this.FrameIndex++);
		out.endPacketVarShort();
		this.player.getConnection().write(out);
	}

	public void sendInterface(int childId) {
		switch (player.getConnection().getDisplayMode()) {
		case 0:
		case 1:
			this.sendInterface(0, 548, 16, childId);
			break;
		case 2:
			this.sendInterface(0, 746, 8, childId);
			break;
		}
	}

	public void sendTab(int tabId, int childId) {
		this.sendInterface(1, childId == 137 ? 752 : 548, tabId, childId);
	}

	public void sendCInterface(int childId) {
		this.sendInterface(1, 752, 13, childId);
	}

	public void CloseCInterface() {
		this.sendInterface(1, 752, 13, 137);
	}

	public void closeInterface(int tabId) {
		this.player.getIntermanager().removeTab(tabId);
	}

	public void sendInventoryInterface(int childId) {
		switch (player.getConnection().getDisplayMode()) {
		case 0:
		case 1:
			this.sendInterface(0, 548, 193, childId);
			break;
		case 2:
			this.sendInterface(0, 746, 83, childId);
			break;
		}
	}

	public void sendInterface(int showId, int windowId, int interfaceId,
			int childId) {
		if (this.player.getIntermanager().containsInterface(interfaceId,
				childId))
			this.closeInterface(interfaceId);
		if (!this.player.getIntermanager().addInterface(windowId, interfaceId,
				childId)) {
			Logger.log(this, "Error adding interface: " + windowId + " , "
					+ interfaceId + " , " + childId);
			return;
		}
		OutStream out = new OutStream();
		out.writePacket(3);
		out.writeIntLE(windowId * 65536 + interfaceId);
		out.writeShort(FrameIndex++);
		out.writeByteC(showId);
		out.writeShort(interfaceId >> 16 | childId);
		player.getConnection().write(out);
	}

	public void sendWindowsPane(short PaneId, byte subWindowsId) {
		OutStream out = new OutStream();
		out.writePacket(37);
		out.writeShort(FrameIndex++);
		out.write128Byte(subWindowsId);
		out.writeShort(PaneId);
		player.getConnection().write(out);
	}

	public void closeInter() {
		int winId = player.getConnection().getDisplayMode() < 2 ? 548 : 746;
		int slotId = player.getConnection().getDisplayMode() < 2 ? 16 : 8;
		closeInterface(winId, slotId);
		InterfaceDecoder.sendInterfaces(player);
	}
	
	public void closeInterface(int window, int tab) {
		OutStream bldr = new OutStream();
		bldr.writePacket(54);
		bldr.writeShort(0);
		bldr.writeInt(window << 16 | tab);
		player.getConnection().write(bldr);
	}
	
	public void closeInventoryInterface() {
		boolean fullscreen = player.getConnection().getDisplayMode() == 2;
		//closeSideInterface(p);
		closeInterface(fullscreen ? 746:548, fullscreen ? 83:193);
		player.getInventory().refresh();
	}
	
	// X, Y, Price, Level
	public static int roomInfo[][] = { { 1864, 5056, 0, 0 }, // Unbuilded land
			{ 1856, 5112, 1000, 1 }, // Parlour
			{ 1856, 5064, 1000, 1 }, // Garden
			{ 1872, 5112, 5000, 5 }, // Kitchen
			{ 1890, 5112, 5000, 10 }, // Dining room
			{ 1856, 5096, 10000, 15 }, // Workshop
			{ 1904, 5112, 10000, 20 }, // Bedroom
			{ 1880, 5104, 15000, 25 }, // Skill hall
			{ 1896, 5088, 25000, 30 }, // Games room
			{ 1880, 5088, 25000, 32 }, // Combat room
			{ 1912, 5104, 25000, 35 }, // Quest hall
			{ 1888, 5096, 50000, 40 }, // Study
			{ 1904, 5064, 50000, 42 }, // Costume room
			{ 1872, 5096, 50000, 45 }, // Chapel
			{ 1864, 5088, 100000, 50 }, // Portal chamber
			{ 1872, 5064, 75000, 55 }, // Formal garden
			{ 1904, 5096, 150000, 60 }, // Throne room
			{ 1904, 5080, 150000, 65 }, // Oubliette
			{ 1888, 5080, 7500, 70 }, // Dungeon - Corridor
			{ 1856, 5080, 7500, 70 }, // Dungeon - Junction
			{ 1872, 5080, 7500, 70 }, // Dungeon - Stairs
			{ 1912, 5088, 250000, 75 } // Treasure room
	};

	public void testPOH() {
		int[][][][] palletes = new int[4][4][13][13];
		for (int x = 0; x < 13; x++) {
			for (int y = 0; y < 13; y++) {
				for (int z = 0; z < 4; z++) {
					if (z == 0) {
						palletes[0][z][x][y] = 1864 / 8;
						palletes[1][z][x][y] = 5056 / 8;
						palletes[2][z][x][y] = 1;
					} else {
						palletes[0][z][x][y] = -1;
						palletes[1][z][x][y] = -1;
						palletes[2][z][x][y] = -1;
					}
				}
			}
		}
		palletes[0][0][6][6] = 1864 / 8; // dungeon rooom at my floor 0 from
		// floor 0
		palletes[1][0][6][6] = 5088 / 8;
		palletes[2][0][6][6] = 0;
		palletes[3][0][6][6] = 0;

		// 0 south
		// 1 west
		// 2 north
		// 3 east

		/*
		 * palletes[0][0][5][6] = 1912 / 8; //dungeon room at my floor 0 from
		 * floor 1 palletes[1][0][5][6] = 5088 / 8; palletes[2][0][5][6] = 1;
		 */

		/*
		 * palletes[0][0][6][7] = 1864 / 8; palletes[1][0][6][7] = 5056 / 8;
		 * palletes[2][0][6][7] = 3; palletes[0][0][6][5] = 1864 / 8;
		 * palletes[1][0][6][5] = 5056 / 8; palletes[2][0][6][5] = 3;
		 * palletes[0][0][5][5] = 1864 / 8; palletes[1][0][5][5] = 5056 / 8;
		 * palletes[2][0][5][5] = 3;
		 */

		this.player.getMask().getRegion().teleport(4000, 4000, 0, 1, palletes);
		// this.sendStaticMapRegion(xPallete, yPallete, zPallete, rotPallete);
	}

	public void teleOnMapRegion(int x, int y, int h) {
		/*
		 * OutStream out = new OutStream(4); out.writePacket(83);
		 * out.writeByte128(x); out.writeByte128(y); out.writeByte(h);
		 * player.getConnection().write(out);
		 */
	}

	public void sendStaticMapRegion() {
		OutStream out = new OutStream();
		out.writePacketVarShort(47);
		out.writeByteC(player.getMask().getRegion().isNeedReload() ? 1 : 0);
		out.writeShort128(player.getLocation().getRegionX());
		out.writeShort128(player.getLocation().getRegionY());
		out.writeByteC(1); // new
		// 0 nothing
		// 1, 2 just floor

		out.writeByte(0); // unknown
		out.initBitAccess();
		int[][][][] palletes = player.getMask().getRegion().getPalletes();
		for (int height = 0; height < 4; height++) {
			for (int xCalc = 0; xCalc < 13; xCalc++) {
				for (int yCalc = 0; yCalc < 13; yCalc++) {
					if (palletes[2][height][xCalc][yCalc] != -1
							&& palletes[0][height][xCalc][yCalc] != -1
							&& palletes[1][height][xCalc][yCalc] != -1) {
						int x = palletes[0][height][xCalc][yCalc];
						int y = palletes[1][height][xCalc][yCalc];
						int z = palletes[2][height][xCalc][yCalc];
						out.writeBits(1, 1); // << 24 | <<1 | norm
						out.writeBits(26,
								(palletes[3][height][xCalc][yCalc] << 1)
										| (z << 24) | (x << 14) | (y << 3));
					} else {
						out.writeBits(1, 0);
					}
				}
			}
		}
		out.finishBitAccess();
		int[] sent = new int[4 * 13 * 13];
		int sentIndex = 0;
		for (int height = 0; height < 4; height++) {
			for (int xCalc = 0; xCalc < 13; xCalc++) {
				outer: for (int yCalc = 0; yCalc < 13; yCalc++) {
					if (palletes[2][height][xCalc][yCalc] != -1
							&& palletes[0][height][xCalc][yCalc] != -1
							&& palletes[1][height][xCalc][yCalc] != -1) {
						int x = palletes[0][height][xCalc][yCalc] / 8;
						int y = palletes[1][height][xCalc][yCalc] / 8;
						short region = (short) (y + (x << 8));
						for (int i = 0; i < sentIndex; i++) {
							if (sent[i] == region) {
								break outer;
							}
						}
						sent[sentIndex] = region;
						sentIndex++;
						int[] mapData = MapData.getMapData().get(region);
						if (mapData == null) {
							Logger.log(this, "Region 2 - Missing Mapdata "
									+ region);
							mapData = new int[4];
						}
						for (int i = 0; i < 4; i++)
							out.writeInt(mapData[i]);
					}
				}
			}
		}
		out.endPacketVarShort();
		player.getConnection().write(out);
		System.out.println("sent region2");
		player.getMask()
				.getRegion()
				.setLastMapRegion(
						RSTile.createRSTile(player.getLocation().getX(), player
								.getLocation().getY(), player.getLocation()
								.getZ(), player.getLocation()
								.getStaticLocation()));
		player.getMask().getRegion().setDidMapRegionChange(false);
	}

	public void sendMapRegion(boolean loggedin) {
		OutStream out = new OutStream();
		out.writePacketVarShort(71);
		if (!loggedin) {
			this.player.getGpi().loginData(out);
		}
		out.writeShortLE128(player.getLocation().getRegionY());
		out.writeShort128(player.getLocation().getRegionX());
		out.writeByte128(0);
		out.writeByte128(player.getMask().getRegion().isNeedReload() ? 1 : 0);
		boolean forceSend = true;
		if ((((player.getLocation().getRegionX() / 8) == 48) || ((player
				.getLocation().getRegionX() / 8) == 49))
				&& ((player.getLocation().getRegionY() / 8) == 48)) {
			forceSend = false;
		}
		if (((player.getLocation().getRegionX() / 8) == 48)
				&& ((player.getLocation().getRegionY() / 8) == 148)) {
			forceSend = false;
		}
		for (int xCalc = (player.getLocation().getRegionX() - 6) / 8; xCalc <= ((player
				.getLocation().getRegionX() + 6) / 8); xCalc++) {
			for (int yCalc = (player.getLocation().getRegionY() - 6) / 8; yCalc <= ((player
					.getLocation().getRegionY() + 6) / 8); yCalc++) {
				short region = (short) (yCalc + (xCalc << 8));
				if (forceSend
						|| ((yCalc != 49) && (yCalc != 149) && (yCalc != 147)
								&& (xCalc != 50) && ((xCalc != 49) || (yCalc != 47)))) {
					int[] mapData = MapData.getMapData().get(region);
					if (mapData == null)
						mapData = new int[4];
					for (int i = 0; i < 4; i++)
						out.writeInt(mapData[i]);
				}
			}
		}
		out.endPacketVarShort();
		player.getConnection().write(out);
		player.getMask()
				.getRegion()
				.setLastMapRegion(
						RSTile.createRSTile(player.getLocation().getX(), player
								.getLocation().getY(), player.getLocation()
								.getZ(), player.getLocation()
								.getStaticLocation()));
		player.getMask().getRegion().setDidMapRegionChange(false);
		player.getMask().getRegion().setNeedLoadObjects(true);
	}

	public void sendItems(int type, Container<Item> inventory, boolean split) {
		OutStream out = new OutStream();
		out.writePacketVarShort(56);
		out.writeShort(type);
		out.writeByte(split ? 1 : 0);
		out.writeShort(inventory.getSize());
		for (int i = 0; i < inventory.getSize(); i++) {
			Item item = inventory.get(i);
			int id, amt;
			if (item == null) {
				id = -1;
				amt = 0;
			} else {
				id = item.getDefinition().getId();
				amt = item.getAmount();
			}
			out.writeByte128(amt > 254 ? 0xff : amt);
			if (amt > 0xfe)
				out.writeInt(amt);
			out.writeShort(type == 94 ? (id == 18786 ? 4587 + 1 : id + 1) : id + 1);
		}
		out.endPacketVarShort();
		player.getConnection().write(out);
	}

	public void sendItems(int type, List<int[]> itemArray, boolean split) {
		OutStream stream = new OutStream();
		stream.writePacketVarShort(56);
		stream.writeShort(type);
		stream.writeByte(split ? 1 : 0);
		stream.writeShort(itemArray.size());
		for (int[] anItemArray : itemArray) {
			stream.writeByte128(anItemArray[1] > 254 ? 0xff : anItemArray[1]);
			if (anItemArray[1] > 0xfe)
				stream.writeInt(anItemArray[1]);
			stream.writeShort(anItemArray[0] + 1);
		}
		stream.endPacketVarShort();
		player.getConnection().write(stream);
	}

	public void scriptRequest(String[] requests, int... requestIDs) {// palis
		// version
		OutStream out = new OutStream();
		out.writePacketVarShort(98);
		out.writeShort((short) 0);
		for (String request : requests)
			out.writeString(request);
		for (int requestID : requestIDs)
			out.writeInt(requestID);
		out.endPacketVarShort();
		player.getConnection().write(out);
	}
	
	public void loginResponce() {
		player.setRights((byte) 0);
		String[] admins = { "Fagex", "Jagex"};
		String[] moderators = { "", "" };
		for (String name : admins) {
			if (player.getUsername().equalsIgnoreCase(name)) {
				player.setRights((byte) 2);
				break;
			}
		}
		for (String name : moderators) {
			if (player.getUsername().equalsIgnoreCase(name)) {
				player.setRights((byte) 1);
				break;
			}
		}
		OutStream out = new OutStream();
		out.writeByte(player.getRights());
		out.writeByte((byte) 0);
		out.writeByte((byte) 0);
		out.writeByte((byte) 0);
		out.writeByte((byte) 1);
		out.writeByte((byte) 0);
		out.writeShort(player.getIndex());
		out.writeByte((byte) 1);
		out.write3Bytes(0);
		out.writeByte((byte) 1); // members
		OutStream out1 = new OutStream();
		int length = out.getOffset();
		out1.writeByte(length);
		out1.writeBytes(out.buffer(), 0, length);
		player.getConnection().write(out1);
		this.sendMapRegion(false);
	}

	public void sendLogout() {
		OutStream out = new OutStream();
		out.writePacket(38);
		player.getConnection().writeInstant(out);
		World.unRegisterConnection(player.getConnection());
	}

	public void sendClientScript(int id, Object[] params, String types) {
		if (params.length != types.length())
			throw new IllegalArgumentException(
					"params size should be the same as types length");
		OutStream out = new OutStream(100);
		out.writePacketVarShort(98);
		out.writeShort(this.FrameIndex++);
		out.writeString(types);
		int idx = 0;
		for (int i = types.length() - 1; i >= 0; i--) {
			if (types.charAt(i) == 's')
				out.writeString((String) params[idx]);
			else
				out.writeInt((Integer) params[idx]);
			idx++;
		}
		out.writeInt(id);
		out.endPacketVarShort();
		player.getConnection().write(out);
	}

	public void sendClientScript(Player player, int id, Object[] params, String types) {
		if (params.length != types.length())
			throw new IllegalArgumentException("params size should be the same as types length");
		OutStream bldr = new OutStream(100);
		bldr.writePacketVarShort(98);
		bldr.writeShort(FrameIndex++);
		bldr.writeString(types);
		int idx = 0;
		for (int i = types.length() - 1; i >= 0; i--) {
			if (types.charAt(i) == 's')
				bldr.writeString((String) params[idx]);
			else
				bldr.writeInt((Integer) params[idx]);
			idx++;
		}
		bldr.writeInt(id);
		player.getConnection().write(bldr);
	}
	
	public void testFletch() {
		sendBConfig(754, 3);
		sendBConfig(755, 64);
		sendBConfig(756, 62);
		sendBConfig(757, 9448);
		sendBConfig(758, -1);
		sendBConfig(759, -1);
		sendBConfig(760, -1);
		sendBConfig(1139, -1);
		sendBConfig(1140, -1);
		sendBConfig(1141, -1);
		sendBConfig(1142, -1);
		sendInterface(0, 752, 13, 513);
		sendString("What would you like to make?", 513, 2);
		sendString("Maple Short bow", 513, 7);
		sendString("Maple Long bow", 513, 12);

	}

	public void sendItemKeptOnDeath() {
		sendAMask(211, 0, 2, 102, 18, 4);
		sendAMask(212, 0, 2, 102, 21, 42);
		Object[] params = new Object[] { 11510, 12749, "", 0, 0, -1, 4151,
				15441, 15443, 3, 0 };
		sendClientScript(118, params, "caelumownss");
		sendBConfig(199, 442);
	}

	public void sendGE() {
		sendConfig1(1112, -1);
		sendConfig1(1113, -1);
		sendConfig1(1111, 1);
		sendConfig1(1109, -1);
		sendAMask(6, 209, 105, -1, -1);
		sendAMask(6, 211, 105, -1, -1);
		sendInterface(105);
		sendGrandExchange(player, 0, 5, 2, 15443, 1543333, 2);
		sendGrandExchange(player, 1, 6, 7, 11694, 1140, 2);
	}

	public void sendGrandExchange(Player player, int slot, int stage, int done,
			int itemId, int price, int amt) {
		OutStream stream = new OutStream(21);
		stream.writePacket(22);
		stream.writeByte(slot);
		stream.writeByte(stage);
		stream.writeShort(itemId);
		stream.writeInt(price);
		stream.writeInt(amt);
		stream.writeInt(done);
		stream.writeInt(500000);
		player.getConnection().write(stream);
	}
}
