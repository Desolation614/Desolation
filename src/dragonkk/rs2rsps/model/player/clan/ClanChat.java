package dragonkk.rs2rsps.model.player.clan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.Misc;

/**
 * ClanChat.java
 * Author Lay and JB
 */
public class ClanChat implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8283321486458136853L;

	/**
	 * Constructor
	 */
	public ClanChat(Player p) {
		setOwner(p);
		setRoomName(Misc.formatPlayerNameForDisplay(p.getUsername()));
		setMembers(new ArrayList<Player>(200));
	}
	
	public void addPlayer(Player member) {
		getMembers().add(member);
		refreshClanAll();
	}
	
	public void removePlayer(Player member) {
		getMembers().remove(member);
		refreshClanAll();
	}
	
	public void refreshClanAll() {
		try {
			for(Player p : getMembers()) {
				if(p == null)
					continue;
				ClanOutboundPackets.sendClanList(p, this);
			}
		} catch(Exception e) {
			
		}
	}
	
	public void sendMessage(Player p, String msg) {
		for(Player member : getMembers()) {
			if(p.getIndex() == member.getIndex())
				continue;
			ClanChatMessagePacket.sendClanChatMessage(p, member, this.getRoomName(), this.getOwner().getUsername(), msg);
		}
		ClanChatMessagePacket.sendClanChatMessage(p, null, this.getRoomName(), this.getOwner().getUsername(), msg);
	}
	
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
	}

	/**
	 * @return the owner
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * @param roomName the roomName to set
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	/**
	 * @return the roomName
	 */
	public String getRoomName() {
		return roomName;
	}

	/**
	 * @param members the members to set
	 */
	public void setMembers(List<Player> members) {
		this.members = members;
	}

	/**
	 * @return the members
	 */
	public List<Player> getMembers() {
		return members;
	}

	private Player owner;
	
	private String roomName;
	
	private List<Player> members;

}
