
package dragonkk.rs2rsps.skills.combat;

import java.util.ArrayList;
import java.util.List;

import dragonkk.rs2rsps.model.World;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.util.Misc;

public class MultiCombatSpells {
	
	public enum SpellType {
		BLOOD,
		SMOKE,
		SHADOW,
		ICE,
		MIASMIC;
	}
	
	/**
	 *  Use for multi magic attacks
	 * @param range
	 * @param opp
	 * @param max
	 * @param gfx
	 * @param height
	 */
	public static void multiAtk(int range, Player atker, Player opp, int max, int gfx, int height, SpellType type, int strength) {
		List<Player> players = new ArrayList<Player>(9);
		for(Player p : World.getPlayers()) {
			if(p == null || p.getSkills().playerDead) {
				continue;
			}
			if(Misc.getDistance((int)opp.getLocation().getX(), (int)opp.getLocation().getY(), (int)p.getLocation().getX(), (int)p.getLocation().getY()) <= range){
				players.add(p);
				if(players.size() > 9)
					break;
			}
		}
		for(Player d : players) {
			attackPlayer(atker, d, max, gfx, height, type, strength);
		}
		players.clear();
		players = null;
	}
	
	private static void attackPlayer(Player p, Player opp, int max, int gfx, int height, SpellType type, int strenght) {
		
	}

}
