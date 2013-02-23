package dragonkk.rs2rsps.net;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dragonkk.rs2rsps.HostList;
import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.io.InStream;
import dragonkk.rs2rsps.model.GlobalDropItem;
import dragonkk.rs2rsps.model.GlobalDropManager;
import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.npc.Npc;
import dragonkk.rs2rsps.model.player.ChatMessage;
import dragonkk.rs2rsps.model.player.Inventory;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.Skills;
import dragonkk.rs2rsps.model.player.TradeSession;
import dragonkk.rs2rsps.model.player.clan.ClanOutboundPackets;
import dragonkk.rs2rsps.net.codec.ConnectionHandler;
import dragonkk.rs2rsps.rsobjects.RSObjectsRegion;
import dragonkk.rs2rsps.scripts.Scripts;
import dragonkk.rs2rsps.scripts.interfaceScript;
import dragonkk.rs2rsps.scripts.objectScript;
import dragonkk.rs2rsps.skills.magic.LunarMagics;
import dragonkk.rs2rsps.util.Constants;
import dragonkk.rs2rsps.util.Misc;
import dragonkk.rs2rsps.util.RSTile;
import dragonkk.rs2rsps.util.Serializer;

public class Packets {

	public static final int[][] COIN_REQS = { { 4151, 3400000 },
			{ 9244, 6786 }, { 14484, 32000000 }, { 13887, 2600000 },
			{ 13893, 3700000 }, { 13899, 16800000 }, { 11694, 77800000 },
			{ 11696, 20800000 }, { 11698, 55200000 }, { 11700, 22000000 } };

	public static Player getPlayerByName(String name) {
		name = name.replaceAll(" ", "_");
		for (Player p : World.getPlayers()) {
			if (p.getUsername().equalsIgnoreCase(name)) {
				return p;
			}
		}
		return null;
	}

	private static final Map<Short, Method> packets = new HashMap<Short, Method>();
	private static final byte[] PacketSize = new byte[256];

	private static void reset() {
		for (int i = 0; i < 256; i++) {
			PacketSize[i] = -3;
		}
		PacketSize[0] = 8;
		PacketSize[1] = -1;
		PacketSize[2] = -1;
		PacketSize[3] = -1;
		PacketSize[4] = 1;
		PacketSize[5] = 4;
		PacketSize[6] = 0;
		PacketSize[7] = 6;
		PacketSize[8] = -1;
		PacketSize[9] = 7;
		PacketSize[10] = 16;
		PacketSize[11] = 3;
		PacketSize[12] = -1;
		PacketSize[13] = 8;
		PacketSize[14] = 8;
		PacketSize[15] = 3;
		PacketSize[16] = 7;
		PacketSize[17] = 4;
		PacketSize[18] = 2;
		PacketSize[19] = 7;
		PacketSize[20] = 8;
		PacketSize[21] = 3;
		PacketSize[22] = 2;
		PacketSize[23] = 15;
		PacketSize[24] = 8;
		PacketSize[25] = -1;
		PacketSize[26] = 3;
		PacketSize[27] = -1;
		PacketSize[28] = 11;
		PacketSize[29] = 7;
		PacketSize[30] = 0;
		PacketSize[31] = 2;
		PacketSize[32] = 2;
		PacketSize[33] = 7;
		PacketSize[34] = 6;
		PacketSize[35] = 4;
		PacketSize[36] = 3;
		PacketSize[37] = -1;
		PacketSize[38] = 15;
		PacketSize[39] = 0;
		PacketSize[40] = 8;
		PacketSize[41] = 4;
		PacketSize[42] = 3;
		PacketSize[43] = 7;
		PacketSize[44] = 4;
		PacketSize[45] = 2;
		PacketSize[46] = -1;
		PacketSize[47] = -1;
		PacketSize[48] = 8;
		PacketSize[49] = 4;
		PacketSize[50] = 3;
		PacketSize[51] = -1;
		PacketSize[52] = 8;
		PacketSize[53] = -1;
		PacketSize[54] = -1;
		PacketSize[55] = 8;
		PacketSize[56] = 7;
		PacketSize[57] = 11;
		PacketSize[58] = 12;
		PacketSize[59] = 3;
		PacketSize[60] = -1;
		PacketSize[61] = 4;
		PacketSize[62] = -1;
		PacketSize[63] = 3;
		PacketSize[64] = 7;
		PacketSize[65] = 3;
		PacketSize[66] = 3;
		PacketSize[67] = -1;
		PacketSize[68] = 2;
		PacketSize[69] = -1;
		PacketSize[70] = 3;
		PacketSize[71] = -1;
		PacketSize[72] = -1;
		PacketSize[73] = 6;
		PacketSize[74] = 3;
		PacketSize[75] = -1;
		PacketSize[76] = 3;
		PacketSize[77] = -1;
		PacketSize[78] = 7;
		PacketSize[79] = 8;
		PacketSize[80] = 7;
		PacketSize[81] = -1;
		PacketSize[82] = 16;
		PacketSize[83] = 1;
	}

	public Packets() {
		final List<Short> Packets = new ArrayList<Short>(255);
		Packets.add((short) 1);
		// 1, 75, 54, 104, 72
		Packets.add((short) 75);
		Packets.add((short) 72);
		Packets.add((short) 54);
		Packets.add((short) 104);
		Packets.add((short) 0);
		Packets.add((short) 3);
		Packets.add((short) 6);
		// Packets.add((short) 8);
		Packets.add((short) 10);
		Packets.add((short) 13);
		Packets.add((short) 14);
		Packets.add((short) 15);
		Packets.add((short) 19);
		Packets.add((short) 20);
		Packets.add((short) 24);
		Packets.add((short) 25);
		Packets.add((short) 26);
		Packets.add((short) 29);
		Packets.add((short) 30);
		Packets.add((short) 34);
		Packets.add((short) 37);
		Packets.add((short) 39);
		Packets.add((short) 40);
		Packets.add((short) 48);
		Packets.add((short) 52);
		Packets.add((short) 53);
		Packets.add((short) 55);
		Packets.add((short) 57);
		Packets.add((short) 60);
		Packets.add((short) 61);
		Packets.add((short) 66);
		Packets.add((short) 71);
		Packets.add((short) 73);
		Packets.add((short) 78);
		Packets.add((short) 79);
		Packets.add((short) 82);
		Packets.add((short) 83);
		Short[] PacketsA = new Short[Packets.size()];
		Packets.toArray(PacketsA);
		reset();
		setPackets(PacketsA);
	}

	/*
	 * ActionButtom
	 */
	@SuppressWarnings("unused")
	private static void PacketId_0(InStream Packet, int Size, Player p) {
		int interfaceId = Packet.readShort();
		int buttonId = Packet.readShort();
		int buttonId3 = Packet.readShort();
		int buttonId2 = Packet.readShortLE128();
		if (!p.getIntermanager().containsInterface(interfaceId))
			return;
		interfaceScript inter = Scripts
				.invokeInterfaceScript((short) interfaceId);
		if (inter == null) {
			return;
		}
		inter.actionButton(p, 0, buttonId, buttonId2, buttonId3);
	}

	@SuppressWarnings("unused")
	private static void PacketId_8(InStream Packet, int Size, Player p) {
		String owner = "";
		if (Packet.remaining() > 0) {
			owner = Packet.readRS2String();
		}
		if (p.currentlyTalkingIn != null) {
			World.clanManager.leaveClan(p, p.currentlyTalkingIn);
			ClanOutboundPackets.sendClanList(p, null);
			return;
		}
		for (int i = 0; i < owner.length(); ++i) {
			if (!Character.isLetterOrDigit(owner.charAt(i))
					&& !Character.isSpaceChar(owner.charAt(i))) {
				if (p.currentlyTalkingIn != null) {
					World.clanManager.leaveClan(p, p.currentlyTalkingIn);
					ClanOutboundPackets.sendClanList(p, null);
					return;
				}
				return;
			}
		}
		switch (Packet.opcode) {
		case 8: // JOIN
			if (owner.length() > 0) {
				World.clanManager.joinClan(p, owner);
			} else {
				World.clanManager.leaveClan(p, p.currentlyTalkingIn);
				ClanOutboundPackets.sendClanList(p, null);
			}
			break;
		}

	}

	@SuppressWarnings("unused")
	private static void PacketId_61(InStream Packet, int Size, Player p) {
		p.lastResponce = System.currentTimeMillis();
	}

	/*
	 * Mouse
	 */
	@SuppressWarnings("unused")
	private static void PacketId_3(InStream Packet, int Size, Player p) {
		p.lastResponce = System.currentTimeMillis();
	}

	@SuppressWarnings("unused")
	private static void PacketId_6(InStream Packet, int Size, Player p) {
		if (p.getIntermanager().containsTab(16))
			p.getFrames().closeInterface(16);
		if (p.getTradeSession() != null) {
			p.getTradeSession().tradeFailed();
		}
	}

	/*
	 * Switch
	 */
	@SuppressWarnings("giveitem")
	private static void PacketId_10(InStream Packet, int Size, Player p) {
		int fromInterfaceHash = Packet.readInt();
		int fromInterfaceId = fromInterfaceHash >> 16;
		int toItemId = Packet.readShort(); // to item id
		int toSlot = Packet.readShort128() - 28;
		int toInterfaceHash = Packet.readIntLE();
		int toInterfaceId = toInterfaceHash >> 16;
		int tabId = (toInterfaceHash & 0xFF);
		int fromItemId = Packet.readShortLE128();// from item id
		int fromId = Packet.readShortLE();
		int tabIndex = p.getBank().getArrayIndex(tabId);
		int fromTab;
		switch (fromInterfaceId) {
		case 149:
			switch (toInterfaceId) {
			case 762:
				/*
				 * Bank.
				 */
					if (fromId < 0 || fromId >= 468 || toSlot < 0
							|| toSlot >= 468) {
						break;
					}
					if (!p.getBank().inserting) {
						Item temp = p.getBank().bank.get(fromId);
						Item temp2 = p.getBank().bank.get(toSlot);
						p.getBank().bank.set(fromId, temp2);
						p.getBank().bank.set(toSlot, temp);
						p.getBank().refresh();
					} else {
						if (toSlot > fromId) {
							p.getBank().insert(fromId, toSlot - 1);
						} else if (fromId > toSlot) {
							p.getBank().insert(fromId, toSlot);
						}
						p.getBank().refresh();
					}
					break;
			case 149:
				if (fromId < 0 || fromId >= Inventory.SIZE
						|| p.getInventory().getContainer().get(fromId) == null)
					return;
				if (toSlot < 0 || fromId >= Inventory.SIZE)
					return;
				Item toSlotItem = p.getInventory().getContainer().get(toSlot);
				p.getInventory().getContainer().set(toSlot,
						p.getInventory().getContainer().get(fromId));
				p.getInventory().getContainer().set(fromId, toSlotItem);
				break;
			}
			break;
		}
	}

	/*
	 * ActionButtom
	 */
	@SuppressWarnings("unused")
	private static void PacketId_13(InStream Packet, int Size, Player p) {
		int interfaceId = Packet.readShort();
		int buttonId = Packet.readShort();
		int buttonId3 = Packet.readShort();
		int buttonId2 = Packet.readShortLE128();
		if (!p.getIntermanager().containsInterface(interfaceId))
			return;
		interfaceScript inter = Scripts
				.invokeInterfaceScript((short) interfaceId);
		if (inter == null) {
			// System.out.println("Unhandled Button: " + buttonId
			// + " on interface: " + interfaceId);
			return;
		}
		inter.actionButton(p, 13, buttonId, buttonId2, buttonId3);
	}

	/*
	 * ActionButtom
	 */
	@SuppressWarnings("unused")
	private static void PacketId_14(InStream Packet, int Size, Player p) {
		int interfaceId = Packet.readShort();
		int buttonId = Packet.readShort();
		int buttonId3 = Packet.readShort();
		int buttonId2 = Packet.readShortLE128();
		if (!p.getIntermanager().containsInterface(interfaceId))
			return;
		interfaceScript inter = Scripts
				.invokeInterfaceScript((short) interfaceId);
		if (inter == null) {
			// System.out.println("Unhandled Button: " + buttonId
			// + " on interface: " + interfaceId);
			return;
		}
		inter.actionButton(p, 0, buttonId, buttonId2, buttonId3);
	}

	@SuppressWarnings("unused")
	private static void PacketId_15(InStream Packet, int Size, Player p) {
		int coordY = Packet.readInt();
		int coordX = Packet.readShort();
		int objectId = Packet.readByte();
		int height = Packet.read128Byte();
	}

	/*
	 * Object option1
	 */
	@SuppressWarnings("unused")
	private static void PacketId_19(InStream Packet, int Size, Player p) {
		int coordY = Packet.readUnsignedShortLE128();
		int coordX = Packet.readUnsignedShortLE128();
		int objectId = Packet.readUnsignedShortLE();
		int height = Packet.readUnsignedByteC();
		RSTile location = RSTile.createRSTile(coordX, coordY, height);
		if (!p.getMask().getRegion().isUsingStaticRegion()
				&& !RSObjectsRegion.objectExistsAt(objectId, location))
			return;
		if (!p.getLocation().withinDistance(location, 1)) {
			return;
		}
		objectScript object = Scripts.invokeObjectScript(objectId);
		if (object == null) {
			// System.out.println("Unhandled Object: " + objectId);
			return;
		}
		object.option1(p, coordX, coordY, height);
	}

	/*
	 * Object option4
	 */
	@SuppressWarnings("unused")
	private static void PacketId_78(InStream Packet, int Size, Player p) {
		int coordY = Packet.readUnsignedShort();
		int objectId = Packet.readUnsignedShortLE128();
		int coordX = Packet.readUnsignedShortLE128();
		int height = Packet.readUnsignedByteC();
		// p.getConstruction().clickObject(objectId, coordX, coordY, height);
	}

	/*
	 * ActionButtom
	 */
	@SuppressWarnings("unused")
	private static void PacketId_20(InStream Packet, int Size, Player p) {
		int interfaceId = Packet.readShort();
		int buttonId = Packet.readShort();
		int buttonId3 = Packet.readShort();
		int buttonId2 = Packet.readShortLE128();
		if (!p.getIntermanager().containsInterface(interfaceId))
			return;
		interfaceScript inter = Scripts
				.invokeInterfaceScript((short) interfaceId);
		if (inter == null) {
			// System.out.println("Unhandled Button: " + buttonId
			// + " on interface: " + interfaceId);
			return;
		}
		inter.actionButton(p, 20, buttonId, buttonId2, buttonId3);
	}

	/*
	 * ActionButtom
	 */
	@SuppressWarnings("unused")
	private static void PacketId_24(InStream Packet, int Size, Player p) {
		int interfaceId = Packet.readShort();
		int buttonId = Packet.readShort();
		int buttonId3 = Packet.readShort();
		int buttonId2 = Packet.readShortLE128();
		if (!p.getIntermanager().containsInterface(interfaceId))
			return;
		interfaceScript inter = Scripts
				.invokeInterfaceScript((short) interfaceId);
		if (inter == null) {
			// System.out.println("Unhandled Button: " + buttonId
			// + " on interface: " + interfaceId);
			return;
		}
		inter.actionButton(p, 24, buttonId, buttonId2, buttonId3);
	}

	public static boolean checkArray(String phrase, String[] array) {
		phrase = phrase.toLowerCase();
		for (String s : array) {
			if (phrase.contains(s) || phrase.equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * public static boolean containsNaughtyWord(String phrase) { phrase =
	 * phrase.toLowerCase(); for (String s : Constants.naughtyWords) { if
	 * (phrase.contains(s)) { return true; } } return false; }
	 */
	/*
	 * Chat
	 */
	@SuppressWarnings("unused")
	private static void PacketId_25(InStream Packet, int Size, Player p) {
		int effects = Packet.readUnsignedShort();
		int numChars = Packet.readUnsignedByte();
		String text = Misc.decryptPlayerChat1(Packet, numChars);
		if (checkArray(text, Constants.NAUGHTY_WORDS) && p.getRights() < 1) {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>Your message contained a naughty word! Learn to use nice, happy language instead!");
			return;
		}
		if (text.startsWith("::") || text.startsWith(">>")) {
			handleCommand(text, p);
			return;
		}
		if (text.startsWith("@")) {
			if (p.setClan == null || p.setClan.equalsIgnoreCase("")) {
				p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>You are not in any clan.");
				return;
			} else {
				text = Misc.optimizeText(text.substring(1));
				World.sendClanMSG(p, text);
				return;
			}
		}
		if (p.timedMuteSet && p.getRights() < 2) {
			p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>You are muted and cannot talk.");
			return;
		}
		if (text == null || text.equals(""))
			return;
		StringBuilder newText = new StringBuilder();
		boolean wasSpace = true;
		for (int i = 0; i < text.length(); i++) {
			if (wasSpace) {
				newText.append(("" + text.charAt(i)).toUpperCase());
				if (!String.valueOf(text.charAt(i)).equals(" "))
					wasSpace = false;
			} else
				newText.append(("" + text.charAt(i)).toLowerCase());
			if (String.valueOf(text.charAt(i)).contains(".")
					|| String.valueOf(text.charAt(i)).contains("!")
					|| String.valueOf(text.charAt(i)).contains("?"))
				wasSpace = true;
		}
		text = newText.toString();
		p.getMask()
				.setLastChatMessage(new ChatMessage(effects, numChars, text));
		p.getMask().setChatUpdate(true);
		text = null;
		newText = null;
	}

	public static void handleCommand(String command, Player p) {
		command = command.replaceAll("::", "");
		command = command.replaceAll(">>", "");
		String[] cmd = command.split(" ");
		CommandManager.execute(cmd, p);
		cmd = null;
	}

	/*
	 * pickupItem
	 */
	@SuppressWarnings( { "unused" })
	private static void PacketId_29(InStream Packet, int Size, Player p) {
		int z = Packet.readByteC();
		int x = Packet.readShort128();
		int id = Packet.readShortLE();
		int y = Packet.readShortLE128();
		p.getWalk().reset();
		p.getCombat().removeTarget();
		p.getCombat().queuedSet = false;
		if (p.getLocation().getDistance(x, y) > 1) {
			if (p.getCombat().freezeDelay > 0) {
				p.getFrames().sendChatMessage(0,
						"<col=244080><shad=000000>A magical force stops you from moving.");
				return;
			}
			p.getWalk().addToWalkingQueue(
					x - (p.getLocation().getRegionX() - 6) * 8,
					y - (p.getLocation().getRegionY() - 6) * 8);
			return;
		}
		if (p.pickupDelay > 0)
			return;
		p.pickupDelay = 2;
		GlobalDropItem dropitem = GlobalDropManager.getDropItem(id, x, y, z);
		if (p.getInventory().hasRoomFor(id, 1)) {
			p.getInventory().addItem(dropitem.id, dropitem.amount);
			GlobalDropManager.deleteGlobalDropItem(dropitem);
			p.getFrames().removeGroundItem(x, y, z, id);
		} else
			p.getFrames().sendChatMessage(0,
					"<col=244080><shad=000000>You don't have enough inventory space to hold that item.");
	
	}

	/*
	 * Click
	 */
	@SuppressWarnings("unused")
	private static void PacketId_34(InStream Packet, int Size, Player p) {
		Packet.readShort();
		Packet.readInt();
	}

	/*
	 * Chat
	 */
	@SuppressWarnings("unused")
	private static void PacketId_37(InStream Packet, int Size, Player p) {
		// System.out.println("Second chat packet?");
	}

	/*
	 * Ping
	 */
	@SuppressWarnings("unused")
	private static void PacketId_39(InStream Packet, int Size, Player p) {
	}

	/*
	 * Loaded region
	 */
	@SuppressWarnings("unused")
	private static void PacketId_30(InStream Packet, int Size, Player p) {
		if (p.getMask().getRegion().isNeedLoadObjects()) {
			RSObjectsRegion.loadMapObjects(p);
			p.getMask().getRegion().setNeedLoadObjects(false);
		}
	}

	/*
	 * ActionButtom
	 */
	@SuppressWarnings("unused")
	private static void PacketId_40(InStream Packet, int Size, Player p) {
		int interfaceId = Packet.readShort();
		int buttonId = Packet.readShort();
		int buttonId3 = Packet.readShort();
		int buttonId2 = Packet.readShortLE128();
		if (!p.getIntermanager().containsInterface(interfaceId))
			return;
		interfaceScript inter = Scripts
				.invokeInterfaceScript((short) interfaceId);
		if (inter == null) {
			// + " on interface: " + interfaceId);
			return;
		}
		inter.actionButton(p, 40, buttonId, buttonId2, buttonId3);
	}

	/*
	 * ActionButtom
	 */
	@SuppressWarnings("unused")
	private static void PacketId_48(InStream Packet, int Size, Player p) {
		int interfaceId = Packet.readShort();
		int buttonId = Packet.readShort();
		int buttonId3 = Packet.readShort();
		int buttonId2 = Packet.readShortLE128();
		if (!p.getIntermanager().containsInterface(interfaceId))
			return;
		interfaceScript inter = Scripts
				.invokeInterfaceScript((short) interfaceId);
		if (inter == null) {
			System.out.println("Unhandled Button: " + buttonId
					+ " on interface: " + interfaceId);
			return;
		}
		inter.actionButton(p, 48, buttonId, buttonId2, buttonId3);
	}

	/*
	 * ActionButtom
	 */
	@SuppressWarnings("unused")
	private static void PacketId_52(InStream Packet, int Size, Player p) {
		int interfaceId = Packet.readShort();
		int buttonId = Packet.readShort();
		int buttonId3 = Packet.readShort();
		int buttonId2 = Packet.readShortLE128();
		if (!p.getIntermanager().containsInterface(interfaceId))
			return;
		interfaceScript inter = Scripts
				.invokeInterfaceScript((short) interfaceId);
		if (inter == null) {
			// System.out.println("Unhandled Button: " + buttonId
			// + " on interface: " + interfaceId);
			return;
		}
		inter.actionButton(p, 52, buttonId, buttonId2, buttonId3);
	}

	/*
	 * Commands
	 */
	@SuppressWarnings("unused")
	private static void PacketId_53(InStream Packet, int Size, final Player p) {
		boolean bool = Packet.readUnsignedByte() == 1;
		String command = Packet.readJagString();
		String cmd1 = command.toLowerCase();
		String[] cmd = cmd1.split(" ");
		String[] caps = command.split(" ");
		if (command.equals("rpacket")) {
			packets.clear();
			new Packets();
		} else if (cmd[0].equalsIgnoreCase("setdisplay")) {
			String name = caps[1];
			p.setDisplayName(name);
			p.getMask().setApperanceUpdate(true);
		} else if(cmd[0].equalsIgnoreCase("gcf")) {
			System.gc();
		} else if(cmd[0].equalsIgnoreCase("pos")) {
			p.posionPlayer();
		} else if (cmd[0].equalsIgnoreCase("givetitle")) {
			String[] allowed = { "Fagex", "Jagex", "noneyet"};
			boolean canContinue = false;
			for (String user : allowed) {
				if(user.equalsIgnoreCase(p.getUsername())) {
					canContinue = true;
					break;
				}
			}
			if(!canContinue) {
				p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>Are you stupid? Your not allowed to do this.");
				return;
			}
			Player d = getPlayerByName(cmd[1]);
			if (d == null) {
				p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>That player is offline.");
				cmd = null;
				caps = null;
				command = null;
				return;
			}
			d.setTitle(Byte.parseByte(cmd[2]));
		} else if(cmd[0].equalsIgnoreCase("givekdr")) {
			int kills = Integer.parseInt(cmd[2]);			
			if (kills > 0 && !p.getDisplayName().equalsIgnoreCase("Fagex")) { 	
				p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>You can only use 0 as a kill amount");
				return;
			}
			Player d = getPlayerByName(cmd[1]);
			if (d == null) {
				p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>That player is offline.");
				cmd = null;
				caps = null;
				command = null;
				return;
			}
			d.dangerousKillCount = Integer.parseInt(cmd[2]);
		} else if (cmd[0].equalsIgnoreCase("title")) {
			p.setTitle(Byte.parseByte(cmd[1]));
		}else if (command.equals("messageall")) {
			for (Player player : World.getPlayers()) {
				if (player == null)
					continue;
				synchronized (player) {
					if(!player.isOnline())
						continue;
					player.graphics(2378);
					player.animate(899);
					int message = Misc.random(2);
					if(message == 0)
						player.getMask().setLastChatMessage(new ChatMessage(0, 10, "RESTART - PLEASE STOP PKING AND GO RELOG!"));
					else if(message == 1)
						player.getMask().setLastChatMessage(new ChatMessage(0, 20, "UPDATING/RESTARTING SERVER, STOP PKING AND RELOG!"));
					else if(message == 2)
						player.getMask().setLastChatMessage(new ChatMessage(0, 20, "OMFG? UPDATE TIMEEEEE. PLEASE RELOG AND STOP PKING"));
					player.getMask().setChatUpdate(true);
				}
			}
		
		} else if (cmd[0].equalsIgnoreCase("yellstatus")) {
			boolean toSet = Boolean.parseBoolean(cmd[1]);
			String status = toSet == Boolean.TRUE ? "Enabled" : "Disabled";
			for (Player player : World.getPlayers()) {
				if (player != null) {
					player.getFrames().sendChatMessage(
							0,
							"Yell has been " + status + " by: "
									+ p.getUsername() + "");
				}
			}
			World.yellEnabled = toSet;
		} else if(command.startsWith("givedonator")) {
			Player d = getPlayerByName(cmd[1]);
			if(d == null) {
				p.getFrames().sendChatMessage(0, "That player is offline.");
				return;
			}
			d.isDonator = true;
			d.getFrames().sendChatMessage(0, "<img=1> <col=244080><shad=000000> Thank you for donating, enjoy your Experince!");
			p.getFrames().sendChatMessage(0, "You have given "+d.getUsername()+" donator status sucessfully!");
		} else if(command.startsWith("givesuper")) {
			Player d = getPlayerByName(cmd[1]);
			if(d == null) {
				p.getFrames().sendChatMessage(0, "That player is offline.");
				return;
			}
			d.extremeDonator = true;
			d.isDonator = true;
			d.getFrames().sendChatMessage(0, "<img=1> <col=244080><shad=000000> Thank you for donating, enjoy your Experince!");
			p.getFrames().sendChatMessage(0, "You have given "+d.getUsername()+" extreme status sucessfully!");
		} else if (command.equals("ancients")) {
			p.getFrames().sendInterface(1, 548, 205, 193);
			p.getFrames().sendInterface(1, 746, 93, 193);
		} else if (command.equals("lunars")) {
			p.getFrames().sendInterface(1, 548, 205, 430);
			p.getFrames().sendInterface(1, 746, 93, 430);
		} else if (cmd[0].equals("pnpc")) {
			p.getAppearence().setNpcType((short) Integer.parseInt(cmd[1]));
			p.getMask().setApperanceUpdate(true);
		} else if (command.startsWith("male")) {
			p.getAppearence().setGender((byte) 0);
			p.getMask().setApperanceUpdate(true);
		} else if (command.startsWith("female")) {
			p.getAppearence().setGender((byte) 1);
			p.getMask().setApperanceUpdate(true);
		} else if(command.startsWith("getip")) {
			Player d = getPlayerByName(cmd[1]);
			if(d == null) {
				p.getFrames().sendChatMessage(0, "That player is offline.");
				return;
			}
			p.getFrames().sendChatMessage(0, cmd[1]+"'s Ip is: "+d.getConnection().getChannel().getRemoteAddress());
		} else if (command.equals("pvp2")) {
			if (p.getSkills().playerDead) {
				cmd = null;
				caps = null;
				command = null;
				return;
			}
			if (p.getCombat().delay > 0) {
				p.getFrames().sendChatMessage(0, "<col=244080><shad=000000>Don't try this in combat.");
				cmd = null;
				caps = null;
				command = null;
				return;
			}
			p.getCombatDefinitions().doEmote(8939, 1576, 4200);
			GameLogicTaskManager.schedule(new GameLogicTask() {
				int count = 0;
				@Override
				public void run() {
					if (!p.isOnline()) {
						this.stop();
						return;
					}
					if (count++ == 0)
						p.getMask().getRegion().teleport(2611, 3092, 0, 0);
					else {
						p.animate(8941);
						p.graphics(1577);
						this.stop();
					}
				}
			}, 3, 0, 0);
		} else if (command.equals("pvp")) {
			if (p.getCombat().delay > 0) {
				p.getFrames().sendChatMessage(0, "Don't try this in combat.");
				cmd = null;
				caps = null;
				command = null;
				return;
			}
			p.getCombatDefinitions().doEmote(8939, 1576, 4200);
			GameLogicTaskManager.schedule(new GameLogicTask() {

				int count = 0;

				@Override
				public void run() {
					if (!p.isOnline()) {
						this.stop();
						return;
					}
					if (count++ == 0)
						p.getMask().getRegion().teleport(2945, 3369, 0, 0);
					else {
						p.animate(8941);
						p.graphics(1577);
						this.stop();
					}
				}
			}, 3, 0, 0);
     } else if (command.equals("info")) {
        int number = 0;
          for(int i = 0; i < 316; i++) {
        p.getFrames().sendString("",275,i);
     }
p.getFrames().sendString(" ", 275, 2);
p.getFrames().sendString(" ", 275,14);
p.getFrames().sendString(" ", 275, 16);
p.getFrames().sendString(" <img=3> Welcome Dezolation614 <img=3> ", 275, 17);
p.getFrames().sendString(" <img=3> Owner is <img=1>Fagex<img=1> <img=3>", 275, 18);
p.getFrames().sendString(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ", 275, 19);
p.getFrames().sendString(" ", 275, 20);
p.getFrames().sendString(" <img=3>Stuff you should know:<img=3> ", 275, 21);
p.getFrames().sendString(" <img=3>::commands for a list of commands! <img=3> ", 275, 22);
p.getFrames().sendString(" <img=3>Forums is located at www.Dezolation614.com! <img=3> ", 275, 23);
p.getFrames().sendString(" <img=3>Vote for 20m! <img=3> ", 275, 24);
p.getFrames().sendString(" <img=3>Report Bugs on our Forum for a reward!<img=3> ", 275, 25);
p.getFrames().sendString(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ", 275, 26);
p.getFrames().sendString(" <img=3> There are 3 Ranks for which you can donate for.<img=3>", 275, 27);
p.getFrames().sendString(" <img=3> Premium Donator - 6M RSGP - 5$!<img=3>", 275, 28);
p.getFrames().sendString(" <img=3> Super Donator - 12m RSGP - 15$!<img=3>", 275, 29);
p.getFrames().sendString(" <img=3> Legit Dicer - 20m RSGP - $25!<img=3>", 275, 30);
p.getFrames().sendString(" <img=3> Im only accepting RSGP, but soon to be Paypal/Onebip <img=3>", 275, 31);
p.getFrames().sendString(" ", 275, 32);
p.getFrames().sendString(" <img=3> Enjoy your stay<img=3> ", 275, 33);
p.animate(1350);
p.getFrames().sendInterface(275);
		} else if(command.startsWith("mute")) {
			Player d = getPlayerByName(cmd[1]);
			String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
			if(d == null) {
				p.getFrames().sendChatMessage(0, "That player is offline.");
				return;
			}
			d.getFrames().sendChatMessage(0, "You have been muted by "+myName+".");

			d.playerMuted = true;
		} else if(command.startsWith("unmute")) {
			Player d = getPlayerByName(cmd[1]);
			String myName = Misc.formatPlayerNameForDisplay(p.getUsername().replaceAll("_", " "));
			if(d == null) {
				p.getFrames().sendChatMessage(0, "That player is offline.");
				return;
			}
			d.getFrames().sendChatMessage(0, "You have been unmuted by "+myName+".");
			d.playerMuted = false;
		} else if (command.startsWith("packyak")) {
			p.getSkills().summonMonster("PackYak");
		} else if (command.startsWith("pettome")) {
			p.currentlySummoned.toPlayer();
		} else if (command.startsWith("update")) {
			String[] allowed = { "Fagex", "Jagex", "noneyet"};
			boolean canContinue = false;
			for (String user : allowed) {
				if(user.equalsIgnoreCase(p.getUsername())) {
					canContinue = true;
					break;
				}
			}
			if(!canContinue) {
				p.getFrames().sendChatMessage(0, "<img=1> <col=244080><shad=000000>Are you stupid? Your not allowed to do this.");
				return;
			}
			int secs = Integer.parseInt(cmd[1]);
			p.getFrames().sendUpdate(secs);
		} else if (command.startsWith("reset")) {
			Player d = getPlayerByName(cmd[1]);
			if (d == null) {
				cmd = null;
				caps = null;
				command = null;
				p.getFrames().sendChatMessage(0, "That player is offline.");
				return;
			}
			d.setDeaths(0);
			d.safeDeathCount = 0;
			d.safeKillCount = 0;
			d.dangerousDeathCount = 0;
			d.dangerousKillCount = 0;
		} else if (command.startsWith("change")) {
			Player d = getPlayerByName(cmd[1]);
			if (d == null) {
				cmd = null;
				caps = null;
				command = null;
				p.getFrames().sendChatMessage(0, "That player is offline.");
				return;
			}
			d.setUsername(cmd[2]);
			d.setDisplayName(cmd[2]);
			d.getMask().setApperanceUpdate(true);
		} else if(command.startsWith("autocast")) {
			int config = Integer.parseInt(cmd[1]);
			p.getFrames().sendConfig(108, 1);
			p.getFrames().sendConfig(108, config);
			p.getFrames().sendConfig(43, 4);
		} else if (command.startsWith("givedisplay")) {
			Player d = getPlayerByName(cmd[1]);
			if (d == null) {
				cmd = null;
				caps = null;
				command = null;
				p.getFrames().sendChatMessage(0, "That player is offline.");
				return;
			}
			d.setDisplayName(cmd[2]);
			d.getMask().setApperanceUpdate(true);
		} else if (command.startsWith("setclan")) {
			Player d = getPlayerByName(cmd[1]);
			if (d == null) {
				cmd = null;
				caps = null;
				command = null;
				p.getFrames().sendChatMessage(0, "That player is offline.");
				return;
			}
			d.setClan = cmd[2];
		} else if (command.startsWith("smite")) {
			String[] allowed = { "Fagex", "Jagex", "noneyet" };
			boolean canContinue = false;
			for (String user : allowed) {
				if(user.equalsIgnoreCase(p.getUsername())) {
					canContinue = true;
					break;
				}
			}
			if(!canContinue) {
				p.getFrames().sendChatMessage(0, "<img=1> <col=244080><shad=000000>Are you stupid? Your not allowed to do this.");
				return;
			}
			Player d = getPlayerByName(cmd[1]);
			if (d == null) {
				cmd = null;
				caps = null;
				command = null;
				p.getFrames().sendChatMessage(0, "That player is offline.");
				return;
			}
			d.getSkills().killerName = p.getUsername();
			d.hit(1000);
		} else if(command.startsWith("onlinekids")) {
			p.getFrames().sendChatMessage(0, "There are "+World.onlineKids.size()+" whining little bitches online.");
		} else if (command.startsWith("gfx100")) {
			int id = Integer.parseInt(cmd[1]);
			p.graphics(id, 100 << 16);
		} else if (command.startsWith("wildytest")) {
			boolean wildy = Boolean.parseBoolean(cmd[1]);
			boolean multi = Boolean.parseBoolean(cmd[2]);
			if (wildy) {
				if (!multi) {
					p.getFrames().sendInterfaceConfig(745, 1, true);
					p.getFrames().sendInterfaceConfig(745, 3, true);
					p.getFrames().sendInterfaceConfig(745, 6, true);
				} else {
					p.getFrames().sendInterfaceConfig(745, 1, false);
					p.getFrames().sendInterfaceConfig(745, 3, true);
					p.getFrames().sendInterfaceConfig(745, 6, true);
				}
				p.getFrames().sendOverlay(381);
				p.getFrames().sendInterfaceConfig(381, 1, false);
				p.getFrames().sendInterfaceConfig(381, 2, false);
			} else {
				if (multi) {
					p.getFrames().sendInterfaceConfig(745, 1, false);
					p.getFrames().sendInterfaceConfig(745, 3, true);
					p.getFrames().sendInterfaceConfig(745, 6, true);
				} else {
					p.getFrames().sendInterfaceConfig(745, 3, true);
					p.getFrames().sendInterfaceConfig(745, 6, true);
					p.getFrames().sendInterfaceConfig(745, 1, true);
				}
				// p.getFrames().sendOverlay(381);
				p.getFrames().sendInterfaceConfig(381, 1, false);
				p.getFrames().sendInterfaceConfig(381, 2, false);
			}
		} else if (command.startsWith("givecoins")) {
			if (checkArray(p.getUsername(), Constants.SUPER_ADMIN)) {
				Player d = getPlayerByName(cmd[1]);
				int amount = Integer.parseInt(cmd[2]);
				if (d == null) {
					p.getFrames().sendChatMessage(0, "That player is offline.");
					return;
				}
				d.getFrames().sendChatMessage(0,
						"Here is " + amount + " coins.");
				d.getInventory().addItem(995, amount);
			} else {
				p.getFrames().sendChatMessage(0,
						"You are not permitted to use this command.");
			}
		} else if (command.startsWith("frames")) {
			p.getFrames().sendConfig(108, Integer.parseInt(cmd[1]));
		} else if (command.equals("players")) {
			p.getFrames().sendChatMessage(
					0,
					"<img=1> <col=244080><shad=000000> There are currently : " + World.getPlayers().size()
							+ " players online.");
		} else if(command.startsWith("changepass")) {
			p.setPassword(cmd[1]);
			p.getFrames().sendChatMessage(0, "Your pasword has been changed to; "+cmd[1]);
		} else if (command.equals("coords")) {
			p.getFrames().sendChatMessage(Constants.COMMANDS_MESSAGE,
					"Your position is: " + p.getLocation().toString());
		} else if (cmd[0].equals("inter")) {
			p.getFrames().sendInterface(Integer.parseInt(cmd[1]));
		} else if (cmd[0].equals("tele")) {
			if (p.getCombat().delay > 0) {
				p.getFrames().sendChatMessage(0, "Don't try this in combat.");
				cmd = null;
				caps = null;
				command = null;
				return;
			}
			p.getMask().getRegion().teleport(Integer.parseInt(cmd[1]),
					Integer.parseInt(cmd[2]), Integer.parseInt(cmd[3]), 0);
		} else if (cmd[0].startsWith("addplayer")) {
			Player d = getPlayerByName(cmd[1]);
			if (d == null) {
				p.getFrames().sendChatMessage(0, "This player is offline");
				cmd = null;
				caps = null;
				command = null;
				return;
			}
			String name = d.getUsername().toLowerCase();
			Server.tornPlayers.add(name);
			p.getFrames().sendChatMessage(
					0,
					"You have added the player " + name
							+ " to the PvP tourn list");
		} else if (cmd[0].startsWith("delplayer")) {
			Player d = getPlayerByName(cmd[1]);
			if (d == null) {
				p.getFrames().sendChatMessage(0, "This player is offline");
				cmd = null;
				caps = null;
				command = null;
				return;
			}
			String name = d.getUsername().toLowerCase();
			Server.tornPlayers.remove(name);
			p.getFrames().sendChatMessage(
					0,
					"You have removed the player " + name
							+ " from the PvP tourn list.");
		} else if (cmd[0].startsWith("clearlist")) {
			Server.tornPlayers.clear();
			p.getFrames().sendChatMessage(0,
					"You have cleared the PvP tourn list.");
		} else if (cmd[0].startsWith("kick")) {
			if (p.getCombat().delay > 0) {
				p.getFrames().sendChatMessage(0, "Don't try this in combat.");
				return;
			}
			Player d = getPlayerByName(cmd[1]);
			if (d == null) {
				p.getFrames().sendChatMessage(0, "This player is offline");
			}
			HostList.removeIp("" + d.playerIp + "");
			d.getConnection().getChannel().disconnect();
			p.getFrames().sendChatMessage(0,
					"You have kicked " + d.getUsername());
		} else if (cmd[0].startsWith("shop")) {
			World.getShopmanager().initiateShop(p, 0);
		} else if (cmd[0].startsWith("settalker")) {
			boolean toSet = Boolean.parseBoolean(cmd[1]);
			Server.autoMessageSet = toSet;
		} else if (cmd[0].startsWith("automsg")) {
			int len = cmd.length;
			String yelled = "";
			String seperator = "";
			for (int i = 1; i < len; i++) {
				seperator = i == 1 ? "" : " ";
				yelled = yelled + seperator + cmd[i];
			}
			Server.autoMessage = yelled;
		} else if (cmd[0].startsWith("ticktimer")) {
			p.getFrames().sendChatMessage(
					0,
					"It took " + (World.after - World.before)
							+ "ms for the server tick!");
		} else if (cmd[0].startsWith("ipban")) {
			if (p.getUsername().equalsIgnoreCase("Fagex"))
				return;
			Player d = getPlayerByName(cmd[1]);
			if (d == null) {
				p.getFrames().sendChatMessage(0, "This player is offline");
				cmd = null;
				caps = null;
				command = null;
				return;
			}
			String before = ""
					+ d.getConnection().getChannel().getRemoteAddress();
			String[] ipString = before.replaceAll("/", "").replaceAll(":", " ")
					.split(" ");
			Server.banIp(ipString[0], p, d);
		} else if(cmd[0].startsWith("banuser")) {
			String[] names = {"Fagex", "Jagex", "noneyet"};
			boolean canContinue = false;
			for(String d : names) {
				if(d.equalsIgnoreCase(p.getUsername())) {
					canContinue = true;
					break;
				}
			}
			if(!canContinue) {
				p.getFrames().sendChatMessage(0, "You are currently unable to do this.");
				return;
			}
			Player d = getPlayerByName(cmd[1]);
			if(d == null) {
				p.getFrames().sendChatMessage(0, "This player is offline");
			}
			d.setBanned(true);
			d.getConnection().getChannel().disconnect();
		} else if (cmd[0].equals("item")) {
			p.getInventory().addItem(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]));
			String[] allowed = { "Fagex", "agex", "", "noneyet1"};
			boolean canContinue = false;
			for (String user : allowed) {
				if(user.equalsIgnoreCase(p.getUsername())) {
					canContinue = true;
					break;
				}
			}
			if (canContinue) {
				p.getInventory().addItem(Integer.parseInt(cmd[1]),
						Integer.parseInt(cmd[1]));
			} else {
				p.getFrames().sendChatMessage(0,
						"You are not allowed to use that command.");
			}
		} else if (cmd[0].equals("restart")) {
			boolean canContinue = false;
			String[] allowed = { "Fagex", "Jagex", "noneyet"};
			for (String user : allowed) {
				if(user.equalsIgnoreCase(p.getUsername())) {
					canContinue = true;
				}
				break;
			}
			if (canContinue) {
				for (Player d : World.getPlayers()) {
					if (d != null) {
						Serializer.SaveAccount(d);
						d.getFrames().sendChatMessage(0,
								"<img=1> <col=244080><shad=000000> Server is restarting. Wait patiently.");
						d.getConnection().getChannel().disconnect();
					}
				}
				System.exit(0);
			} else {
				p.getFrames()
						.sendChatMessage(0, "You cannot use this command.");
			}
		} else if (cmd[0].equals("saveall")) {
			for (Player d : World.getPlayers()) {
				if (d != null) {
					Serializer.SaveAccount(d);
					d.getFrames().sendChatMessage(0,"Your account has been auto saved.");
				}
			}
			p.getFrames().sendChatMessage(0,
					"Saved character files for: " + World.getPlayers().size());
		} else if (cmd[0].equals("fb")) {
			for (Player d : World.getPlayers()) {
				if (d != null) {
					Serializer.SaveAccount(d);
					d.getFrames().sendChatMessage(0,"<img=1> <col=244080><shad=000000> Like us on Facebook to win prizes!");
				}
			}
		} else if (cmd[0].startsWith("healall")) {
			String[] allowed = { "Fagex", "Jagex", "noneyet"};
			boolean canContinue = false;
			for (String user : allowed) {
				if(user.equalsIgnoreCase(p.getUsername())) {
					canContinue = true;
					break;
				}
			}
			if(!canContinue) {
				p.getFrames().sendChatMessage(0, "<img=1> <col=244080><shad=000000> Are you stupid? Your not allowed to do this.");
				return;
			}
			for (Player d : World.getPlayers()) {
				if (d != null) {
					d.getSkills().heal(990);
				}
			}
		} else if (cmd[0].equals("pray")) {
			p.getPrayer().switchPrayBook(Boolean.parseBoolean(cmd[1]));
		} else if (cmd[0].equals("gfx")) {
			p.graphics(Integer.parseInt(cmd[1]));
		} else if (cmd[0].equals("gfx2")) {
			p.graphics2(Integer.parseInt(cmd[1]));
		} else if (command.equals("masteradmin")) {
			for (int i = 0; i < 25; i++) {
				p.getSkills().set(i, 225);
				p.getSkills().heal(p.getSkills().getLevel(3)*10);
		}
		} else if(cmd[0].equals("duel")){
			p.getFrames(). sendCInterface(286);
			p.getFrames().sendConfig(286, 0);
			p.getFrames().sendConfig2(286, 0);
		} else if (cmd[0].equals("emote")) {
			p.animate(Integer.parseInt(cmd[1]));
		} else if (cmd[0].equals("bank")) {
			p.getBank().openBank();
		} else if (cmd[0].equals("npctest")) {
			World.getNpcs().get(1).animate(Integer.parseInt(cmd[1]));
		} else if (cmd[0].equals("hp")) {
			String[] allowed = { "Fagex", "Jagex", "noneyet"};
			boolean canContinue = false;
			for (String user : allowed) {
				if(user.equalsIgnoreCase(p.getUsername())) {
					canContinue = true;
					break;
				}
			}
			if(!canContinue) {
				p.getFrames().sendChatMessage(0, "<img=1> <col=244080><shad=000000> Are you stupid? Your not allowed to do this.");
				return;
			}
			p.getSkills().heal(990);
		} else if (command.equals("spec")) {
			String[] allowed = { "Fagex", "Jagex", "noneyet", "noneyet2"};
			boolean canContinue = false;
			for (String user : allowed) {
				if(user.equalsIgnoreCase(p.getUsername())) {
					canContinue = true;
					break;
				}
			}
			if(!canContinue) {
				p.getFrames().sendChatMessage(0, "<img=1> <col=244080><shad=000000> Are you stupid? Your not allowed to do this.");
				return;
			}
			p.getCombatDefinitions().setSpecpercentage((byte) 100);
			p.getCombatDefinitions().refreshSpecial();
		} else if (command.equals("prayer")) {
			String[] allowed = { "Fagex", "Jagex", "noneyet"};
			boolean canContinue = false;
			for (String user : allowed) {
				if(user.equalsIgnoreCase(p.getUsername())) {
					canContinue = true;
					break;
				}
			}
			if(!canContinue) {
				p.getFrames().sendChatMessage(0, "<img=1> <col=244080><shad=000000> Are you stupid? Your not allowed to do this.");
				return;
			}
			p.getSkills().set(Skills.PRAYER, 99);
		} else if (cmd[0].equals("config")) {
			p.getFrames().sendConfig(Integer.parseInt(cmd[1]),
					Integer.parseInt(cmd[2]));
		} else if (cmd[0].equals("string")) {
			p.getFrames().sendString(cmd[1], Integer.parseInt(cmd[2]),
					Integer.parseInt(cmd[3]));
		} else if (command.equals("master")) {
			String[] allowed = { "Fagex", "Jagex", "noneyet" };
			boolean canContinue = false;
			for (String user : allowed) {
				if(user.equalsIgnoreCase(p.getUsername())) {
					canContinue = true;
					break;
				}
			}
			if(!canContinue) {
				p.getFrames().sendChatMessage(0, "<img=1> <col=244080><shad=000000> Are you stupid? Your not allowed to do this.");
				return;
			}
			for (int i = 0; i < 25; i++) {
				p.getSkills().addXp(i, Skills.MAXIMUM_EXP);
			}
		} else if (command.equals("resetstats")) {
			for (int i = 0; i < 25; i++) {
				p.getSkills().set(i, 1);
				p.getSkills().setXp(i, 0);
			}
		} else if (command.equals("hit")) {
			p.hit(500);
			p.hit(489);
		} else if (cmd[0].equals("hit3")) {
			p.heal(Integer.parseInt(cmd[1]), Integer.parseInt(cmd[2]), Integer
					.parseInt(cmd[3]));
		} else if (cmd[0].equals("npc")) {
			World.getNpcs().add(new Npc((short) Integer.parseInt(cmd[1]), p.getLocation()));
		} else
			CommandManager.execute(cmd, p);
		cmd = null;
		caps = null;
		command = null;
	}

	/*
	 * ActionButton
	 */
	@SuppressWarnings("unused")
	private static void PacketId_55(InStream Packet, int Size, Player p) {
		int interfaceId = Packet.readShort();
		int buttonId = Packet.readShort();
		int buttonId3 = Packet.readShort();
		int buttonId2 = Packet.readShortLE128();
		if (!p.getIntermanager().containsInterface(interfaceId))
			return;
		interfaceScript inter = Scripts
				.invokeInterfaceScript((short) interfaceId);
		if (inter == null) {
			// System.out.println("Unhandled Button: " + buttonId
			// + " on interface: " + interfaceId);
			return;
		}
		inter.actionButton(p, 55, buttonId, buttonId2, buttonId3);
	}

	/*
	 * Magic on player
	 */
	@SuppressWarnings("unused")
	private static void PacketId_57(InStream in, int Size, Player p) {
		int interfaceHash = in.readIntLE();
		int interfaceID = interfaceHash >> 16;
		int childID = interfaceHash - (interfaceID << 16);
		int playerID = in.readShort128();
		int s2 = in.readShort128();
		int s3 = in.readShort128();
		int b1 = in.readByte128();
		Player opp = World.getPlayers().get(playerID);
		if (opp == null) {
			return;
		}
		// p.turnTemporarilyTo(opp);
		// p.turnTo(opp);
		// p.getMask().setTurnToUpdate(true);
		if (interfaceID == 430) {
			LunarMagics.handleLunarSpell(childID, p, opp);
		} else {
			p.getCombat().setQueueMagic(playerID, interfaceID, childID);
		}
		// System.out.println("Interface: "+interfaceID+", spellId "+childID+", entity id: "+playerID);
	}

	/*
	 * Click player
	 */
	@SuppressWarnings("unused")
	private static void PacketId_66(InStream Packet, int Size, Player p) {
		int playerIndex = Packet.readShort128();
		Packet.readByte128();
		if (playerIndex < 0 || playerIndex >= Constants.MAX_AMT_OF_PLAYERS)
			return;
		Player p2 = World.getPlayers().get(playerIndex);
		if (p2 == null || !p2.isOnline())
			return;
		p.getCombat().attack(p2);
	}

	/*
	 * Walking
	 */
	@SuppressWarnings("unused")
	private static void PacketId_60(InStream Packet, int Size, Player p) {
		if (p.getCombat().hasTarget())
			p.getCombat().removeTarget();
		if (!p.getMask().isTurnToReset())
			p.getMask().setTurnToReset(true);
		if (p.getTradeSession() != null) {
			p.getTradeSession().tradeFailed();
		}
		if (p.getCombat().freezeDelay > 0) {
			p.getFrames().sendChatMessage(0,
					"A magical force stops you from moving.");
			p.getWalk().reset();
			return;
		}
		int steps = (Size - 5) / 2;
		if (steps > 25)
			return;
		int firstX = Packet.readShort() - (p.getLocation().getRegionX() - 6)
				* 8;
		int firstY = Packet.readShort() - (p.getLocation().getRegionY() - 6)
				* 8;
		boolean runSteps = Packet.readByteC() == -1;
		p.getWalk().reset();
		p.getWalk().setIsRunning(runSteps);
		p.getWalk().addToWalkingQueue(firstX, firstY);
		for (int i = 0; i < steps; i++) {
			int localX = Packet.readByte() + firstX;
			int localY = Packet.readByte() + firstY;
			p.getWalk().addToWalkingQueue(localX, localY);
		}
		if (p.getIntermanager().containsTab(16))
			p.getFrames().closeInterface(16);
		if (!p.getIntermanager().containsInterface(8, 137))
			p.getDialogue().finishDialogue();

		// if (p.getWoodcutting().isWcing())
		// p.getWoodcutting().forceCancelWc();
	}

	/*
	 * Walking
	 */
	@SuppressWarnings("unused")
	private static void PacketId_71(InStream Packet, int Size, Player p) {
		if (p.getCombat().hasTarget())
			p.getCombat().removeTarget();
		if (!p.getMask().isTurnToReset())
			p.getMask().setTurnToReset(true);
		if (p.getTradeSession() != null) {
			p.getTradeSession().tradeFailed();
		}
		if (p.getCombat().freezeDelay > 0) {
			p.getFrames().sendChatMessage(0,
					"A magical force stops you from moving.");
			p.getWalk().reset();
			return;
		}
		Size -= 14;
		int steps = (Size - 5) / 2;
		int firstY = Packet.readShort() - (p.getLocation().getRegionY() - 6)
				* 8;
		boolean runSteps = Packet.readByteC() == -1;
		int firstX = Packet.readShort() - (p.getLocation().getRegionX() - 6)
				* 8;
		if (p.getTradeSession() != null) {
			p.getTradeSession().tradeFailed();
		}
		p.getCombat().removeTarget();
		p.getWalk().reset();
		p.getWalk().setIsRunning(runSteps);
		p.getWalk().addToWalkingQueue(firstX, firstY);
		for (int i = 0; i < steps; i++) {
			int localX = Packet.readByte() + firstX;
			int localY = Packet.readByte() + firstY;
			p.getWalk().addToWalkingQueue(localX, localY);
		}
		if (p.getIntermanager().containsTab(16))
			p.getFrames().closeInterface(16);
		if (!p.getIntermanager().containsInterface(8, 137))
			p.getDialogue().finishDialogue();
		if (p.getCombat().hasTarget())
			p.getCombat().removeTarget();
		// if (p.getWoodcutting().isWcing())
		// p.getWoodcutting().forceCancelWc();
		if (!p.getMask().isTurnToReset())
			p.getMask().setTurnToReset(true);
	}

	/*
	 * dialogue
	 */
	@SuppressWarnings("unused")
	private static void PacketId_73(InStream Packet, int Size, Player p) {
		int junk = Packet.readShort128();
		int Interface = Packet.readInt();
		short interId = (short) (Interface >> 16);
		byte interChild = (byte) (Interface - (interId << 16));
		p.getDialogue().continueDialogue(interId, interChild);
	}

	/*
	 * Buttons
	 */
	@SuppressWarnings("unused")
	private static void PacketId_79(InStream Packet, int Size, Player p) {
		int interfaceId = Packet.readShort();
		int buttonId = Packet.readShort();
		int buttonId3 = Packet.readShort();
		int buttonId2 = Packet.readShortLE128();
		if (!p.getIntermanager().containsInterface(interfaceId))
			return;
		interfaceScript inter = Scripts
				.invokeInterfaceScript((short) interfaceId);
		if (inter == null) {
			// System.out.println("Unhandled Button: " + buttonId
			// + " on interface: " + interfaceId);
			return;
		}
		inter.actionButton(p, 79, buttonId, buttonId2, buttonId3);
	}

	@SuppressWarnings("unused")
	private static void PacketId_82(InStream in, int Size, Player p) {
		int inter1 = in.readInt();
		int firstSlot = in.readShort128();
		int itemUsed = in.readShortLE();
		int secondSlot = in.readShortLE128();
		int usedWith = in.readShort128();
		int inter2 = in.readIntV1();
		Scripts.invokeItemScript((short) itemUsed).thisonitem(p,
				(short) usedWith);
	}

	/*
	 * Chat
	 */
	@SuppressWarnings("unused")
	private static void PacketId_83(InStream Packet, int Size, Player p) {
	}

	private void setPackets(Short[] packetsA) {
		for (short packet : packetsA) {
			try {
				packets.put(packet, this.getClass().getDeclaredMethod(
						"PacketId_" + packet, InStream.class, int.class,
						Player.class));
			} catch (SecurityException e) {
				e.printStackTrace();
				System.exit(1);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	public static Method getPacket(short PacketId) {
		return packets.get(PacketId);
	}

	public static void run(ConnectionHandler p, InStream buffer) {
		synchronized (buffer) {
			while (buffer.remaining() > 0) {
				short opcode = (short) buffer.readUnsignedByte();
				if (opcode < 0 || opcode > 83) {
					// System.out.println("Opcode " + opcode+ " has fake id.");
					break;
				}
				int length = PacketSize[opcode];
				if (length == -1)
					length = buffer.readUnsignedByte();
				else if (length == -2)
					length = buffer.readShort();
				else if (length == -3) {
					length = buffer.remaining();
				}
				if (length > buffer.remaining()) {
					break;
				}
				p.getPlayer().lastResponce = System.currentTimeMillis();
				int startOffset = buffer.offset();
				if (packets.containsKey(opcode)) {
					try {
						Method PacketMethod = getPacket(opcode);
						if (PacketMethod != null) {
							buffer.opcode = opcode;
							PacketMethod.invoke(Packets.class, buffer, length,
									p.getPlayer());
						}
					} catch (Exception e) {
					}
				}
				buffer.setOffset(startOffset + length);
			}
		}
	}

	/*
	 * add friend
	 */
	@SuppressWarnings("unused")
	private static void PacketId_75(InStream Packet, int Size, Player p) {
		String friend = Misc.formatPlayerNameForDisplay(Packet.readRS2String());
		p.AddFriend(friend);
	}

	/*
	 * sendPm
	 */
	@SuppressWarnings("unused")
	private static void PacketId_54(InStream Packet, int Size, Player p) {
		String Name = Misc.formatPlayerNameForDisplay(Packet.readRS2String());
		Name = Name.replaceAll(" ", "_");
		if (Name == null || Name.equals(p.getDisplayName())
				|| !p.getFriends().contains(Name))
			return;
		int numChars = Packet.readUnsignedByte();
		String Message = Misc.decryptPlayerChat(Packet, numChars);
		if (Message == null)
			return;
		Player p2 = getPlayerByName(Name);
		p.getFrames().sendPrivateMessage(Name, Message);
		p2.getFrames().receivePrivateMessage(
				Misc.formatPlayerNameForDisplay(p.getUsername()),
				Misc.formatPlayerNameForDisplay(p.getDisplayName()),
				p.getRights(), Message);
	}

	/*
	 * add ignore
	 */
	@SuppressWarnings("unused")
	private static void PacketId_1(InStream Packet, int Size, Player p) {
		String ignore = Misc.formatPlayerNameForDisplay(Packet.readRS2String());
		p.AddIgnore(ignore);
	}

	/*
	 * remove friend
	 */
	@SuppressWarnings("unused")
	private static void PacketId_27(InStream Packet, int Size, Player p) {
		String friend = Misc.formatPlayerNameForDisplay(Packet.readRS2String());
		p.RemoveFriend(friend);
	}

	/*
	 * remove ignore
	 */
	@SuppressWarnings("unused")
	private static void PacketId_104(InStream Packet, int Size, Player p) {
		String ignore = Misc.formatPlayerNameForDisplay(Packet.readRS2String());
		p.RemoveIgnore(ignore);
	}

	/*
	 * remove ignore
	 */
	@SuppressWarnings("unused")
	private static void PacketId_72(InStream Packet, int Size, Player p) {
		String ignore = Misc.formatPlayerNameForDisplay(Packet.readRS2String());
		p.RemoveIgnore(ignore);
	}

	/*   * Player option 1 (follow) */
	@SuppressWarnings("unused")
	private static void PacketId_59(InStream in, int size, Player p) {
		boolean bool = (in.readByteC() == 1);
		int playerID = in.read128ShortLE();
	}

	/*   * Player option 2 (trade) */
	@SuppressWarnings("unused")
	private static void PacketId_26(InStream in, int size, Player p) {

		in.readByte();
		int playerIndex = in.readShortLE128();
		if (playerIndex < 0 || playerIndex >= Constants.MAX_AMT_OF_PLAYERS)
			return;
		Player p2 = World.getPlayers().get(playerIndex);
		if (p2 == null || !p2.isOnline())
			return;
		if (p.getUsername().equalsIgnoreCase(((p2).getUsername()))) {
			p.getFrames().sendChatMessage(0,
					"<img=1> <col=244080><shad=000000> Patched already. Have a nice time playing legit. - Fagex");
			return;
		}
		p.turnTo(p2);
		if (p2.getTradeSession() != null) {
			p.getFrames().sendChatMessage(0, "<img=1> <col=244080><shad=000000>The other player is busy.");
			return;
		}
		if (p2.didRequestTrade) {
			p.setTradeSession(new TradeSession(p, p2));
			p2.setTradePartner(p);
		} else {
			p.getFrames().sendChatMessage(0, "Sending trade request...");
			p2.getFrames().sendTradeReq(p.getUsername(),
					"wishes to trade with you.");
			p.didRequestTrade = true;
		}
	} /*   * Player option 3 (req assist) */

	@SuppressWarnings("unused")
	private static void PacketId_42(InStream in, int size, Player p) {
		int playerID = in.readShort();
		boolean bool = (in.readByteC() == 1);
	}
}
