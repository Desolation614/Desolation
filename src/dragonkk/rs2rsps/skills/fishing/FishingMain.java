package dragonkk.rs2rsps.skills.fishing;

import java.util.HashMap;
import java.util.Map;

import dragonkk.rs2rsps.skills.fishing.spots.NetFishing;


public class FishingMain {
	
	private static final Map<Integer, FishingSpot> FISHING_SPOTS = new HashMap<Integer, FishingSpot>();

	public FishingMain() {
		FISHING_SPOTS.put(320, new NetFishing());
	}
}
