package dragonkk.rs2rsps;

import java.io.File;
import java.util.*;

import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.events.TaskManager;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.minigames.Castlewars;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.CommandManager;
import dragonkk.rs2rsps.net.Packets;
import dragonkk.rs2rsps.net.ServerChannelHandler;
import dragonkk.rs2rsps.rscache.Cache;
import dragonkk.rs2rsps.skills.combat.MagicManager;
import dragonkk.rs2rsps.util.Constants;
import dragonkk.rs2rsps.util.Logger;
import dragonkk.rs2rsps.util.MapData;
import dragonkk.rs2rsps.util.Serializer;
import dragonkk.rs2rsps.util.save.SaveTaskManager;

public class Server {

	//public static vBulletin vb = new vBulletin();
	public static boolean yellEnabled = true;
	private static GameEngine engine;
	private static final TaskManager entityExecutor = new TaskManager();
	private static final TaskManager worldExecutor = new TaskManager();
	private static final SaveTaskManager saveTaskManager = new SaveTaskManager();
	private static final Timer timer = new Timer();
	public static Castlewars castleWars = null;
	public static List<String> tornPlayers = new ArrayList<String>();
	public static List<String> bannedIps = new ArrayList<String>();
	public static List<String> onlinePlayers = new ArrayList<String>();
	public static String autoMessage = "In order to donate go to www.runedesign.co.nr!";
	public static boolean autoMessageSet = false;
	public static int messageTimer = 250;
	public static boolean lotteryStatus = true;
	public static final Object lockObject = new Object();
	public static String[] message = {
			"Donate on www.runedesign.co.nr and receive cool features!",
			"Check the forums for new updates: www.runedesign.co.nr",
			"Vote on forums and receive 250 tokens FREE",
			"lottery"
	};
	
	public static Random word = new Random();

	public static GameEngine getEngine() {
		return engine;
	}

	public static void banIp(String ip, Player p, Player banned) {
		bannedIps.add(ip);
		File bannedIpFile = new File("Data/bannedips.dat");
		if (bannedIpFile.exists()) {
			Serializer.saveBanned(bannedIpFile, bannedIps);
		}
		banned.getConnection().getChannel().disconnect();
		p.getFrames().sendChatMessage(0, "You have banned the ip: " + ip);
	}

	public static void setTimer() {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				World.calendar = new GregorianCalendar();
				System.gc();
			}
		}, 60000 * 5, 60000 * 5);
	}

	public static long tickTimer;
	public static Player lastMute = null;
	public static ServerChannelHandler netty;

	@SuppressWarnings("unchecked")
	public Server() {
		setTimer();
		Logger.log(this, "AutoSaver and System garbage collector task started!");//restart back. what? lol i was afk XD restart server compile
		File bannedIpFile = new File(
				"Data/bannedips.dat");
		if (!bannedIpFile.exists()) {
			Serializer.saveBanned(bannedIpFile, new ArrayList<String>());
		} else {
			try {
				bannedIps = (ArrayList<String>) Serializer.load(bannedIpFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		bannedIpFile = null;
		castleWars = new Castlewars();
		Logger.log(this, "Loading commands");
		new CommandManager();
		Logger.log(this, "Starting loading Cache...");
		new Cache();
		Logger.log(this, "Starting loading MapData...");
		new MapData();
		Logger.log(this, "Starting loading Packets...");
		new Packets();
		Logger.log(this, "Loading magic spells...");
		new MagicManager();
		Logger.log(this, "Starting up Game engine.");
		engine = new GameEngine();
		engine.start();
		Logger.log(this, "Starting loading World...");
		new World();
		Logger.log(this, "Starting loading Game Logic TaskManager...");
		new GameLogicTaskManager();
		Logger.log(this, "Starting Server Channel Handler...");
		netty = new ServerChannelHandler(port);
		Logger.log(this, "Server running, multi threaded and using "+Constants.AVALIABLE_PROCESSORS+" processors.");
	}

	public static void main(String[] args) {
		port = 43594;
		new Server();
	}
	
	public static int port = 43594;

	public static TaskManager getWorldExecutor() {
		return worldExecutor;
	}

	public static TaskManager getEntityExecutor() {
		return entityExecutor;
	}

	/**
	 * @return the savetaskmanager
	 */
	public static SaveTaskManager getSaveTaskmanager() {
		return saveTaskManager;
	}
	
}