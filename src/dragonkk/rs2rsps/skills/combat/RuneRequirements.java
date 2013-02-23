package dragonkk.rs2rsps.skills.combat;

import dragonkk.rs2rsps.model.player.Player;

public class RuneRequirements {
	
	public static final int FIRE_RUNE = 554;
	public static final int WATER_RUNE = 555;
	public static final int AIR_RUNE = 556;
	public static final int EARTH_RUNE = 557;
	public static final int MIND_RUNE = 558;
	public static final int BODY_RUNE = 559;
	public static final int DEATH_RUNE = 560;
	public static final int NATURE_RUNE = 561;
	public static final int CHAOS_RUNE = 562;
	public static final int LAW_RUNE = 563;
	public static final int COSMIC_RUNE = 564;
	public static final int BLOOD_RUNE = 565;
	public static final int SOUL_RUNE = 566;
	public static final int ASTRAL_RUNE = 9075;
	
	public static final Object[][] RUNE_REQS = {
		/* name , rune 1, rune 2, rune 3, rune 4, rune am 1, rune am 2, rune am3 , 4 */
		{"MIASMIC_BARRAGE", BLOOD_RUNE, EARTH_RUNE, SOUL_RUNE, -1, 4, 4, 4, -1},
		{"ICE_BARRAGE", BLOOD_RUNE, DEATH_RUNE, WATER_RUNE, -1, 2, 4, 6, -1},
		{"BLOOD_BARRAGE", DEATH_RUNE, BLOOD_RUNE, SOUL_RUNE, -1, 4, 4, 1, -1},
		{"SHADOW_BARRAGE", DEATH_RUNE, BLOOD_RUNE, AIR_RUNE, SOUL_RUNE, 4, 2, 4, 3},
		{"SMOKE_BARRAGE", DEATH_RUNE, BLOOD_RUNE, FIRE_RUNE, AIR_RUNE, 4, 2, 4, 4},
		{"MIASMIC_BLITZ", BLOOD_RUNE, EARTH_RUNE, SOUL_RUNE, -1, 2, 3, 3},
		{"ICE_BLITZ", DEATH_RUNE, BLOOD_RUNE, WATER_RUNE, -1, 2, 2, 3, -1},
		{"BLOOD_BLITZ", DEATH_RUNE, BLOOD_RUNE, -1, -1, 2, 4 , -1, -1},
		{"SHADOW_BLITZ", DEATH_RUNE, BLOOD_RUNE, AIR_RUNE, SOUL_RUNE, 2, 2, 2, 2},
		{"SMOKE_BLITZ", DEATH_RUNE, BLOOD_RUNE, FIRE_RUNE, AIR_RUNE, 2, 2, 2, 2},
		{"MIASMIC_BURST", CHAOS_RUNE, EARTH_RUNE, SOUL_RUNE, -1, 4, 2, 2, -1},
		{"ICE_BURST", CHAOS_RUNE, DEATH_RUNE, WATER_RUNE, -1, 4, 2, 4, -1},
		{"BLOOD_BURST", CHAOS_RUNE, DEATH_RUNE, BLOOD_RUNE, -1, 4, 2, 2, -1},
		{"SHADOW_BURST", CHAOS_RUNE, DEATH_RUNE, AIR_RUNE, SOUL_RUNE, 4, 2, 1, 2},
		{"SMOKE_BURST", CHAOS_RUNE, DEATH_RUNE, FIRE_RUNE, AIR_RUNE, 4, 2, 2, 2},
		{"MIASMIC_RUSH", CHAOS_RUNE, EARTH_RUNE, SOUL_RUNE, -1, 2, 1, 1, -1},
		{"ICE_RUSH", CHAOS_RUNE, DEATH_RUNE, WATER_RUNE, -1, 2, 2, 2, -1},
		{"BLOOD_RUSH", CHAOS_RUNE, DEATH_RUNE, BLOOD_RUNE, -1, 2, 2, 1, -1},
		{"SHADOW_RUSH", CHAOS_RUNE, DEATH_RUNE, AIR_RUNE, SOUL_RUNE, 2, 2, 1, 1},
		{"SMOKE_RUSH", CHAOS_RUNE, DEATH_RUNE, FIRE_RUNE, AIR_RUNE, 2, 2, 1, 1},
		{"FIRE_SURGE", FIRE_RUNE, AIR_RUNE, BLOOD_RUNE, DEATH_RUNE, 10, 7, 1, 1},
		{"EARTH_SURGE", EARTH_RUNE, AIR_RUNE, BLOOD_RUNE, DEATH_RUNE, 10, 7, 1, 1},
		{"WATER_SURGE", WATER_RUNE, AIR_RUNE, BLOOD_RUNE, DEATH_RUNE, 10, 7, 1, 1},
		{"TELEPORT_BLOCK", CHAOS_RUNE, LAW_RUNE, DEATH_RUNE, -1, 1, 1, 1 ,-1},
		{"WIND_SURGE", AIR_RUNE, BLOOD_RUNE, DEATH_RUNE, -1, 7, 1, 1, -1},
		{"FIRE_WAVE", FIRE_RUNE, AIR_RUNE, BLOOD_RUNE, -1, 7, 5, 1, -1},
		{"EARTH_WAVE", EARTH_RUNE, AIR_RUNE, BLOOD_RUNE, -1, 7, 5, 1, -1},
		{"WATER_WAVE", WATER_RUNE, AIR_RUNE, BLOOD_RUNE, -1, 7, 5, 1, -1},
		{"WIND_WAVE", AIR_RUNE, BLOOD_RUNE, -1, -1, 5, 1, -1, -1},
		{"FLAMES OF ZAMORAK", FIRE_RUNE, BLOOD_RUNE, AIR_RUNE, -1, 4, 2, 1, -1},
		{"CLAWS OF GUTHIX", FIRE_RUNE, BLOOD_RUNE, AIR_RUNE, -1, 1, 2, 4, -1},
		{"SARADOMIN_STRIKE", FIRE_RUNE, BLOOD_RUNE, AIR_RUNE, -1, 2, 2 ,4, -1},
		{"FIRE_BLAST", FIRE_RUNE, AIR_RUNE, DEATH_RUNE, -1, 5, 4, 1, -1},
		{"EARTH_BLAST", EARTH_RUNE, AIR_RUNE, DEATH_RUNE, -1, 4, 3, 1, -1},
		{"WATER_BLAST", WATER_RUNE, AIR_RUNE, DEATH_RUNE, -1, 3, 3, 1, -1},
		{"WIND_BLAST", AIR_RUNE, DEATH_RUNE, -1, -1, 3, 1, -1, -1},
		{"FIRE_BOLT", FIRE_RUNE, AIR_RUNE, CHAOS_RUNE, -1, 4, 3, 1, -1},
		{"EARTH_BOLT", EARTH_RUNE, AIR_RUNE, CHAOS_RUNE, -1, 3, 2, 1, -1},
		{"WATER_BOLT", WATER_RUNE, AIR_RUNE, CHAOS_RUNE, -1, 2, 2, 1, -1},
		{"WIND_BOLT", AIR_RUNE, CHAOS_RUNE, -1, -1, 2, 1, -1, -1},
		{"FIRE_STRIKE", FIRE_RUNE, AIR_RUNE, MIND_RUNE, -1, 3, 2, 1, -1},
		{"EARTH_STRIKE", EARTH_RUNE, AIR_RUNE, MIND_RUNE, -1, 2, 1, 1, -1},
		{"WATER_STRIKE", WATER_RUNE, AIR_RUNE, MIND_RUNE, -1, 1, 1, 1, -1},
		{"WIND_STRIKE", AIR_RUNE, MIND_RUNE, -1, -1, 1, 1, -1, -1},


		{"TELEPORT_TO_APE_ATOLL", FIRE_RUNE, WATER_RUNE, LAW_RUNE, -1, 2, 2, 2, -1},
		{"TROLLHEIM_TELEPORT", FIRE_RUNE, LAW_RUNE, -1, -1, 2, 2, -1, -1},
		{"WATCHTOWER_TELEPORT", EARTH_RUNE, LAW_RUNE, -1, -1, 2, 2 , -1, -1},
		{"ARDOUGNE_TELEPORT", WATER_RUNE, LAW_RUNE, -1, -1, 2, 2, -1, -1},
		{"CAMELOT_TELEPORT", AIR_RUNE, LAW_RUNE, -1, -1, 5, 1, -1, -1},
		{"TELEPORT_TO_HOUSE", LAW_RUNE, AIR_RUNE, EARTH_RUNE, -1, 1, 1, 1, -1},
		{"FALADOR_TELEPORT", WATER_RUNE, AIR_RUNE, LAW_RUNE, -1, 1, 3, 1, -1},
		{"LUMBRIDGE_TELEPORT", EARTH_RUNE, AIR_RUNE, LAW_RUNE, -1, 1, 3, 1, -1},
		{"VARROCK_TELEPORT", FIRE_RUNE, AIR_RUNE, LAW_RUNE, -1, 1, 3, 1, -1},
		{"MOBILISING_ARMIES_TELEPORT", LAW_RUNE, WATER_RUNE, AIR_RUNE, -1, 1, 1, 1, -1},



		{"GHORROCK_TELEPORT", LAW_RUNE, WATER_RUNE, -1, -1, 2, 8, -1, -1},
		{"ANNAKARL_TELEPORT", LAW_RUNE, BLOOD_RUNE, -1, -1, 2, 2, -1, -1},
		{"CARRALLANGAR_TELEPORT", LAW_RUNE, SOUL_RUNE, -1, -1, 2, 2, -1, -1},
		{"DAREEYAK_TELEPORT", LAW_RUNE, FIRE_RUNE, AIR_RUNE, -1, 2, 3, 2, -1},
		{"LASSAR_TELEPORT", LAW_RUNE, WATER_RUNE, -1, -1, 2, 4, -1, -1},
		{"KHARYRLL_TELEPORT", LAW_RUNE, BLOOD_RUNE, -1, -1, 2, 1, -1, -1},
		{"SENNTISTEN_TELEPORT", LAW_RUNE, SOUL_RUNE, -1, -1, 2, 1, -1, -1},
		{"PADDEWWA_TELEPORT", LAW_RUNE, FIRE_RUNE, AIR_RUNE, -1, 2, 1, 1, -1},

		{"SPELLBOOK_SWAP", ASTRAL_RUNE, COSMIC_RUNE, LAW_RUNE, -1, 3, 2, 1, -1},
		{"HEAL_GROUP", ASTRAL_RUNE, BLOOD_RUNE, LAW_RUNE, -1, 4, 3, 6, -1},
		{"VENGEANCE", ASTRAL_RUNE, DEATH_RUNE, EARTH_RUNE, -1, 4, 2, 10, -1},
		{"VENGEANCE_OTHER", ASTRAL_RUNE, DEATH_RUNE, EARTH_RUNE, -1, 3, 2, 10, -1},
		{"HEAL_OTHER", ASTRAL_RUNE, LAW_RUNE, BLOOD_RUNE, -1, 3, 3, 1, -1},
		{"ENERGY_TRANSFER", ASTRAL_RUNE, LAW_RUNE, NATURE_RUNE, -1, 3, 2, 1, -1},
		{"TELE_GROUP_ICE_PLATEAU", ASTRAL_RUNE, LAW_RUNE, WATER_RUNE, -1, 3, 3, 16, -1},
		{"ICE_PLATEAU_TELEPORT", ASTRAL_RUNE, LAW_RUNE, WATER_RUNE, -1, 3, 3, 8, -1},
		{"TELE_GROUP_CATHERBY", ASTRAL_RUNE, LAW_RUNE, WATER_RUNE, -1, 3, 3, 15, -1},
		{"CATHERBY_TELEPORT", ASTRAL_RUNE, LAW_RUNE, WATER_RUNE, -1, 3, 3, 10, -1},
		{"PLANK_MAKE", ASTRAL_RUNE, EARTH_RUNE, NATURE_RUNE, -1, 2, 15, 1, -1},
		{"TELE_GROUP_FISHING_GUILD", ASTRAL_RUNE, LAW_RUNE, WATER_RUNE, -1, 3, 3, 14, -1},
		{"FISHING_GUILD_TELEPORT", ASTRAL_RUNE, LAW_RUNE, WATER_RUNE, -1, 3, 3, 10, -1},
		{"BOOST_POTION_SHARE", ASTRAL_RUNE, EARTH_RUNE, WATER_RUNE, -1, 3, 12, 10, -1},
		{"FERTILE_SOIL", ASTRAL_RUNE, NATURE_RUNE, EARTH_RUNE, -1, 3, 2, 15, -1},
		{"MAGIC_IMBUE", ASTRAL_RUNE, FIRE_RUNE, WATER_RUNE, -1, 2, 7, 7, -1},
		{"STAT_RESTORE_POT_SHARE", ASTRAL_RUNE, EARTH_RUNE, WATER_RUNE, -1, 2, 10 ,10 ,-1},
		{"STRING_JEWELLERY", ASTRAL_RUNE, EARTH_RUNE, WATER_RUNE, -1, 2, 10, 5, -1},
		{"DREAM", ASTRAL_RUNE, COSMIC_RUNE, BODY_RUNE, -1, 2, 1, 5, -1},
		{"TELE_GROUP_KHAZARD", ASTRAL_RUNE, LAW_RUNE, WATER_RUNE, -1, 2, 2, 8 , -1},
		{"KHAZARD_TELEPORT", ASTRAL_RUNE, LAW_RUNE, WATER_RUNE, -1, 2, 2, 4, -1},
		{"SUPERGLASS_MAKE", ASTRAL_RUNE, FIRE_RUNE, AIR_RUNE, -1, 2, 6, 10, -1},
		{"TELE_GROUP_BARBARIAN", ASTRAL_RUNE, LAW_RUNE, FIRE_RUNE, -1, 2, 2, 6,-1},
		{"BARBARIAN_TELEPORT", ASTRAL_RUNE, LAW_RUNE, FIRE_RUNE, -1, 2, 2, 3, -1},
		{"STAT_SPY", ASTRAL_RUNE, COSMIC_RUNE, MIND_RUNE, -1, 2, 2, 5, -1},
		{"CURE_GROUP", ASTRAL_RUNE, COSMIC_RUNE, LAW_RUNE, -1, 2, 2, 2, -1},
		{"TELE_GROUP_WATERBIRTH", ASTRAL_RUNE, LAW_RUNE, WATER_RUNE, -1, 2, 1, 5, -1},
		{"WATERBIRTH_TELEPORT", ASTRAL_RUNE, LAW_RUNE, WATER_RUNE, -1, 2, 1, 1, -1},
		{"HUNTER_KIT", ASTRAL_RUNE, EARTH_RUNE, -1, -1, 2, 2, -1, -1},
		{"CURE_ME", ASTRAL_RUNE, COSMIC_RUNE, LAW_RUNE, -1, 2, 2, 1, -1},
		{"OURANIA_TELEPORT", ASTRAL_RUNE, LAW_RUNE, EARTH_RUNE, -1, 2, 1, 6, -1},
		{"TELE_GROUP_MOONCLAN", ASTRAL_RUNE, LAW_RUNE, EARTH_RUNE, -1, 2, 1, 4, -1},
		{"MOONCLAN_TELEPORT", ASTRAL_RUNE, LAW_RUNE, EARTH_RUNE, -1, 2, 1, 2, -1},
		{"HUMIDIFY", ASTRAL_RUNE, WATER_RUNE, FIRE_RUNE, -1, 1, 3, 1, -1},
		{"CURE_OTHER", ASTRAL_RUNE, LAW_RUNE, EARTH_RUNE, -1, 1, 1, 10, -1},
		{"NPC_CONTACT", ASTRAL_RUNE, COSMIC_RUNE, AIR_RUNE, -1, 1, 1, 2, -1},
		{"MONSTER_EXAMINE", ASTRAL_RUNE, COSMIC_RUNE, MIND_RUNE, -1, 1, 1, 1, -1},
		{"CURE_PLANT", ASTRAL_RUNE, EARTH_RUNE, -1, -1, 1, 8 , -1, -1},
		{"BAKE_PIE", ASTRAL_RUNE, FIRE_RUNE, WATER_RUNE, -1, 1, 5, 4, -1}
	};
	
	public static boolean hasRunes(Player p, String spell) {
		int data[] = new int[8];
		for(int i = 0; i < 8; i++) {
			data[i] = ((Integer)getData(spell, 1 + i));
		}
		boolean has4Runes = data[3] != -1;
		boolean has3Runes = data[3] == -1;
		boolean has2Runes = data[2] == -1;
		if(has4Runes) {
			if(p.getInventory().contains(data[0], data[4]) &&
				p.getInventory().contains(data[1], data[5]) &&
				p.getInventory().contains(data[2], data[6]) &&
				p.getInventory().contains(data[3], data[7])) {
				
				p.getInventory().deleteItem(data[0], data[4]);
				p.getInventory().deleteItem(data[1], data[5]);
				p.getInventory().deleteItem(data[2], data[6]);
				p.getInventory().deleteItem(data[3], data[7]);
				return true;
			} else {
				return false;
			}
		}
		if(has3Runes) {
			if(p.getInventory().contains(data[0], data[4]) &&
					p.getInventory().contains(data[1], data[5]) &&
					p.getInventory().contains(data[2], data[6])) {
				p.getInventory().deleteItem(data[0], data[4]);
				p.getInventory().deleteItem(data[1], data[5]);
				p.getInventory().deleteItem(data[2], data[6]);
				return true;
			} else {
				return false;
			}
		}
		if(has2Runes) {
			if(p.getInventory().contains(data[0], data[4]) &&
					p.getInventory().contains(data[1], data[5])) {
				p.getInventory().deleteItem(data[0], data[4]);
				p.getInventory().deleteItem(data[1], data[5]);
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	public static Object getData(String name, int data) {
		for(Object[] d: RUNE_REQS) {
			if(name.equalsIgnoreCase((String)d[0])) {
				return d[data];
			}
		}
		return null;
	}

}
