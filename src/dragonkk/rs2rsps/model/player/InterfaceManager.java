package dragonkk.rs2rsps.model.player;

import java.util.HashMap;
import java.util.Map;

public class InterfaceManager {

	private Player player;

	private final Map<Integer, int[]> openedinterfaces = new HashMap<Integer, int[]>();

	public InterfaceManager(Player player) {
		this.player = player;
	}

	public boolean addInterface(int windowId, int tabId, int childId) {
		if (this.openedinterfaces.containsKey(tabId))
			this.player.getFrames().closeInterface(tabId);
		this.openedinterfaces.put(tabId, new int[] { childId, windowId });
		return this.openedinterfaces.get(tabId)[0] == childId;
	}

	public boolean containsInterface(int tabId, int childId) {
		if (childId == 548 || childId == 746)
			return true;
		if (!this.openedinterfaces.containsKey(tabId))
			return false;
		return this.openedinterfaces.get(tabId)[0] == childId;
	}

	public int getTabWindow(int tabId) {
		if (!this.openedinterfaces.containsKey(tabId))
			return 548;
		return this.openedinterfaces.get(tabId)[1];
	}

	public boolean containsInterface(int childId) {
		if (childId == 548 || childId == 746)
			return true;
		for (Object value : this.openedinterfaces.values().toArray())
			if (((int[]) value)[0] == childId)
				return true;
		return false;
	}

	public boolean containsTab(int tabId) {
		return this.openedinterfaces.containsKey(tabId);
	}

	public boolean removeAll() {
		this.openedinterfaces.clear();
		return this.openedinterfaces.isEmpty();
	}

	public boolean removeTab(int tabId) {
		if (!this.openedinterfaces.containsKey(tabId))
			return false;
		this.openedinterfaces.remove(tabId);
		return !this.openedinterfaces.containsKey(tabId);
	}

	public boolean removeInterface(int tabId, int childId) {
		if (!this.openedinterfaces.containsKey(tabId))
			return false;
		if (this.openedinterfaces.get(tabId)[0] != childId)
			return false;
		this.openedinterfaces.remove(tabId);
		return !this.openedinterfaces.containsKey(tabId);
	}

}
