package dragonkk.rs2rsps.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import dragonkk.rs2rsps.HostList;
import dragonkk.rs2rsps.Server;
import dragonkk.rs2rsps.event.managment.Event;
import dragonkk.rs2rsps.event.managment.EventManager;
import dragonkk.rs2rsps.event.managment.impl.ServerTickEvent;
import dragonkk.rs2rsps.event.managment.impl.ServerUpTimeEvent;
import dragonkk.rs2rsps.event.tickable.TickableManager;
import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.events.Task;
import dragonkk.rs2rsps.model.npc.Npc;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.model.player.clan.ClanManager;
import dragonkk.rs2rsps.model.shops.ShopManager;
import dragonkk.rs2rsps.net.codec.ConnectionHandler;
import dragonkk.rs2rsps.util.Constants;
import dragonkk.rs2rsps.util.EntityList;
import dragonkk.rs2rsps.util.Logger;
import dragonkk.rs2rsps.util.Misc;
import dragonkk.rs2rsps.util.RSTile;
import dragonkk.rs2rsps.util.Serializer;

public class World {

	private static EntityList<Player> players;
	private static EntityList<Npc> npcs;
	private static HashMap<Integer, Long> ips;
	private static ShopManager shopmanager;
	private static GlobalDropManager globaldropmanager;
	public static boolean yellEnabled = true;
	public static boolean[] playerUpdates = new boolean[2048];
	public static EventManager eventManager;
	private static TickableManager tickableManager;
	public static int updateSecs = 0;
	public static long before, after;
	public static List<String> onlineKids = new ArrayList<String>();

	public static void startUpdate(int seconds) {
		updateSecs = seconds;
		Server.getWorldExecutor().schedule(new Task() {
			@Override
			public void run() {
				updateSecs--;
				if (updateSecs < 1) {
					for (Player d : getPlayers()) {
						if (d != null) {
							Serializer.SaveAccount(d);
							d.getFrames().sendChatMessage(0,
									"Your account has been auto saved.");
						}
					}
					try {
						Thread.sleep(10000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.exit(-1);
				}
			}

		}, 1000, 1000);
	}

	public static void sendClanMSG(Player send, String msg) {
		String clan = send.setClan;
		String name = Misc.formatPlayerNameForDisplay(send.getDisplayName());
		String Text = ("<col=800000>") + "[" + name + "]: " + msg;
		for (Player curr : getPlayers()) {
			if (curr == null)
				continue;
			if (curr.setClan == null)
				continue;
			if (curr.setClan.equalsIgnoreCase(""))
				continue;
			if (!curr.setClan.equalsIgnoreCase(clan))
				continue;
			curr.getFrames().sendChatMessage(0, Text);

		}
	}

	public static int hours, mins, seconds;

	public static void processUpTime() {
		seconds++;
		if (seconds == 60) {
			mins++;
			seconds = 0;
			if (mins == 60) {
				hours++;
				mins = 0;
			}
		}
	}

	// private static RegionManager regionManager;

	public static EntityList<Player> getPlayers() {
		synchronized (players) {
			return players;
		}
	}

	public World() {
		clanManager = new ClanManager();
		eventManager = new EventManager();
		setTickableManager(new TickableManager());
		shopmanager = new ShopManager();
		globaldropmanager = new GlobalDropManager();
		players = new EntityList<Player>(Constants.MAX_AMT_OF_PLAYERS);
		npcs = new EntityList<Npc>(Constants.MAX_AMT_OF_NPCS);
		ips = new HashMap<Integer, Long>();
		npcs.add(new Npc((short) 0, RSTile.createRSTile(3220, 3222, 0)));
		calendar = new GregorianCalendar();
		submitGlobalEvents();
		// regionManager = new RegionManager();
	}

	public static ClanManager clanManager;

	/**
	 * Submits a new event.
	 * 
	 * @param event
	 *            The event to submit.
	 */
	public void submit(Event event) {
		eventManager.submit(event);
	}

	public void submitGlobalEvents() {
		submit(new ServerTickEvent(600));
		submit(new ServerUpTimeEvent(1000));
	}

	public static Calendar calendar;

	public static void registerConnection(ConnectionHandler p) {
		Server.onlinePlayers.add(p.getPlayer().getUsername());
		if (players.add(p.getPlayer())) {
			Logger.log(
					"WorldController",
					"Player "
							+ Misc.formatPlayerNameForDisplay(p.getPlayer()
									.getUsername()) + " registered.");

			p.getPlayer().LoadPlayer(p);
		}
	}

	public static void unRegisterConnection(final ConnectionHandler p) {
		final Player player = p.getPlayer();
		if (player == null) {
			p.getChannel().close();
			return;
		}
		if (player.getTradeSession() != null) {
			player.getTradeSession().tradeFailed();
		}
		if (player.getSkills().xLogProtection
				|| player.getSkills().getHitPoints() < 1
				|| player.getCombat().combatWithDelay > 0
				|| player.getCombat().delay > 0) {
			GameLogicTaskManager.schedule(new GameLogicTask() {

				@Override
				public void run() {
					removePlayer(player);
					this.stop();

				}

			}, 60, 0);
		} else {
			removePlayer(player);
		}
	}

	public static void removePlayer(Player p) {
		if (p.currentlySummoned != null) {
			World.getNpcs().remove(p.currentlySummoned.npc);
			p.currentlySummoned = null;
		}
		Serializer.SaveAccount(p);
		World.onlineKids.remove(p.getConnection().getName());
		Server.onlinePlayers.remove(p.getUsername());
		HostList.removeIp("" + p.playerIp + "");
		p.setOnline(false);
		players.remove(p);
		for (Player p2 : players) {
			if (p2.getFriends().contains(
					Misc.formatPlayerNameForDisplay(p.getUsername()))) {
				p2.UpdateFriendStatus(
						Misc.formatPlayerNameForDisplay(p.getUsername()),
						(short) 0, false);
			}
		}
		if (p.getConnection().getChannel() != null
				|| !p.getConnection().isDisconnected()) {
			p.getConnection().getChannel().close();
		}
		p = null;
	}

	public static boolean isOnline(String Username) {
		for (Player p : getPlayers())
			if (p.getUsername().equalsIgnoreCase(Username))
				if (p.isOnline())
					return true;

		return false;
	}

	public static boolean isOnList(String Username) {
		for (Player p : players)
			if (p.getUsername().equals(Username))
				return true;

		return false;
	}

	public static boolean isOnList(Player player) {
		return players.contains(player);
	}

	public static EntityList<Npc> getNpcs() {
		synchronized (npcs) {
			return npcs;
		}
	}

	public static HashMap<Integer, Long> getIps() {
		synchronized (ips) {
			return ips;
		}
	}

	/**
	 * @param shopmanager
	 *            the shopmanager to set
	 */
	public static void setShopmanager(ShopManager shopmanager) {
		World.shopmanager = shopmanager;
	}

	/**
	 * @return the shopmanager
	 */
	public static ShopManager getShopmanager() {
		return shopmanager;
	}

	public static void setGlobaldropmanager(GlobalDropManager globaldropmanager) {
		World.globaldropmanager = globaldropmanager;
	}

	public static GlobalDropManager getGlobaldropmanager() {
		return globaldropmanager;
	}

	public static void setTickableManager(TickableManager tickableManager) {
		World.tickableManager = tickableManager;
	}

	public static TickableManager getTickableManager() {
		return tickableManager;
	}

	/*
	 * public static RegionManager getRegionManager() { return regionManager; }
	 */

	/*
	 * p.packetQueue.clear(); if (p.getPlayer() != null) {
	 * 
	 * if (p.getPlayer().getCombat().getLastAttackedTime() != 0 &&
	 * System.currentTimeMillis() -
	 * p.getPlayer().getCombat().getLastAttackedTime() < 10000 &&
	 * p.getPlayer().getCombat().combatWithDelay > 0) {
	 * Server.getEntityExecutor().scheduleAtFixedRate(new Task() {
	 * 
	 * @Override public void run() { if
	 * (p.getPlayer().getCombat().getLastAttackedTime() != 0 &&
	 * System.currentTimeMillis() - p.getPlayer().getCombat()
	 * .getLastAttackedTime() < 10000) return; if
	 * (p.getPlayer().getCombat().combatWithDelay > 0) return;
	 * Serializer.SaveAccount(p.getPlayer()); try {
	 * DatabaseFunctions.updatePk(p.getPlayer()); } catch (Exception e) {
	 * System.out.println("[SQL] Error updating pk points for " +
	 * p.getPlayer().getUsername()); } p.getPlayer().setOnline(false);
	 * players.remove(p.getPlayer());
	 * //DatabaseFunctions.updatePlayers(getPlayers().size());
	 * 
	 * for (Player p2 : players) if (p2.getFriends().contains(
	 * Misc.formatPlayerNameForDisplay(p .getPlayer().getUsername())))
	 * p2.UpdateFriendStatus(Misc .formatPlayerNameForDisplay(p
	 * .getPlayer().getUsername()), (short) 0, false);
	 * 
	 * this.stop(); }
	 * 
	 * }, 0, 1000); } else { try { DatabaseFunctions.updatePk(p.getPlayer());
	 * DatabaseFunctions.updatePlayers(getPlayers().size()); } catch (Exception
	 * e) { System.out.println("[SQL] Error updating pk points for " +
	 * p.getPlayer().getUsername()); } Serializer.SaveAccount(p.getPlayer());
	 * HostList.removeIp(""+p.getPlayer().playerIp+"");
	 * p.getPlayer().setOnline(false); players.remove(p.getPlayer()); }
	 * //DatabaseFunctions.updatePlayers(getPlayers().size()); }
	 */

}
