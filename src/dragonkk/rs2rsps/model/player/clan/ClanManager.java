package dragonkk.rs2rsps.model.player.clan;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.Logger;
import dragonkk.rs2rsps.util.Misc;
import dragonkk.rs2rsps.util.Serializer;

public class ClanManager {
	
	@SuppressWarnings("unchecked")
	public ClanManager() {
		if(!clanChatsFile.exists()) {
			Logger.log("ClanManager", "Clan files don't exist. Creating a new one.");
			setRegisteredClans(new HashMap<String, ClanChat>());
			Serializer.saveBanned(clanChatsFile, getRegisteredClans());
		} else {
			try {
				registeredClans = (HashMap<String, ClanChat>) Serializer.load(clanChatsFile);
				Logger.log("ClanManager", "Loaded "+registeredClans.size()+" clans from file.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public File clanChatsFile = new File("Data/clans.dat");
	
	public void joinClan(final Player p, String owner) {
		final ClanChat channel = getRegisteredClans().get(owner.toLowerCase().replaceAll(" ", "_"));
		p.getFrames().sendChatMessage(0, "Attempting to join channel...");
		if(channel == null) {
			GameLogicTaskManager.schedule(new GameLogicTask() {

				@Override
				public void run() {
					p.getFrames().sendChatMessage(0, "The channel you tried to join does not exist.");
					this.stop();
				}
			}, 1, 0);
		} else {
			GameLogicTaskManager.schedule(new GameLogicTask() {
				@Override
				public void run() {
					p.getFrames().sendChatMessage(0, "Now talking in clan chat channel "+Misc.formatPlayerNameForDisplay(channel.getRoomName()));
					p.getFrames().sendChatMessage(0, "To talk, start each line the @ symbol.");
					channel.addPlayer(p);
					p.currentlyTalkingIn = channel;
					this.stop();
				}
			}, 1, 0);
		}
	}
	
	public void registerClanChat(ClanChat clan, String owner) {
		getRegisteredClans().put(owner.toLowerCase(), clan);
		Serializer.saveBanned(clanChatsFile, getRegisteredClans());
		Logger.log(this, "Registered new clan chat: "+owner);
	}
	
	public void leaveClan(Player p, ClanChat clan) {
		clan.removePlayer(p);
		p.currentlyTalkingIn = null;
		p.getFrames().sendChatMessage(0, "You have left the clan.");
	}
	
	public void removePlayerTemp(Player p, ClanChat clan) {
		clan.removePlayer(p);
	}
	
	/**
	 * @param registeredClans the registeredClans to set
	 */
	public void setRegisteredClans(HashMap<String, ClanChat> registeredClans) {
		this.registeredClans = registeredClans;
	}

	/**
	 * @return the registeredClans
	 */
	public HashMap<String, ClanChat> getRegisteredClans() {
		return registeredClans;
	}

	private HashMap<String, ClanChat> registeredClans;

}
