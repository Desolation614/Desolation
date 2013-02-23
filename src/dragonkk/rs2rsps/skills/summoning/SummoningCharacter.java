package dragonkk.rs2rsps.skills.summoning;

import java.io.Serializable;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.npc.Npc;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.RSTile;

@SuppressWarnings("serial")
public abstract class SummoningCharacter implements Serializable {
	
	/**
	 * Constructor
	 * @param p The player the summmoning monster is bound to
	 */
	public SummoningCharacter(Player p, int id) {
		setPlayer(p);
		setId(id);
		RSTile loc = getPlayer().getLocation();
		npc = new Npc((short)id, RSTile.createRSTile(loc.getX(), loc.getY(), loc.getZ()));
		World.getNpcs().add(npc);
		toPlayer();
		sendFrames();
		GameLogicTaskManager.schedule(tickTask, 0, 0);
	}
	
	public String ownerName = "";
	
	/**
	 * Sends the frames for summoning information
	 */
	public void sendFrames() {
		getPlayer().getFrames().sendConfig(448, getId());
		getPlayer().getFrames().sendConfig(1174, getId());
		getPlayer().getFrames().sendConfig(1175, 102025930);
		getPlayer().getFrames().sendConfig(1175, 102025930);
		getPlayer().getFrames().sendConfig(1171, 20480);
		getPlayer().getFrames().sendConfig(1171, 20480);
		getPlayer().getFrames().sendConfig(1176, 7424);
		getPlayer().getFrames().sendConfig(1801, 48);
		getPlayer().getFrames().sendConfig(1231, 333839);
		getPlayer().getFrames().sendConfig(1160, getHeadAnimConfig());
		getPlayer().getFrames().sendConfig(1175, 102025930);
		getPlayer().getFrames().sendConfig(1175, 102025930);
		getPlayer().getFrames().sendConfig(1160, getHeadAnimConfig());
		getPlayer().getFrames().sendConfig(108, 1);
		getPlayer().getFrames().sendInterface(1, 746, 51, 662);
		getPlayer().getFrames().sendInterface( 1, 746, 34, 884);
		getPlayer().getFrames().sendAMask( -1, -1, 746, 125, 0, 2);
		getPlayer().getFrames().sendAMask( -1, -1, 884, 11, 0, 2);
		getPlayer().getFrames().sendAMask( -1, -1, 884, 12, 0, 2);
		getPlayer().getFrames().sendAMask( -1, -1, 884, 13, 0, 2);
		getPlayer().getFrames().sendConfig( 1175, 102025930);
		getPlayer().getFrames().sendConfig( 1175, 102025930);
		getPlayer().getFrames().sendInterface( 1, 746, 51, 662);
	}
	
	public void tick() {
		if(getPlayer() == null) {
			tickTask.stop();
			System.out.println("Player was either nulled.");
			return;
		}
		if(!npc.getLocation().withinDistance(getPlayer().getLocation(), 12)) {
			toPlayer();
			return;
		} else {
			if(npc.getLocation().getDistance(getPlayer().getLocation()) > 3) {
				followPlayer();
			}
		}
	}
	
	public void toPlayer() {
		if(npc == null) {
			return;
		}
		if(getPlayer() == null) {
			return;
		}
		npc.teleport(getPlayer().getLocation().getX(), getPlayer().getLocation().getY(), getPlayer().getLocation().getZ());
		npc.graphics(1315);
	}
	
	public GameLogicTask tickTask = new GameLogicTask() {
		@Override
		public void run() {
			try {
				tick();
				this.needRemove = false;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	public void followPlayer() {
		int firstX = getPlayer().getLocation().getX() - (npc.getLocation().getRegionX() - 6) * 8;
		int firstY = getPlayer().getLocation().getY() - (npc.getLocation().getRegionY() - 6) * 8;
		npc.getWalk().reset();
		npc.getWalk().addToWalkingQueue(firstX, firstY);
	}

	
	public abstract int getHeadAnimConfig();
	
	public Npc npc;
	
	/**
	 * Sets the bound instance
	 * @param player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Gets the bound player instance
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * The player instance the monster is bound to.
	 */
	private Player player;
	
	/**
	 * The id of the monster
	 */
	private int id;

}
