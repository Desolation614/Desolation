package dragonkk.rs2rsps.model.player;

import java.util.ArrayList;
import java.util.List;

public class MinigameManager {

	private Player player;
	private List<String> minigames;
	
	public MinigameManager(Player player) {
		this.player = player;
		this.minigames = new ArrayList<String>();
	}
	
	public void addMinigame(String mini) {
		minigames.add(mini);
	}
	
	
}
