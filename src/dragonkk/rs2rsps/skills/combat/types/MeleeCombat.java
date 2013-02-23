package dragonkk.rs2rsps.skills.combat.types;

import dragonkk.rs2rsps.model.Entity;
import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.skills.combat.CombatType;

public class MeleeCombat implements CombatType {

	/*
	 * Executes the combat type when called
	 */
	@Override
	public void execute(Entity p, Entity opp) {
		if (p instanceof Player) {
			if (opp instanceof Player) {
				int weaponAnimation = 1;
				p.animate(weaponAnimation);
			}
		}
	}

	/*
	 * Gets the weapon animation id
	 */
	public int getWeaponAnimation(Player p) {
		switch (p.getEquipment().get(3).getId()) {
		case 4151:
			return 1658;
		}
		return 808;
	}

}
