package dragonkk.rs2rsps.skills.summoning.impl;

import java.io.Serializable;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.skills.summoning.SummoningCharacter;

/**
* Yo Lay i just got this from harry its nothing special
* Spawns a pack yak
 */
@SuppressWarnings("serial")
public class PackYak extends SummoningCharacter implements Serializable {

	/**
	 * @param p
	 * @param id
	 */
	public PackYak(Player p) {
		super(p, 6873);
	}

	@Override
	public int getHeadAnimConfig() {
		return 8388608;
	}
}
