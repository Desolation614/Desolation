package dragonkk.rs2rsps.skills.combat;

import dragonkk.rs2rsps.model.Entity;
import dragonkk.rs2rsps.model.Item;
import dragonkk.rs2rsps.model.player.Equipment;
import dragonkk.rs2rsps.model.player.Player;

public class CombatManager {
	
	public static byte getSpeedForWeapon(int weaponId) {
		if(weaponId == -1)
			return 3;
		if(weaponId == 18786)
			return 4;
		String name = new Item(weaponId).getDefinition().name;
		if(name.contains("dart")||
			name.contains("knife"))
			return 3;
		if(name.contains("shortbow") || name.contains("Karil's crossbow"))
			return 3;
		if(name.contains("dagger") ||
				name.contains("claws") ||
				name.contains("whip") ||
				name.contains("Zamorakian spear") || 
				name.contains("Korasi's sword") ||
				name.contains("Slayers staff") || 
				name.contains("Ancient staff") || 
				name.contains("Saradomin sword") ||
				name.contains("scimitar") ||
				name.contains(" sword") ||
				name.contains("Sling") ||
				name.contains("Toktz-Xil-Ak") ||
				name.contains("Toktz-Xil-Ek") ||
				name.contains("Toktz-Xil-Ul") ||
				name.contains("salamander") ||
				name.contains("Saradomin staff") ||
				name.contains("Zamorak staff") ||
				name.contains("rapier") ||
				name.contains("Guthix staff") || name.contains("Korasi's sword"))
			return 4;

		if(name.contains("longsword") ||
			name.contains("Staff of light") ||
				name.contains("mace") ||
				name.contains("spear") ||
				name.contains("pickaxe") ||
				name.contains("hatchet") ||
				name.contains("TzHaar-Ket-Em") ||
				name.contains("Torag's hammers") ||
				name.contains("Guthan's warspear") ||
				name.contains("Verac's flail") ||
				name.contains("(sighted)") ||
				name.contains("throwing axe") ||
				name.contains("battlestaff") ||
				name.contains("Iban's staff") ||
				name.contains("alamander") ||
				name.contains("composite bow") ||
				name.contains("Seercull") ||
				name.contains("Crystal bow"))
		return 5;

		if(name.contains("godsword") ||
				name.contains("warhammer") ||
				name.contains("battleaxe") ||
				name.contains("anchor") ||
				name.contains("Ahrim's staff") ||
				name.contains("Toktz-Mej-Tal") ||
				name.contains("longbow") ||
				name.contains("crossbow") ||
				name.contains("javelin"))
			return 6;


		if(name.contains("2h") ||
				name.contains("halberd") ||
				name.contains("maul") ||
				name.contains("Balmung") ||
				name.contains("TzHaar-Ket-Om") ||
				name.contains("Ivandis flail") ||
				name.contains("Hand cannon") ||
				name.contains("Dharok's greataxe"))
		    
			return 7;

		if(name.contains("Ogre bow"))
			return 8;
		if(name.contains("Dark bow"))
			return 9;
		return 4;
	}
	
	public static boolean wearingDharok(Player p) {
		if(p.getEquipment().get(Equipment.SLOT_WEAPON) == null || !p.getEquipment().get(Equipment.SLOT_WEAPON).getDefinition().name.contains("Dharok"))
			return false;
		if(p.getEquipment().get(Equipment.SLOT_HAT) == null || !p.getEquipment().get(Equipment.SLOT_HAT).getDefinition().name.contains("Dharok"))
			return false;
		if(p.getEquipment().get(Equipment.SLOT_CHEST) == null || !p.getEquipment().get(Equipment.SLOT_CHEST).getDefinition().name.contains("Dharok"))
			return false;
		if(p.getEquipment().get(Equipment.SLOT_LEGS) == null || !p.getEquipment().get(Equipment.SLOT_LEGS).getDefinition().name.contains("Dharok"))
			return false;
		return true;
	}
	
	public static boolean wearingVoid(Player p, int helm) {
		if(p.getEquipment().get(Equipment.SLOT_WEAPON) == null || !p.getEquipment().get(Equipment.SLOT_WEAPON).getDefinition().name.contains("Void"))
			return false;
		if(p.getEquipment().get(Equipment.SLOT_HAT) == null || !(p.getEquipment().get(Equipment.SLOT_HAT).getDefinition().getId() == helm))
			return false;
		if(p.getEquipment().get(Equipment.SLOT_CHEST) == null || !p.getEquipment().get(Equipment.SLOT_CHEST).getDefinition().name.contains("Void"))
			return false;
		if(p.getEquipment().get(Equipment.SLOT_LEGS) == null || !p.getEquipment().get(Equipment.SLOT_LEGS).getDefinition().name.contains("Void"))
			return false;
		return true;
	}

	public static byte distForWeap(int weaponId) {
		switch (weaponId) {
		default:
			return 0;
		}
	}

	public static double getSpecDamageDoublePercentage(int weaponId) {
		switch (weaponId) {
		case 11694:
			return 1.34375;
		case 11696:
			return 1.1825;
		case 3204:
		case 3101:
			return 1.1;
		case 3105:
			return 1.15;
		case 1434:
			return 1.45;
		case 11698:
		case 11700:
			return 1.075;
		case 13902:
		case 13904:
			return 1.25;
		case 13899:
		case 13901:
			return 1.20;
		}
		String weaponName = weaponId == -1 ? "" : new Item(weaponId).getDefinition().name;
		if(weaponName.contains("Dragon dagger"))
			return 1.1;
		
		return 1;
	}

	public static short getSpecAmt(int weaponId) {
		switch (weaponId) {
		case 4151:
		case 4153:
		case 11694:
		case 14484:
			return 50;
		default:
			return -1;
		}
	}

	public static short getDefenceEmote(Entity entity) {
		if (entity instanceof Player) {
			Player target = (Player) entity;
			final short weaponId = (short) (target.getEquipment().get(3) == null ? -1 : target.getEquipment().get(3).getId());
			final short shieldId = (short) (target.getEquipment().get(5) == null ? -1 : target.getEquipment().get(5).getId());
			if(shieldId == -1 && weaponId == -1)
				return 424;
			String weaponName = weaponId == -1 ? "" : new Item(weaponId).getDefinition().name;
			String shieldName = shieldId == -1 ? "" : new Item(shieldId).getDefinition().name;
			
			if (shieldId != -1 && shieldName.contains("defender"))
				return 4177;
			if (shieldId != -1 && shieldName.contains("shield") || shieldId == 6524)
				return 1156;
			if (weaponId != -1 && (weaponName.contains("godsword") || weaponName.contains("2h sword") || weaponName.contains("Saradomin sword")))
				return 7050;
			if (weaponId != -1 && (weaponName.contains("Keris") || weaponName.contains("dagger")))
				return 403;
			if(weaponName.contains("staff") || weaponName.contains("wand")) {
				return 415;
			}
			if(weaponName.contains("whip"))
				return 11974;
			if(weaponName.contains("scimitar"))
				return 12030;
			if(weaponId == 15486){
				return 12806;
			}

		}
		return 424;
	}

	public static boolean isRangingWeapon(int weaponId) {
		if(weaponId == -1)
			return false;
		String weaponName = new Item(weaponId).getDefinition().name;
		if(weaponName.contains("bow") || weaponName.contains("dart") || weaponName.contains("knife") || weaponName.contains("javel"))
			return true;
		return false;
	}

}
