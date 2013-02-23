package dragonkk.rs2rsps.net;

import java.util.HashMap;
import java.util.Map;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.net.commands.*;
import dragonkk.rs2rsps.util.Logger;


public class CommandManager {
	
	/*
	 * A hashmap holding the commands
	 */
	private static final Map<String, Command> COMMANDS = new HashMap<String, Command>();
	
	/*
	 * Constructor
	 */
	public CommandManager() {
		COMMANDS.put("buyspec", new BuySpec());
		COMMANDS.put("wests", new Wests());
		COMMANDS.put("44s", new Ports44());
		COMMANDS.put("52s", new Ports52());
		COMMANDS.put("gdz", new GdzTele());
		COMMANDS.put("volc", new VolcanoTele());
		COMMANDS.put("multisafe", new MultiSafe());
		COMMANDS.put("teletome", new TeleToMe());
		COMMANDS.put("clearlist", new ClearList());
		COMMANDS.put("delplayer", new RemovePlayer());
		COMMANDS.put("addplayer", new AddPlayer());
		COMMANDS.put("sendhome", new SendHome());
		COMMANDS.put("clanwars", new Clanwars());
		COMMANDS.put("uptime", new Uptime());
		COMMANDS.put("home", new Home());
		COMMANDS.put("info", new Info());
		COMMANDS.put("easts", new Easts());
		COMMANDS.put("changepass", new ChangePass());
		COMMANDS.put("donatorshop", new DonatorShop());
		COMMANDS.put("setgender", new SetGender());
		COMMANDS.put("pvp2", new PVPArea());
		COMMANDS.put("edge", new EdgeTele());
		COMMANDS.put("pvp", new DangerousPvPTeleport());
		COMMANDS.put("yell", new Yell());
		COMMANDS.put("bank", new Bank());
		COMMANDS.put("players", new Players());
		COMMANDS.put("help", new Help());
		COMMANDS.put("setlevel", new Setlevel());
		COMMANDS.put("moderns", new Moderns());
		COMMANDS.put("curses", new Curses());
		COMMANDS.put("ancients", new Ancients());
		COMMANDS.put("lunars", new Lunars());
		COMMANDS.put("item", new Item());
		COMMANDS.put("master", new Master());
		COMMANDS.put("mute", new Mute());
		COMMANDS.put("kick", new Kick());
		COMMANDS.put("unmute", new Unmute());
		COMMANDS.put("commands", new Commands());
		COMMANDS.put("extele", new ExTeleport());
		COMMANDS.put("teleto", new Teleto());
		COMMANDS.put("mb", new MageBank());
		COMMANDS.put("stats", new Stats());
		COMMANDS.put("empty", new Empty());
		COMMANDS.put("settag", new Tag());
		COMMANDS.put("removetag", new RemoveTag());
		COMMANDS.put("tokenring", new TokenRing());
		COMMANDS.put("gettokens", new GetTokens());
		COMMANDS.put("closelottery", new CloseLottery());
		COMMANDS.put("staffzone", new StaffZone());
		COMMANDS.put("dicezone", new DiceZone());
		COMMANDS.put("dicebag", new DiceBag());
		COMMANDS.put("runes", new Runes());
		COMMANDS.put("hybridset", new HybridSet());
		COMMANDS.put("food", new Food());
		COMMANDS.put("pots", new Pots());
		COMMANDS.put("safepk", new SafePk());
		Logger.log("CommandHandler", "Sucessfully loaded: "+COMMANDS.size()+" commands.");
	}
	
	/*
	 * Executes the command
	 */
	public static void execute(String args[], Player p) {
		try {
			if(p.getSkills().playerDead){ 
			//	p.getFrames().sendChatMessage(0, "You cannot use commands while in in the wilderness.");
				return;
			}
			String userCommand = args[0].toLowerCase();
			Command command = COMMANDS.get(userCommand);
			command.execute(args, p);
		} catch(Exception e) {
			p.getFrames().sendChatMessage(0, "Error while processing command.");
			//e.printStackTrace();
		}
	}

}
