package dragonkk.rs2rsps.skills.woodcutting;

import dragonkk.rs2rsps.events.GameLogicTask;
import dragonkk.rs2rsps.events.GameLogicTaskManager;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.rscache.ItemDefinitions;
import dragonkk.rs2rsps.rsobjects.RSObjectsRegion;
import dragonkk.rs2rsps.util.Misc;
import dragonkk.rs2rsps.util.RSObject;
import dragonkk.rs2rsps.util.RSTile;

public class WoodCutting {

	Player player;
	
	private RSTile treeLocation;
	private byte treeType;
	private short treeId;
	public short anim;
	public short getAxeTime;
	public int wcTimer;
	private static int[] reqLevels = {1,15,30,60,75,45};
	private static int[] randomise = {5,5,5,18,22,16};
	private static int[] treeTimes = {20, 30, 60, 120, 150, 83};
	
	private boolean processingWc;
	public WoodCutting(Player player) {
		this.player = player;
	}
	
	public void startWcing(int treeId, int treeType, RSTile treeLocation) {
		this.treeType = (byte) treeType;
		player.turnTemporarilyTo(treeLocation);
		if(!canStartWcing()) {
			forceCancelWc();
			return;
		}
		this.treeId = (short) treeId;
		this.treeLocation = treeLocation;
		setWcTime();
		processWC();
	}

	public boolean isWcing() {
		return treeId != -1 && treeType != -1 && treeLocation != null;
	}
	
	private static int[] treeLogs = {1511, 1521, 1519, 1515, 1513, 1517};
	private static double[] treeXp = {25, 37.5, 67.5, 175, 250, 100};
	
	private void addLog() {
		player.getInventory().addItem(treeLogs[treeType], 1);
		player.getSkills().addXp(8, treeXp[treeType]);
		player.getFrames().sendChatMessage(0, "You cut some "+ItemDefinitions.forID(treeLogs[treeType]).name+ ".");
	}
	public void processWC() {
		if(processingWc)
			return;
		processingWc = true;
		GameLogicTaskManager.schedule(new GameLogicTask() {
			@Override
			public void run() {
				if(!player.isOnline() || !isWcing()) {
					processingWc = false;
					this.stop();
					return;
				}
				player.animate(anim);
				if (wcTimer > 0) {
					wcTimer--;
					if(!RSObjectsRegion.objectExistsAt(treeId, treeLocation)) {
						cancelWc();
						processingWc = false;
						this.stop();
					}
					return;
				}
				switch(useTree(treeId, treeLocation, treeType)) {
				case 0:
					addLog();
					setWcTime();
				break;
				case 1:
					addLog();
					forceCancelWc();
					processingWc = false;
					this.stop();
				return;
				case 2:
					cancelWc();
					processingWc = false;
					this.stop();
				return;
				}
				if (player.getInventory().getFreeSlots() == 0){
					player.getFrames().sendChatMessage(0, "Not enough space in your inventory.");
					forceCancelWc();
					processingWc = false;
					this.stop();
				}
				
			}
			
		}, 0,0, 0);
	}
	
    public void setWcTime() { // 1 normal, 2 oak, 3 willow, 4 yew, 5 magic 6maple
        wcTimer = treeTimes[treeType] - player.getSkills().getLevel(8) - Misc.random(getAxeTime) + Misc.random(getAxeTime);
        if (wcTimer < 1) {
        	wcTimer = 1 + Misc.random(randomise[treeType]);
        }
    }
	
	public boolean canStartWcing() {
		if (!canUseAxe())
			return false;
		if(!hasLvlForWood())
			return false;
		if (player.getInventory().getFreeSlots() == 0){
			player.getFrames().sendChatMessage(0, "Not enough space in your inventory.");
			return false;
		}
		player.getFrames().sendChatMessage(0, "You swing you're axe at the tree.");
		return true;
	}
	public boolean hasLvlForWood() {
		int requiredLvl = reqLevels[treeType];
		if (player.getSkills().getLevel(8) < requiredLvl) {
			player.getFrames().sendChatMessage(0, "You need a woodcutting level of " + requiredLvl + " to cut this tree.");
		return false;
		}
		return true;
	}
	
	
	public boolean hasAxe(){
	if (hasItem(1351) || hasItem(1349) || hasItem(1353) || hasItem(1355) || hasItem(1357)|| hasItem(1361) || hasItem(1359) || hasItem(6739) || hasItem(13661))
		return true;
	if (player.getEquipment().get(3) != null){
	int axe = player.getEquipment().get(3).getDefinition().getId();
		switch (axe){
			case 1351://Bronze Axe
			case 1349://Iron Axe
			case 1353://Steel Axe
			case 1361://Black Axe
			case 1355://Mithril Axe
			case 1357://Adamant Axe
			case 1359://Rune Axe
			case 6739://Dragon Axe
			case 13661: //Inferno adze
			return true;
		}
	}
		return false;
	}
		
	public boolean canUseAxe() {
		if (!hasAxe()) {
			player.getFrames().sendChatMessage(0, "You need an axe to wood cut.");
			return false;
		}
		if (!SetAxe()) {
			player.getFrames().sendChatMessage(0, "You dont have level for use that axe.");
			return false;
		}
		return true;
	}
	
		public boolean SetAxe(){
	anim = -1;
	//UsingAdze = false;
	int wcLvl = player.getSkills().getLevel(8);
	if (player.getEquipment().get(3) == null){
		if (hasItem(6739) && wcLvl >= 61) {anim = 9992;getAxeTime = 13; return true;}
		if (hasItem(1359) && wcLvl >= 41) {anim = 9993;getAxeTime = 10; return true;}
		if (hasItem(1357) && wcLvl >= 31) {anim = 9994;getAxeTime = 7; return true;}
		if (hasItem(1355) && wcLvl >= 21) {anim = 9995;getAxeTime = 5; return true;}
		if (hasItem(1361) && wcLvl >= 10) {anim = 9996;getAxeTime = 4; return true;}
		if (hasItem(1353) && wcLvl >= 6) {anim = 9997;getAxeTime = 3; return true;}
		if (hasItem(1349) && wcLvl >= 1) {anim = 9998;getAxeTime = 2; return true;}
		if (hasItem(1351) && wcLvl >= 1) {anim = 9999;getAxeTime = 1; return true;}
		if (hasItem(13661) && wcLvl >= 61) {anim = 10227;getAxeTime = 13; /*UsingAdze = true;*/ return true;}
	return false;
	}
	int axe = player.getEquipment().get(3).getDefinition().getId();
	switch(axe){
	case 6739://Dragon Axe
		if(wcLvl >= 61) {
		anim = 9992;
		getAxeTime = 13;
		return true;
		}
	case 1359://Rune Axe
		if(wcLvl >= 41) {
		getAxeTime = 10;
		anim = 9993;
		return true;
		}
	case 1357://Adamant Axe
		if(wcLvl >= 31) {
		getAxeTime = 7;
		anim = 9994;
		return true;
		}
	case 1355://Mithril Axe
		if(wcLvl >= 21) {
		getAxeTime = 5;
		anim = 9995;
		return true;
		}
	case 1361://Black Axe
		if(wcLvl >= 11) {
			getAxeTime = 4;
		anim = 9996;
		return true;
		}
	case 1353://Steel Axe
		if(wcLvl >= 6) {
		getAxeTime = 3;
		anim = 9997;
		return true;
	}
	case 1349://Iron Axe
		getAxeTime = 2;
		anim = 9998;
		return true;
	case 1351://Bronze Axe
		getAxeTime = 1;
		anim = 9999;
		return true;
	case 13661: //Inferno adze
		if(wcLvl >= 61) {
		getAxeTime = 13;
		//UsingAdze = true;
		anim = 10227;
		return true;
		}
	default:
		if (hasItem(6739) && wcLvl >= 61) {anim = 9992;getAxeTime = 13; return true;}
		if (hasItem(1359) && wcLvl >= 41) {anim = 9993;getAxeTime = 10; return true;}
		if (hasItem(1357) && wcLvl >= 31) {anim = 9994;getAxeTime = 7; return true;}
		if (hasItem(1355) && wcLvl >= 21) {anim = 9995;getAxeTime = 5; return true;}
		if (hasItem(1361) && wcLvl >= 10) {anim = 9996;getAxeTime = 4; return true;}
		if (hasItem(1353) && wcLvl >= 6) {anim = 9997;getAxeTime = 3; return true;}
		if (hasItem(1349) && wcLvl >= 1) {anim = 9998;getAxeTime = 2; return true;}
		if (hasItem(1351) && wcLvl >= 1) {anim = 9999;getAxeTime = 1; return true;}
		if (hasItem(13661) && wcLvl >= 61) {anim = 10227;getAxeTime = 13; /*UsingAdze = true;*/ return true;}
	break;
	}
	return false;
	}
		
	public boolean hasItem(int id) {
		return player.getInventory().contains(id);
	}
	
	public void cancelWc() {
		treeLocation = null;
		treeType = -1;
		treeId = -1;
	}
	
	public void forceCancelWc() {
		treeLocation = null;
		treeType = -1;
		treeId = -1;
		player.animate(-1);
	}
	
	  private static int[] TreeRest = {8,  15,   51,  94,   121, 72};
	  private static int[] TreeStump = {3884,3884,3884,3884,3884,3884};
	  
	  private static byte useTree(int treeId, RSTile location, int treeType) {
		  if(!RSObjectsRegion.objectExistsAt(treeId, location))
			  return 2;
		  if(treeType == 0 || Misc.random(10) == 0) {
			  RSObjectsRegion.putObject(new RSObject(TreeStump[treeType], location, 10, -1), TreeRest[treeType]*600);
			  return 1;
		  }
		  return 0;
	  }
	  
}
