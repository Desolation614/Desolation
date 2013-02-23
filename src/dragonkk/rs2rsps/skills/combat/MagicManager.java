package dragonkk.rs2rsps.skills.combat;

import java.util.HashMap;
import java.util.Map;

import dragonkk.rs2rsps.model.player.Player;
import dragonkk.rs2rsps.skills.combat.spells.BloodBarrage;
import dragonkk.rs2rsps.skills.combat.spells.BloodBlitz;
import dragonkk.rs2rsps.skills.combat.spells.IceBarrage;
import dragonkk.rs2rsps.skills.combat.spells.IceBlitz;
import dragonkk.rs2rsps.skills.combat.spells.MiasmicBarrage;
import dragonkk.rs2rsps.skills.combat.spells.Teleblock;
import dragonkk.rs2rsps.util.Logger;

public class MagicManager {
	
	private static final Map<String, MagicInterface> SPELLS = new HashMap<String, MagicInterface>();
	
	public MagicManager(){
		SPELLS.put("IceBlitz", new IceBlitz());
		//SPELLS.put("BloodBlitz", new BloodBlitz());
		SPELLS.put("BloodBarrage", new BloodBarrage());
		SPELLS.put("IceBarrage", new IceBarrage());
		SPELLS.put("MiasmicBarrage", new MiasmicBarrage());
		SPELLS.put("TeleBlock", new Teleblock());
		Logger.log("MagicManager", "Sucessfully loaded: "+SPELLS.size()+", magic spells.");
	}
	
	public static void executeSpell(Player p, Player opp, String Spell) {
		MagicInterface spell = SPELLS.get(Spell);
		spell.execute(p, opp);
	}

}
