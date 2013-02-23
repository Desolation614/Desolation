package dragonkk.rs2rsps.model.player.clan;

import dragonkk.rs2rsps.io.OutStream;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.Misc;

public class ClanOutboundPackets {

	/*
	public static void sendClanList(Player p, ClanChat clan) {
		OutStream bldr = new OutStream();
		bldr.writePacketVarShort(23);

		bldr.writeString(Misc.formatPlayerNameForDisplay(clan.getOwner()
				.getUsername()));
		bldr.writeByte(0);
		bldr.writeLong(Misc.stringToLong(clan.getRoomName()));
		bldr.writeByte(7);
		bldr.writeByte(clan.getMembers().size() + 1);
		if (clan != null) {
			for (Player pl : clan.getMembers()) {
				if (p == null)
					continue;
				bldr.writeString(Misc.formatPlayerNameForDisplay(pl
						.getUsername()));
				bldr.writeByte(0); // Need to figure this out
				bldr.writeString("");
				bldr.writeShort(p.getUsername().equalsIgnoreCase(
						clan.getOwner().getUsername()) ? 6 : 6); // idk tbh ;s
				bldr.writeByte(0);
				bldr.writeString("Legacy");
			}
		}
		bldr.endPacketVarShort();
		p.getConnection().write(bldr);
	}
	*/

	public static void sendClanList(Player p, ClanChat clan) {
		OutStream spb = new OutStream();
		spb.writePacketVarShort(23);
		if(clan != null) {
		spb.writeString(Misc.formatPlayerNameForDisplay(clan.getOwner()
				.getDisplayName()));
		spb.writeByte((byte) 0);
		spb.writeLong(Misc.stringToLong(clan.getRoomName()));
		spb.writeByte((byte) 6); // KICK REQ
		spb.writeByte((byte) clan.getMembers().size());
		
			for (Player pl : clan.getMembers()) {
				spb.writeByte((byte) 1);
				spb.writeString(Misc.formatPlayerNameForDisplay(pl
						.getUsername().replaceAll("_", " ").trim()));
				spb.writeShort(1); //
				spb.writeByte((byte) 0);
				spb.writeByte(clan.getOwner().getUsername().equalsIgnoreCase(pl.getUsername()) ? 7 : -1); // rank
				spb.writeString("Legacy614");
			}
		}
		spb.endPacketVarShort();
		p.getConnection().write(spb);
	}

}
