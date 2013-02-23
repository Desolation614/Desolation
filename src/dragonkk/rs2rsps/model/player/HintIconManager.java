package dragonkk.rs2rsps.model.player;

import dragonkk.rs2rsps.model.Entity;
import dragonkk.rs2rsps.util.HintIcon;

public class HintIconManager {

	private Player p;
	private HintIcon[] loadedIcons;
	
	public HintIconManager(Player p) {
		this.p = p;
		loadedIcons = new HintIcon[7];
	}
	
	public int addHintIcon(Entity target, int arrowType, int modelId, boolean saveIcon) {
		int index = saveIcon ? getFreeIndex() : 7;
		if(index != -1) {
			HintIcon icon = new HintIcon(target.getIndex(), target instanceof Player ? 10 : 1, arrowType, modelId, index);
			p.getFrames().sendHintIcon(icon);
			if(saveIcon)
				loadedIcons[index] = icon;
		}
		return index;		
	}
	/*
	 * dirs
	 * 2 - center
	 * 3 - west
	 * 4 - east
	 * 5 - south
	 * 6 - north
	 */
	public int addHintIcon(int coordX, int coordY, int height, int distanceFromFloor, int direction, int arrowType, int modelId, boolean saveIcon) {
		int index = saveIcon ? getFreeIndex() : 7;
		if(index != -1) {
			if(direction < 2 || direction > 6)
				direction = 2;
			HintIcon icon = new HintIcon(coordX, coordY, height, distanceFromFloor, direction, arrowType, modelId, index);
			p.getFrames().sendHintIcon(icon);
			if(saveIcon)
				loadedIcons[index] = icon;
		}
		return index;		
	}
	
	public int addHintIcon(int modelId, boolean saveIcon) {
		int index = saveIcon ? getFreeIndex() : 7;
		if(index != -1) {
			HintIcon icon = new HintIcon(8, modelId, index);
			p.getFrames().sendHintIcon(icon);
			if(saveIcon)
				loadedIcons[index] = icon;
		}
		return index;		
	}
	
	public void removeUnsavedHintIcon() {
		p.getFrames().sendHintIcon(new HintIcon());
	}
	
	public boolean reloadHintIcon(int index) {
		if(index >= loadedIcons.length)
			return false;
		if(loadedIcons[index] == null)
			return false;
		p.getFrames().sendHintIcon(loadedIcons[index]);
		return true;
	}
	
	public boolean removeHintIcon(int index) {
		if(index == 7) {
			removeUnsavedHintIcon();
			return true;
		}
		if(index >= loadedIcons.length)
			return false;
		if(loadedIcons[index] == null)
			return false;
		loadedIcons[index].setTargetType(0);
		p.getFrames().sendHintIcon(loadedIcons[index]);
		loadedIcons[index] = null;
		return true;
	}
	
	public void removeAll() {
		for(int index = 0; index < loadedIcons.length; index++) {
			loadedIcons[index].setTargetType(0);
			p.getFrames().sendHintIcon(loadedIcons[index]);
			loadedIcons[index] = null;
		}
	}
	
	public boolean isEmpty() {
		for(int index = 0; index < loadedIcons.length; index++)
			if(loadedIcons[index] != null)
				return false;
		return true;
	}
	
	private int getFreeIndex() {
		for(int index = 0; index < loadedIcons.length; index++)
				if(loadedIcons[index] == null)
					return index;
		return -1;
	}
}
