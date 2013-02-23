package dragonkk.rs2rsps.model.minigames;

import java.util.ArrayList;
import java.util.List;

import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.model.player.Player;

public class Castlewars {
	
	/*
	 * Players in the waiting room
	 */
	public List<Player> SaradominWaiting = new ArrayList<Player>();
	public List<Player> ZamorakWaiting = new ArrayList<Player>();
	
	/*
	 * Players in game
	 */	
	public List<Player> SaradominGame = new ArrayList<Player>();
	public List<Player> ZamorakGame = new ArrayList<Player>();
	
	/*
	 * Barrackades setup by each team
	 */
	public int saradominBarrackades;
	public int zamorakBarrackades;
	
	/*
	 * Flag taken variables
	 */
	public boolean saradominFlagTaken;
	public boolean zamorakFlagTaken;
	public int saradominPoints;
	public int zamorakPoints;
	
	/*
	 * Game timer variables
	 */
	public int waitingTimer;
	public int gameTimer;
	
	/*
	 * Message variables
	 */
	public final String HAS_CAPE_HELM = "You cannot wear hats, capes or helms in the arena.";
	public final String SAME_TEAM_ATTACK_ZAMORAK = "Zamorak won't let you attack your allies!";
	public final String SAME_TEAM_ATTACK_SARADOMIN = "Saradomin won't let you attack your allies!";
	public final String TOO_MANY_BARRACKADES = "Your Team has already set up ten barricades.";
	public final String BANDAGE_FROM_TABLE = "You take a bandage from the table.";
	public final String ENTER_OTHER_ROOM = "Your not allowed to enter the other team's starting room.";
	public final String NOT_ENOUGH_SPACE = "You don't have enough space in your inventory to pick up this item.";
	
	/*
	 * Item variables
	 */
	public final int BANDAGE = 4049; 
	public final int BARRACADE = 4053;
	public final int SARADOMIN_CAPE = 4041;
	public final int ZAMORAK_CAPE = 4042;
	public final int[] ZAMORAK_WAIITING_ROOM = {
			2421, 9523
	};
	public final int[] SARADOMIN_WAITING_ROOM = {
			2381, 9489
	};
	
	/*
	 * Item methods
	 */
	public void pickupBandage(Player p) {
		if(p.getInventory().getFreeSlots() < 1) {
			p.getFrames().sendChatMessage(0, NOT_ENOUGH_SPACE);
			return;
		}
		p.getInventory().addItem(BANDAGE, 1);
	}
	
	
	/*
	 * Portal methods
	 */
	public boolean canEnter(Player p) {
		if(p.getEquipment().getEquipment().get(p.getEquipment().SLOT_HAT) == null) {
			if(p.getEquipment().getEquipment().get(p.getEquipment().SLOT_CAPE) == null) {
				return true;
			} else {
				p.getFrames().sendChatMessage(0, this.HAS_CAPE_HELM);
				return false;
			}
		} else {
			p.getFrames().sendChatMessage(0, this.HAS_CAPE_HELM);
			return false;
		}
	}
	
	@SuppressWarnings("static-access")
	public void enterZamorak(Player p) {
		if(!canEnter(p) || ZamorakWaiting.contains(p))
			return;
		ZamorakWaiting.add(p);
		p.getEquipment().set(p.getEquipment().SLOT_CAPE, new Item(ZAMORAK_CAPE));
		p.getMask().getRegion().teleport(ZAMORAK_WAIITING_ROOM[0], ZAMORAK_WAIITING_ROOM[1], 0, 0);
	}
	
	@SuppressWarnings("static-access")
	public void enterSaradomin(Player p) {
		if(!canEnter(p) || SaradominWaiting.contains(p))
			return;
		p.getEquipment().set(p.getEquipment().SLOT_CAPE, new Item(SARADOMIN_CAPE));
		SaradominWaiting.add(p);
		p.getMask().getRegion().teleport(SARADOMIN_WAITING_ROOM[0], SARADOMIN_WAITING_ROOM[1], 0, 0);
	}
	
	public void leaveZamorak(Player p) {
		if(!ZamorakWaiting.contains(p) || !ZamorakGame.contains(p))
			return;
		p.getEquipment().set(p.getEquipment().SLOT_CAPE, new Item(-1));
		ZamorakWaiting.remove(p);
		ZamorakGame.remove(p);
			//2422 3076
	}
	
	public void leaveSaradomin(Player p) {
		if(!SaradominWaiting.contains(p) || !SaradominGame.contains(p))
			return;
		p.getEquipment().set(p.getEquipment().SLOT_CAPE, new Item(-1));
		SaradominWaiting.remove(p);
		SaradominGame.remove(p);
			
	}
	

}
